<template>
  <div >
  <el-form ref="form" :model="form" label-width="80px" style="-moz-box-shadow:4px 2px 9px #333333; -webkit-box-shadow:4px 2px 9px #333333; box-shadow:4px 2px 9px #333333;">
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
        :on-preview="handlePreview"
        limit=1
        list-type="picture">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </el-form-item>
    <el-form-item label="所属频道">
      <div>
        <el-radio-group v-model="form.channelid" >
          <el-radio-button label="机器学习"></el-radio-button>
          <el-radio-button label="后端开发"></el-radio-button>
          <el-radio-button label="前端开发"></el-radio-button>
          <el-radio-button label="移动开发"></el-radio-button>
          <el-radio-button label="程序哲学"></el-radio-button>
        </el-radio-group>
      </div>
    </el-form-item>
    <el-form-item label="是否公开" style="width: 33%;float: left">
      <el-radio-group v-model="form.ispublic">
        <el-radio label="是"></el-radio>
        <el-radio label="否"></el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="是否置顶" style="width: 33%;float: left">
      <el-radio-group v-model="form.istop">
        <el-radio label="是"></el-radio>
        <el-radio label="否"></el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="审核状态" style="width: 33%;float: left">
      <el-radio-group v-model="form.state">
        <el-radio label="是"></el-radio>
        <el-radio label="否"></el-radio>
      </el-radio-group>
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
<!--    <div v-html="str" class="ql-editor">
      asdasd{{str}}
    </div>-->
  </div>
</template>

<script>
// 工具栏配置
import { quillEditor } from 'vue-quill-editor'
// 调用编辑器
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import {toolbarOptions} from '../../../util/util.js'
export default {

  components: {
    quillEditor
  },
  name: 'updateArticle',
  data () {
    return {
      imgArticlePath: 'http://localhost:8004/articles/',
      imgCoverPath: 'http://localhost:8004/cover/',
      headers: {
        'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
      },
      // 1代表是文章插图，2为封面
      serverUrl: 'http://localhost:8012/article/article/uploadImg/1',
      coverServerUrl: 'http://localhost:8012/article/article/uploadImg/2',
      str: '',
      form: {
        image: '',
        channelid: '',
        istop: '否',
        state: '',
        ispublic: '是',
        title: '',
        content: `<p></p><p><br></p><ol><li><strong><em>Or drag/paste an image here.</em></strong></li><li><strong><em>rerew</em></strong></li><li><strong><em>rtrete</em></strong></li><li><strong><em>tytrytr</em></strong></li><li><strong><em>uytu</em></strong></li></ol>`
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
      this.$axios.post('http://localhost:8012/article/article', this.form, {
        headers: {
          'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
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
    }

  },
  computed: {
    editor () {
      return this.$refs.myQuillEditor.quill
    }

  }
}

</script>

<style>

</style>
