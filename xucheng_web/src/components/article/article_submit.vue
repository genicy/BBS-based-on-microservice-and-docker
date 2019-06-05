<template>
  <div style="min-width: 1200px;" >
    <headers></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 20px 30px; ">
      <el-col :span="17" style="padding: 20px 30px; background-color: white;height: -webkit-fill-available;">
        <div style="margin-bottom: 20px;height: 30px;border-left: 2px solid blue"><a style="float: left;line-height: 2">&nbsp;&nbsp;&nbsp;&nbsp;发表文章</a></div>
        <el-form ref="form" :model="form" label-width="80px" style="">
          <el-form-item label="文章标题">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="上传封面">
            <el-upload
              class="upload-demo"
              name="img"
              :on-success="uploadCoverSuccess"
              :action="coverServerUrl"
              :headers="headers"
              :limit=1
              list-type="picture">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="所属频道">
            <div>
              <el-radio-group v-model="form.channelid" >
                <el-radio-button v-for="(item, index) in channel" :label="item.id" >{{item.name}}</el-radio-button>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="是否公开" >
            <el-radio-group v-model="form.ispublic">
              <el-radio label="是"></el-radio>
              <el-radio label="否"></el-radio>
            </el-radio-group>
          </el-form-item>


          <el-form-item label="标签" >
            <draggable>
          <el-tag
            :key="tag"
            v-for="tag in dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
            {{tag}}
          </el-tag>
            </draggable>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
          <el-form-item label="文章正文">
            <el-upload
              class="avatar-uploader"
              :action="serverUrl"
              :headers="headers"
              name="img"
              :show-file-list="false"
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :before-upload="beforeUpload">
            </el-upload>
            <div class="ql-container ql-snow" style="margin-top: 30px">
              <quill-editor
                v-model="form.content"
                ref="myQuillEditor"
                :options="editorOption"
                @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                @change="onEditorChange($event)">
              </quill-editor>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="7">
        <div style="margin: 0 80px 20px 20px;background-color: white;height:-webkit-fill-available ">
          <div style="height: 60px;border-bottom: 1px solid gainsboro">
          <p style="float: left;line-height:2 ">发帖指南</p>
          </div>
          <div>
          <li>独立思考、自由探索</li><br/>
          <li>发布的内容和互联网、编程开发、产品设计有关</li>
          <li>标题能准确描述的内容，不要发重复的内容</li>
          <li>如果你遇到了具体的编程难题，请到 问答平台 提问</li>
          <li>保持友善，禁止任何形式的广告、SEO 推广</li>
          </div>
        </div>
      </el-col>
    </div>
  </div>
</template>

