<!--专栏类似公众号，媒体号，官方等等，只有专栏能够发文章，具体参考https://xiaozhuanlan.com/-->
<template>
  <div>

    <el-col :span="24" class="toolbar" style="padding-bottom: 0px; height: 60px;margin-top: 10px">
      <el-form :inline="true" style="float: left">
        <el-form-item>
          <el-input placeholder="姓名" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createNew">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
      style="width: 100%;">
      <el-table-column type="selection" width="45"/>

      <el-table-column
        prop="id"
        label="活动id"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="活动名称"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="summary"
        label="活动简介"
        sortable
        width="150">
      </el-table-column>
<!--      <el-table-column
        prop="detail"
        label="活动细节"
        sortable
        width="100">
      </el-table-column>-->
      <el-table-column
        prop="sponsor"
        label="主办方"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="image"
        label="活动图片"
        sortable
        width="150">
        <template slot-scope="scope">
          <img :src="scope.row.image" width="100%"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="starttime"
        label="开始时间"
        sortable
        width="150">

      </el-table-column>
      <el-table-column
        prop="endtime"
        label="结束时间"
        sortable
        width="150">

      </el-table-column>
      <el-table-column
        prop="city"
        label="举办城市"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="address"
        label="举办地点"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="enrolltime"
        label="截止时间"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="state"
        label="可见性"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"

        width="70">
        <template slot-scope="scope">
          <el-button @click="handleEditClick(scope.$index,scope.row)" type="text" size="mini">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col style="padding: 20px;height: 40px"><el-button style="float: left" type="danger" @click="multiDelete">批量删除</el-button>
      <el-pagination
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
        style="float: right;height: 40px"
        :page-size="10"
        :total="50">
      </el-pagination></el-col>

    <!--编辑界面-->
    <el-dialog title=""  :visible.sync="editFormVisible"  width="90%" style="float: right;">
      <el-button type="primary">活动编辑</el-button><el-button type="success">活动预览</el-button>
      <div style="border: 3px solid cornflowerblue;margin-top: -20px;border-radius: 5px"></div>

      <el-row >
        <!--编辑-->
        <el-col :span="12"><div class="grid-content bg-purple-dark" style="border-right: 3px solid gainsboro" >
          <el-form :model="editGathering" label-width="80px" >
            <el-row >
              <el-col :span="12"><div class="grid-content bg-purple-dark" style="">
                <el-form-item label="活动名称">
                <el-input  size="mini"  style="width: 220px" v-model="editGathering.name" placeholder="活动名称" auto-complete="off"></el-input>
                </el-form-item>
              </div></el-col>
            </el-row>
            <el-row>
              <el-col :span="12"><div class="grid-content bg-purple">
                <el-form-item label="开始日期">
                  <el-date-picker type="date" size="mini" placeholder="开始时间" v-model="editGathering.starttime"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束日期">
                  <el-date-picker type="date" size="mini" placeholder="结束时间" v-model="editGathering.endtime" ></el-date-picker>
                </el-form-item>
                <el-form-item label="截至日期">
                  <el-date-picker type="date" size="mini" placeholder="截至日期" v-model="editGathering.enrolltime" ></el-date-picker>
                </el-form-item>
                <el-form-item >
                  <el-upload
                    class="upload-demo"
                    name="img"
                    :on-success="uploadCoverSuccess"
                    :action="coverServerUrl"
                    :headers="headers"
                    limit=1
                    size="mini"
                    list-type="picture">
                    <el-button size="mini" type="primary" >上传活动封面</el-button>
                  </el-upload>
                </el-form-item>

              </div></el-col>
              <el-col :span="12"><div class="grid-content bg-purple-light">

                <el-form-item label="城市">
                <el-input v-model="editGathering.city" size="mini" placeholder="举办城市" auto-complete="off"> </el-input>
                </el-form-item>
                  <el-form-item label="地址">
                <el-input v-model="editGathering.address" size="mini" placeholder="举办地点" auto-complete="off" ></el-input>
                  </el-form-item>
                    <el-form-item label="主办">
                <el-input v-model="editGathering.sponsor" size="mini" placeholder="主办方" auto-complete="off" ></el-input>
                    </el-form-item>

              </div></el-col>
            </el-row>
            <el-row>
              <el-col :span="24"><div class="grid-content bg-purple">
                <el-form-item label="活动简介" >
                  <el-input type="textarea"  v-model="editGathering.summary" auto-complete="off" ></el-input>
                </el-form-item>
                <el-form-item label="活动细节">
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
                  <div class="ql-container" >
                    <quill-editor
                      v-model="editGathering.detail"
                      ref="myQuillEditor"
                      :options="editorOption"
                      @blur="onEditorBlur($event)" @focus="onEditorFocus($event)"
                      @change="onEditorChange($event)">
                    </quill-editor>
                  </div>
                </el-form-item>
              </div></el-col>
              <!--<el-col :span="4"><div class="grid-content bg-purple-light">侧</div></el-col>-->
            </el-row>
          </el-form>
        </div></el-col>
        <!--预览-->
        <el-col :span="12"><div class="grid-content bg-purple-dark" style="">
            <el-col :span="24"><div class="grid-content bg-purple-dark" style="">
              <h3 style="float: left;margin-left: 20px">{{editGathering.name}}</h3>
            </div></el-col>
          <el-col :span="12"><div class="grid-content bg-purple-dark" style="">
            <img style="width: 260px;height: 160px;" :src="editGathering.image" alt="封面"/>
          </div></el-col>
          <el-col :span="12"><div  style="">
            <el-col :span="24" ><h5 style="float: left;margin: 5px 0 5px 8px">开始时间：{{editGathering.starttime}}</h5></el-col>
          <el-col :span="24"><h5 style="float: left;margin: 5px 0 5px 8px" >结束时间：{{editGathering.endtime}}</h5></el-col>
          <el-col :span="24"><h5 style="float: left;margin: 5px 0 5px 8px">截至时间：{{editGathering.enrolltime}}</h5></el-col>
          <el-col :span="24"><h5 style="float: left;margin: 5px 0 5px 8px">主办方：{{editGathering.sponsor}}</h5></el-col>
          <el-col :span="24"><h5 style="float: left;margin: 5px 0 5px 8px">地址：{{editGathering.address}}</h5></el-col>
        </div></el-col>
          <el-col :span="20"><div  style="">
            <el-col :span="24" ><h5 style="float: left;color: #20a0ff;border-left: 2px solid #20a0ff;margin-bottom: 0">&nbsp;活动介绍：</h5></el-col>
            <el-col :span="24" ><h5 style="float: left;margin: 5px 0 0 5px">&nbsp;{{editGathering.summary}}</h5></el-col>
            <el-col :span="24" >
              <h5 style="float: left;color: #20a0ff;border-left: 2px solid #20a0ff;margin-bottom: 0">&nbsp;活动详情：</h5></el-col>
            <el-col :span="24" >
              <span v-html="editGathering.detail" style="float: left;margin-bottom: 0;margin: 5px 0 0 5px">&nbsp;{{editGathering.detail}}</span>
            </el-col>
          </div></el-col>
          <el-col :span="4"><div  style="">
            <el-col :span="24" ><h5 style="float: left;color: #20a0ff;border-left: 2px solid #20a0ff;margin-bottom: 0">&nbsp;活动组织者：</h5></el-col>
            <el-col :span="24" ><h5 style="float: left;margin: 5px 0 0 5px">&nbsp;{{editGathering.sponsor}}</h5></el-col>
            <el-col :span="24" >
              <h5 style="float: left;color: #20a0ff;border-left: 2px solid #20a0ff;margin-bottom: 0">&nbsp;相关链接：</h5></el-col>
            <el-col :span="24" >
              <h5  style="float: left;margin-bottom: 0;margin: 5px 0 0 5px">&nbsp;www.XXXX.com</h5>
            </el-col>
            <el-col :span="24" >
              <h5 style="float: left;color: #20a0ff;border-left: 2px solid #20a0ff;margin-bottom: 0">&nbsp;分享链接：</h5></el-col>
            <el-col :span="24" >
              <h5  style="float: left;margin-bottom: 0;margin: 5px 0 0 5px">&nbsp;待...</h5>
            </el-col>
          </div></el-col>
        </div></el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editGatheringFunc">提交</el-button>
      </div>
    </el-dialog>

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
  data () {
    return {
      imgArticlePath: 'http://localhost:8004/articles/',
      imgCoverPath: 'http://localhost:8004/cover/',
      coverPath: 'http://localhost:8004/cover/',
      serverUrl: 'http://localhost:8012/article/article/uploadImg/1',
      coverServerUrl: 'http://localhost:8012/article/article/uploadImg/2',
      headers: {
        'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
      },
      editFormVisible: false,
      editGathering: {},
      pageSize: 5,
      pageCount: 1,
      tableData: [],
      multipleSelection: [],
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
    multiDelete () {
      var _this = this
      console.log(this.multipleSelection.length)
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.$axios.delete('http://localhost:8012/gathering/gathering/' + this.multipleSelection[i].id, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('删除成功')
          } else {
            _this.$message.error('删除失败')
          }
        })
      }
    },
    // 多选框点击函数
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    createNew () {
      this.editFormVisible = true
      this.editGathering = {}
    },
    editGatheringFunc () {
      /* 判断是修改还是新增 */
      if (this.editGathering.id) {
        var _this = this
        this.$axios.put('http://localhost:8012/gathering/gathering/' + this.editGathering.id, this.editGathering, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('修改成功')
          } else {
            _this.$message.error('修改失败')
          }
        })
      } else {
        var _this = this
        this.$axios.post('http://localhost:8012/gathering/gathering/', this.editGathering, {
          headers: {
            'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
          }
        }).then(function (res) {
          if (res.data.code == '20000') {
            _this.$message.success('新增成功')
          } else {
            _this.$message.error('新增失败')
          }
        })
      }
      this.editFormVisible = false
    },
    /* 改变当前页 */
    handleCurrentChange (val) {
      this.pageCount = val
      this.getGathering()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editGathering = row

      this.editFormVisible = true
      console.log(this.editGathering + this.editFormVisible)
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editGathering = row
      console.log(this.editGathering)
    },
    /* 分页获取频道 */
    getGathering () {
      var _this = this
      this.$axios.post('http://localhost:8012/gathering/gathering/search/' + this.pageCount + '/' + this.pageSize, {

      }, {
        headers: {
          'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
        }
      }).then(function (res) {
        if (res.data.flag === true) {
          _this.tableData = res.data.data.rows
          console.log(_this.tableData)
        }
      })
    },
    beforeUpload () {
      // 显示loading动画
      this.quillUpdateImg = true
    },
    uploadCoverSuccess(res, file){
      var filePath = this.imgCoverPath + res.data
      this.editGathering.image = filePath;
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
      this.escapeStringHTML(this.form.content)
    }, // 内容改变事件
    escapeStringHTML (str) {
      str = str.replace(/</g, '<')
      str = str.replace(/>/g, '>')
      console.log(this.str)
    }

  },
  mounted () {
    this.getGathering()
  }

}

</script>

<style scoped>

  element.style {
  }
  user agent stylesheet
  div {
    display: block;
  }
  .el-dialog--center .el-dialog__footer {
    text-align: inherit;
    margin-top: -60px;
  }
  .quill-editor {
    min-height: 200px;
    max-height: 400px;
    overflow-y: auto;
  }
  .el-form-item{
    margin: 2px;
    float: left;
  }
  .el-dialog__header{
    color: #20a0ff;
    border: 2px solid greenyellow;
  }
</style>
