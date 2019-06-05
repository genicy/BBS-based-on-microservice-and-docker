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
        label="职位id"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="jobname"
        label="职位名称"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="salary"
        label="职位薪水"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="conditions"
        label="经验要求"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="education"
        label="学历要求"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="type"
        label="任职方式"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="address"
        label="办公地址"
        sortable
        width="150">

      </el-table-column>

      <el-table-column
        prop="datetime"
        label="创建日期"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="url"
        label="链接"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="label"
        label="标签"
        sortable
        width="150">
      </el-table-column>

        <el-table-column
          prop="state"
          label="状态"
          sortable
          width="50">

      </el-table-column>

      <el-table-column
        fixed="right"
        label="操作"

        width="70">
        <template slot-scope="scope">
          <el-button @click="handleEditClick(scope.$index,scope.row)" type="text" size="small">编辑</el-button>
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
    <el-dialog title="职位编辑"  :visible.sync="editFormVisible" >
      <el-form :model="editRecruit" label-width="80px" >
        <el-form-item label="职位名称" >
          <el-input v-model="editRecruit.jobname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属企业" >
          <el-select v-model="editRecruit.eid" placeholder="请选择">
            <el-option
              v-for="item in enterprise"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="薪水" >
          <el-input v-model="editRecruit.salary" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="创建时间" >
          <el-date-picker type="date" placeholder="选择日期" v-model="editRecruit.createtime" style="float: left"></el-date-picker>
        </el-form-item>
        <el-form-item label="经验要求" >
          <el-input v-model="editRecruit.conditions" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="学历要求">
          <el-select v-model="editRecruit.education" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任职方式" >
          <el-select v-model="editRecruit.type" placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="办公地址" >
          <el-input  v-model="editRecruit.address" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="标签" >
          <el-input placeholder="如：python;人工智能" v-model="editRecruit.label" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="职位描述" >
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
              v-model="editRecruit.content1"
              ref="myQuillEditor"
              :options="editorOption"
              >
            </quill-editor>
          </div>
        </el-form-item>
        <el-form-item label="职位要求" >
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
              v-model="editRecruit.content2"
              ref="myQuillEditor"
              :options="editorOption"
             >
            </quill-editor>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editRecruitFunc">提交</el-button>
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
      headers: {
        'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
      },
      serverUrl: 'http://localhost:8012/article/article/uploadImg/1',
      options1: [{
        value: '1',
        label: '全职'
      }, {
        value: '2',
        label: '实习'
      }, {
        value: '3',
        label: '兼职'
      }],
      options: [{
        value: '1',
        label: '高中及以上'
      }, {
        value: '2',
        label: '专科及以上'
      }, {
        value: '3',
        label: '本科及以上'
      }, {
        value: '4',
        label: '硕士及以上'
      }, {
        value: '5',
        label: '博士及以上'
      }],
      editFormVisible: false,
      editRecruit: {},
      pageSize: 5,
      pageCount: 1,
      tableData: [],
      enterprise: [],
      multipleSelection: [],
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
    uploadSuccess (res, file) {
      var filePath = this.imgArticlePath + res.data
      console.log(filePath + ' && ' + res.code)
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
    multiDelete () {
      var _this = this
      console.log(this.multipleSelection.length)
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.$axios.delete('http://localhost:8012/recruit/recruit/' + this.multipleSelection[i].id, {
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
      this.editRecruit = {}
    },
    editRecruitFunc () {
      /* 判断是修改还是新增 */
      if (this.editRecruit.id) {
        var _this = this
        this.$axios.put('http://localhost:8012/recruit/recruit/' + this.editRecruit.id, this.editRecruit, {
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
        this.$axios.post('http://localhost:8012/recruit/recruit/', this.editRecruit, {
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
      this.getRecruit()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editRecruit = row

      this.editFormVisible = true
      console.log(this.editRecruit + this.editFormVisible)
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editRecruit = row
      console.log(this.editRecruit)
    },
    /* 分页获取频道 */
    getRecruit (id) {
      var _this = this
      console.log('企业id:' + id)
      this.$axios.post('http://localhost:8012/recruit/recruit/search/' + this.pageCount + '/' + this.pageSize, {
        eid: id
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
    getEnterprise () {
      var _this = this
      this.$axios.get('http://localhost:8012/recruit/enterprise/', {
        headers: {
          'Authorization': 'yangxucheng ' + localStorage.getItem('admin_token')
        }
      }).then(function (res) {
        if (res.data.flag === true) {
          _this.enterprise = res.data.data
          console.log(res.data.data)
        }
      })
    }
  },
  mounted () {
    var id = this.$route.params.id // 如果是路由跳转过来的
    this.getRecruit(id)
    this.getEnterprise()
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
</style>
