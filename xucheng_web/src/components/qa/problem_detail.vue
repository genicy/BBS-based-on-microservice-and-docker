<template>
  <div style="min-width: 1100px">
    <headers></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 5px 50px; ">
      <el-col :span="24" style=" background-color: white;">
        <div style="width: 100%;">
          <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="ghostwhite"
            active-text-color="#409EFF">

            <el-menu-item  style="float: left;color: gray" index="-1">全部</el-menu-item>
            <el-menu-item v-on:click="getProblem(item)" style="float: left;color: gray"  v-for="(item, index) in labels" :key="index" :index="index.toString().trim()">{{item.labelname}}</el-menu-item>
            <el-menu-item style="float: right" index="-1">更多</el-menu-item>

          </el-menu>
        </div>
      </el-col>
      <el-col :span="18" style="padding: 0 10px 0 0px">
        <div>
          <div style="padding: 20px">
            <a style="float: left;font-size: xx-large;font-weight: bold">{{problem_detail.title}}</a>
            <el-col  style="margin-top: 10px">
              <el-tag v-for="item in problem_detail.labels" style="border: 0;margin: 0 1px;float: left;">{{item}}</el-tag>
              <a style="color: gray;float: left;"><a style="color: aquamarine">&nbsp;&nbsp;&nbsp;{{problem_detail.nickname}}&nbsp;&nbsp;</a>在2019-05-25 19:52时提问&nbsp;&nbsp;{{problem_detail.visits}}次浏览</a>
            </el-col>

            <el-col :span="3">
              <div style="margin: 10px;height: 100px;width: 60px;border: 1px solid gray;border-radius: 5px;line-height: 2">
                <i class="el-icon-caret-top" v-on:click="commentThumbup(-1)"></i>
                <h4>{{problem_detail.thumbup}}</h4>
                <i class="el-icon-caret-bottom" v-on:click="commentThumbup(-1)"></i>
              </div>
            </el-col>
            <el-col :span="21">
              <div class="ql-container ql-snow" style="margin-top: 10px;min-height: 200px">
                <div class="ql-editor" v-html="problem_detail.content">
                  {{problem_detail.content}}
                </div>
              </div>
            </el-col>
            <el-col style="margin-top: 20px"><h4 style="float: left">撰写答案</h4></el-col>


            <el-col :span="24">
              <el-col><h4 style="float: left;margin: 10px">{{problem_detail.reply}}个回答</h4></el-col>

              <div v-for="(item, index) in comments" style="">
                <div style=" width: 100%;height:100px;">
              <el-col :span="3">
                <div style="margin: 10px;height: 85px;width: 40px;border: 1px solid gray;border-radius: 5px;">
                  <i class="el-icon-caret-top" v-on:click="commentThumbup(item,1)"></i>
                  <h4>{{item.thumbup}}</h4>
                  <i class="el-icon-caret-bottom" v-on:click="commentThumbup(item,-1)"></i>
                </div>
              </el-col>
              <el-col :span="20">
                <div class="ql-container ql-snow" style="margin-top: 10px;min-height: 95px;border: 0;">
                  <div class="ql-editor" v-html="item.content">
                    {{item.content}}
                  </div>
                </div>
                <div style="height: 100px;margin-top: 10px">
                  <el-col><div style="float: left"><i  class="fa fa-commenting">评论</i>&nbsp; &nbsp;&nbsp;
                    <i class="fa fa-cny">赞赏</i>&nbsp; &nbsp;&nbsp;
                    <i class="fa fa-hand-stop-o">举报</i>&nbsp; &nbsp;&nbsp;</div>

                    <div style="margin-left: 400px">
                      <img :src="item.avatar" class="littleImg" style="margin: 10px;cursor: pointer"/>
                      <div>
        <span style="float: left;line-height: 4;font-size: small">{{item.nickname}}
           <el-button type="warning" round size="mini" style="margin: 0 0 0 20px">+ 关注</el-button>
        </span><br/>
                      </div>
                      <div>
                        <div>
        <span style="float: left;margin-left:-120px;line-height: 5;font-size: small">
          <a style="color: darkgrey">发表于2019-05-27</a>
          <a style="color: aqua;font-size: small;cursor: pointer"></a>
        </span>
                        </div>
                      </div>
                    </div>
                  </el-col>
                </div>
              </el-col>
              </div>
              </div>


              <el-col :span="24" style="margin-top: -30px">
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
                <div class="ql-container ql-snow"  style=";margin-top: 30px">
                  <quill-editor
                    v-model="form.content"
                    ref="myQuillEditor"
                    :options="editorOption"
                    @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                    @change="onEditorChange($event)">
                  </quill-editor>
                </div>
                <el-col>
                  <el-switch
                    style="margin: 20px;float: left;"
                    v-model="value3"
                    :width=60
                    active-text="匿名发布"
                    inactive-text="">
                  </el-switch>
                  <el-button type="primary" style="float: right;margin: 5px" v-on:click="sendComment()">提交答案</el-button>
                </el-col>
              </el-col>
            </el-col>

          </div>
        </div>
      </el-col>
      <el-col :span="6" style="padding: 0 10px 0 0px;padding: 10px 20px 0 10px;">
        <div v-show="!showLogin" style="height: 300px;border: 1px solid #409EFF;border-radius: 2px;background-color: white">

          <el-col style="font-weight: bold;color: gray;margin-top: 20px">FANSHU 问答社区</el-col>
          <el-col style="font-size: small;margin-top: 20px;float: left;"><a style="float: left;margin-left: 20px">账号或手机号码</a></el-col>
          <el-input style="width: 90%;" placeholder="请输入账号或手机号码"></el-input>
          <el-col style="font-size: small;margin-top: 20px;float: left;"><a style="float: left;margin-left: 20px">密码</a></el-col>
          <el-input style="width: 90%;" placeholder="不少于6位"></el-input>
          <el-button type="primary" style="margin: 20px;background-color: #409EFF;width: 50%;float: left" @click.native.prevent="onSubmit" >登录</el-button>
          <a href="/#/Main/" style="margin-top: 30px;color: gray;font-size: 14px;float: right;margin-right: 10px">立即注册</a>
        </div>
        <div v-show="showLogin" style="height: 150px;border: 1px solid #409EFF;border-radius: 2px;background-color: white">

          <el-col style="font-weight: bold;color: gray;margin-top: 20px">FANSHU 问答社区</el-col>

          <el-col style="font-size: small;margin-top: 20px;"><a style="margin-left: 20px">三人行，必有我师也！</a></el-col>
          <el-button type="primary" style="margin: 20px;background-color: #409EFF;width: 80%;float: left" @click.native.prevent="onSubmit" >提问题</el-button>
        </div>
      </el-col>
    </div>
  </div>
