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
        label="学生id"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="联系方式"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮件地址"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="regdate"
        label="注册时间"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="lastdate"
        label="最后登录"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="在线时长"
        label="online"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="interest"
        label="兴趣"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
      prop="personality"
      label="个性"
      sortable
      width="150">
    </el-table-column>
      <el-table-column
        prop="followcount"
        label="关注数"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="fanscount"
        label="粉丝数"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        prop="state"
        label="状态"
        sortable
        width="150">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
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
    <el-dialog title="用户编辑"  :visible.sync="editFormVisible" >
      <el-form :model="editUser" label-width="80px" >
        <el-form-item label="昵称" >
          <el-input v-model="editUser.nickname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="editUser.mobile" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮件">
          <el-input v-model="editUser.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" >
          <el-input v-model="editUser.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="生日">
          <el-input type="date" v-model="editUser.birthday" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="兴趣">
          <el-input v-model="editUser.interest" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="个性">
          <el-input v-model="editUser.personality" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="粉丝数">
          <el-input v-model="editUser.fanscount" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="关注数">
          <el-input v-model="editUser.followcount" auto-complete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="editUserFunc">提交</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {
  data () {
    return {
      editFormVisible: false,
      editUser: {},
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
        this.$axios.delete('http://localhost:8012/user/user/' + this.multipleSelection[i].id, {
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
      this.editUser = {}
    },
    editUserFunc () {
      /* 判断是修改还是新增 */
      if (this.editUser.id) {
        var _this = this
        this.$axios.put('http://localhost:8012/user/user/' + this.editUser.id, this.editUser, {
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
        this.$axios.post('http://localhost:8012/user/user/', this.editUser, {
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
      this.getUser()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editUser = row

      this.editFormVisible = true
      console.log(this.editUser + this.editFormVisible)
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editUser = row
      console.log(this.editUser)
    },
    /* 分页获取频道 */
    getUser () {
      var _this = this
      this.$axios.post('http://localhost:8012/user/user/search/' + this.pageCount + '/' + this.pageSize, {

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
    this.getUser()
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