<script>
  import draggable from 'vuedraggable'
  import Vue from 'vue'
  import 'font-awesome/css/font-awesome.min.css'
  import headers from '../../components/Header'
  // 工具栏配置
  import { quillEditor } from 'vue-quill-editor'
  // 调用编辑器
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'
  import {toolbarOptions} from '../../util/util.js'
    export default {
        name: "article_submit",
      components:{
        draggable:draggable,
        quillEditor :quillEditor,
        headers: headers
      },
      data(){
          return {
            channel: [],
            columninfo: {},
            dynamicTags: [],
            inputVisible: false,
            inputValue: '',
            imgArticlePath: 'http://localhost:8004/articles/',
            imgCoverPath: 'http://localhost:8004/cover/',
            headers: {
              'Authorization': 'yangxucheng ' + localStorage.getItem('user_token')
            },
            // 1代表是文章插图，2为封面
            serverUrl: 'http://localhost:8013/article/article/uploadImg/1',
            coverServerUrl: 'http://localhost:8013/article/article/uploadImg/2',
            str: '',
            form: {
              userid: localStorage.getItem('user_id'),
              columnid: '',
              type:'',
              image: '',
              channelid: '',
              istop: '',
              state: '',
              ispublic: '',
              createtime:'',
              title: '',
              content: `<h1 class="ql-align-center">示范标题</h1><p></p><p><br></p><ol><li><strong><em>Or drag/paste an image here.</em></strong></li><li><strong><em>rerew</em></strong></li><li><strong><em>rtrete</em></strong></li><li><strong><em>tytrytr</em></strong></li><li><strong><em>uytu</em></strong></li></ol>`
            },
            /* 自定义文本编辑器中图片按钮点击事件 */
            editorOption: {
              placeholder: '',
              theme: 'snow', // or 'bubble'
              modules: {
                toolbar: {
                  container: toolbarOptions, // 工具栏
                  handlers: {
                    'image': function (value) {
                      if (value) {
                        // 触发input框选择图片文件
                        document.querySelector('.avatar-uploader input').click()
                      } else {
                        this.quill.format('image', false)
                      }
                    }
                  }
                }
              }
            }
        }
      },
      methods: {
        handleClose(tag) {
          this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },

        showInput() {
          this.inputVisible = true;
          this.$nextTick(_ => {
            this.$refs.saveTagInput.$refs.input.focus();
          });
        },

        handleInputConfirm() {
          let inputValue = this.inputValue;
          if (inputValue) {
            this.dynamicTags.push(inputValue);
          }
          this.inputVisible = false;
          this.inputValue = '';
        },
        beforeUpload () {
          // 显示loading动画
          this.quillUpdateImg = true
        },
        uploadCoverSuccess(res, file){
          var filePath = this.imgCoverPath + res.data
          this.form.image = filePath;
          //res为后端返回的结果集，code为自定义
          if (res.code == '20000') {
            this.$message.success('图片插入成功')
          } else {
            this.$message.info('图片已传输后台，但前台显示错误')
          }
        },
        uploadSuccess (res, file) {
          var filePath = this.imgArticlePath + res.data
          console.log(filePath +" && "+ res.code)
          // res为图片服务器返回的数据
          // 获取富文本组件实例
          let quill = this.$refs.myQuillEditor.quill
          // 如果上传成功
          if (res.code == '20000') {
            // 获取光标所在位置
            let length = quill.getSelection().index
            // 插入图片  filePath为服务器返回的图片地址
            quill.insertEmbed(length, 'image', filePath)
            // 调整光标到最后
            quill.setSelection(length + 1)
            this.$message.success('图片插入成功')
          } else {
            this.$message.info('图片已传输后台，但前台显示错误')
          }
          // loading动画消失
          this.quillUpdateImg = false
        },
        // 富文本图片上传失败
        uploadError () {
          // loading动画消失
          this.quillUpdateImg = false
          this.$message.error('图片插入失败')
        },
        onSubmit () {
          var _this = this
          console.log(this.form.columnid)
          this.form.createtime = new Date()
          this.$axios.post('http://localhost:8013/article/article', this.form, {
            headers: {
              'Authorization': 'yangxucheng ' + localStorage.getItem('user_token')
            }
          }).then(function (res) {
            if (res.data.flag === true) {
              _this.$message.success('文章发布成功')
            } else {
              _this.$message.error('服务器内部错误')
            }
          }).catch(function (res) {
            _this.$message.error('文章上传出错')
          })
        },
        onEditorReady (editor) { // 准备编辑器
        },
        onEditorBlur () {
        }, // 失去焦点事件
        onEditorFocus () {
        }, // 获得焦点事件
        onEditorChange () {
          this.str = this.escapeStringHTML(this.form.content)
        }, // 内容改变事件
        escapeStringHTML (str) {
          str = str.replace(/</g, '<')
          str = str.replace(/>/g, '>')
          console.log(this.str)
          return str
        },
        getChannel(){
          var _this = this
            this.$axios.get('http://localhost:8013/article/channel/')
              .then(function (res) {
                if (res.data.flag === true) {
                  _this.channel = res.data.data
                  console.log(_this.channel)
                }
              })
        }
      },
      computed: {
        editor () {
          return this.$refs.myQuillEditor.quill
        }

      },
      watch: {
        dynamicTags: function (val) {
          this.form.type=''
          val.forEach((item, index)=>{
            this.form.type += item+';'
          })
          console.log(this.form.type)
        }

      },
      mounted(){
        this.columninfo = JSON.parse(localStorage.getItem('columninfo'))
        if(this.columninfo==undefined || this.columninfo == ''){
          this.$router.push('/Main')
        }
        this.getChannel()
        console.log(this.columninfo)
        this.form.columnid =  this.columninfo.id
      }
    }
</script>

<style >
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  li {
    height: 60px;
    float: left;
  }
</style>