</template>

<script>
  import 'font-awesome/css/font-awesome.min.css'
  import { quillEditor } from 'vue-quill-editor'
  // 调用编辑器
  import {toolbarOptions} from '../../util/util.js'
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'
  import draggable from 'vuedraggable'
  import headers from '../../components/Header'
  import Vue from 'vue'

  export default {
    name: "problem",
    components:{
      headers,
      draggable,
      quillEditor :quillEditor
    },
    data() {
      return {
        value3:'',
        serverUrl: 'http://localhost:8013/article/article/uploadImg/1',
        headers: {
          'Authorization': 'yangxucheng ' + localStorage.getItem('user_token')
        },
        /*编辑器里面上传图片后返回的路径*/
        imgArticlePath: 'http://localhost:8004/articles/',
        comments:[],
        problem_detail:{},
        showLogin:false,
        userid:'',
        pageSize:5,
        item:[],
        pageCount:1,
        labels:[],
        problems:[],
        labelid:'-1',
        /*问题类型，等待，热门*/
        qaType:'1',
        activeIndex: '1',
        activeIndex2: '1',
        activeName:'first',
        form: {
          userid: '',
          problemid: '',
          nickname:'',
          thumbup: 0,
          avatar: '',
          createtime:'',
          content: `<a style="color: gray;float: left;font-size: large">撰写你的答案...</a>`
    },
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
      };
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
      getdata (evt) {
        console.log(evt.draggedContext.element.id)
      },
      datadragEnd (evt) {
        console.log('拖动前的索引 :' + evt.oldIndex)
        console.log('拖动后的索引 :' + evt.newIndex)
        console.log(this.tags)
      },
      handleCurrentChange (val) {
        this.pageCount = val
        this.getAllProblems()
      },
      handleSelect(key, keyPath) {
        if(key=='-1'){
          this.$router.push('/labels')
        }
      },
      getAllLabel(){
        var arr=[]
        var _this = this
        this.$axios.post("http://localhost:8013/qa/problem/userLabel/"+this.userid,{
        }).then(function (res) {
          res.data.forEach((item2, index)=>{
            arr.push(item2.data)
          })
          _this.$axios.get("http://localhost:8013/base/label")
            .then(function (res) {
              res.data.data.forEach((item2, index)=>{
                arr.push(item2)
              })
            })
        })
        /*数组去重*/
        this.labels = arr

      },
      getProblem(item){
        var _this = this
        this.labelid = item.id
        if(this.qaType=='1'){
          this.newProblems();
        }
        else if(this.qaType=='2'){
          this.waitProblems();
        }
        else {
          this.hotProblems();
        }
      },
      /*获得所有评论*/
      getComments(){
        var _this = this
        this.$axios.post("http://localhost:8013/qa/reply/search/1/10",{
          problemid: this.problem_detail.id,
        }).then(function (res) {
          _this.comments = res.data.data.rows
        }).catch(function (res) {
          console.log(res.data.message)
        })

      },
      commentThumbup(item,thumbup){
        var userid = localStorage.getItem('user_id')
        var _this = this
        if(userid!=''&&userid!=undefined){
          this.$axios.put('http://localhost:8013/qa/reply/thumbup/'+item.id+'/'+userid+'/'+thumbup,{},{
            headers:{
              Authorization: 'yangxucheng ' + localStorage.getItem('user_token')
            }
          }).then(function (res) {
            if(res.data.flag){
              item.thumbup+=thumbup
              /*item.thumbupclass="fa fa-thumbs-up"*/
            }else {
              _this.$message(res.data.message)
            }

          }).catch(function (res) {
            _this.$message.error('您已经表态过了')
          })
        }else {
          this.$router.push({name:'login',params:{path:'/article_detail'}})
        }
      },
      sendComment(){
        var _this = this
        if(this.userid!=undefined&&this.userid!=''){
          this.form.problemid = this.problem_detail.id
          var user = JSON.parse(localStorage.getItem('user'))
          this.form.avatar = user.avatar
          this.form.nickname = user.nickname
          this.form.userid = this.userid
          this.form.createtime = new Date()
          this.$axios.post("http://localhost:8013/qa/reply/",this.form,{
            headers:{
              Authorization: 'yangxucheng ' + localStorage.getItem('user_token')
            }
          }).then(function (res) {
            _this.$message.info('回复成功')
            _this.comments.push(res.data.data)
          })


        }else {
          this.$router.push({name:'login',params:{path:'/problem_detail'}})
        }
      },

    },
    mounted() {
      this.$cookieStore.setCookie('user','1234',10)
      this.problem_detail = JSON.parse(localStorage.getItem('problem_detail'))
      this.userid = localStorage.getItem("user_id")
      if(this.userid!='' && this.userid!=undefined){
        this.showLogin = true
      }
      this.getComments()
      this.getAllLabel()
      console.log(this.$cookieStore.getCookie('user'))
    },

  }
</script>

<style scoped>
  .littleImg {
    cursor: pointer;
    width: 50px;
    height: 50px;
    border-radius: 28px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
</style>
