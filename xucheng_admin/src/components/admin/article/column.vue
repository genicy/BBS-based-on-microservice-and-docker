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
        label="专栏id"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="专栏名称"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="summary"
        label="专栏简介"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="userid"
        label="用户id"
        sortable
        width="100">
      </el-table-column>
      <el-table-column
        prop="createtime"
        label="创建日期"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="checktime"
        label="审核日期"
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
    <el-dialog title="专栏编辑"  :visible.sync="editFormVisible" >
      <el-form :model="editColumn" label-width="80px" >
        <el-form-item label="专栏名称" >
          <el-input v-model="editColumn.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="专栏简介" >
        <el-input type="textarea" v-model="editColumn.summary" auto-complete="off" ></el-input>
      </el-form-item>
        <el-form-item label="申请日期" >
          <el-date-picker type="date" placeholder="选择日期" v-model="editColumn.createtime" style="float: left"></el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-radio-group v-model="editColumn.state" style="float: left;margin-top: 10px">
            <el-radio class="radio" :label="1">通过</el-radio>
            <el-radio class="radio" :label="0">禁止</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editColumnFunc">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {

  data () {
    return {

      editFormVisible: false,
      editColumn: {},
      pageSize: 5,
      pageCount: 1,
      tableData: [],
      multipleSelection: []
    }
  },
  methods: {
    multiDelete () {
      var _this = this
      console.log(this.multipleSelection.length)
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.$axios.delete('http://localhost:8012/article/column/' + this.multipleSelection[i].id, {
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
      this.editColumn = {}
    },
    editColumnFunc () {
      /* 判断是修改还是新增 */
      if (this.editColumn.id) {
        var _this = this
        this.$axios.put('http://localhost:8012/article/column/' + this.editColumn.id, this.editColumn, {
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
        this.$axios.post('http://localhost:8012/article/column/', this.editColumn, {
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
      this.getColumn()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editColumn = row

      this.editFormVisible = true
      console.log(this.editColumn + this.editFormVisible)
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editColumn = row
      console.log(this.editColumn)
    },
    /* 分页获取频道 */
    getColumn () {
      var _this = this
      this.$axios.post('http://localhost:8012/article/column/search/' + this.pageCount + '/' + this.pageSize, {

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
    }

  },
  mounted () {
    this.getColumn()
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
