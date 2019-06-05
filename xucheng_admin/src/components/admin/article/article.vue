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
          <el-button type="primary" ><router-link to="/21">新增</router-link></el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table
      id="table"
      :data="tableData"
      border
      @selection-change="handleSelectionChange"
      style="width: 100%;">
      <el-table-column type="selection" width="45"/>
      <el-table-column
        prop="title"
        label="title"
        width="120">
      </el-table-column>
      <el-table-column
        prop="columnid"
        label="专栏id"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="userid"
        label="用户id"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="image"
        label="封面"
        width="120">
        <template slot-scope="scope">
          <img :src="scope.row.image" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column
        prop="updatetime"
        label="更新日期"
        width="120">
      </el-table-column>
      <el-table-column
        prop="ispublic"
        label="是否公开"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="istop"
        label="是否置顶"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="visits"
        label="浏览量"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="thumbup"
        label="点赞数"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="comment"
        label="评论数"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="state"
        label="审核状态"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="channelid"
        label="频道"
        sortable
        width="120">
      </el-table-column>
      <el-table-column
        prop="type"
        sortable
        label="类型"
        width="120">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"

        width="100">
        <template slot-scope="scope">
          <el-button  type="text" size="small" @click="handleEditClick(scope.$index,scope.row)">编辑</el-button>
          <el-button @click="handleShowClick(scope.$index,scope.row)" type="text" size="small">预览</el-button>
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
    <el-dialog title="文章预览"  :opend="test" :visible.sync="editFormVisible1" >
      <!--      <el-form :model="editArticle" label-width="80px" >
              <el-form-item label="文章标题" >
                <el-input v-model="editArticle.title" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="审核状态">
                <el-radio-group v-model="editArticle.state">
                  <el-radio class="radio" :label="1">通过</el-radio>
                  <el-radio class="radio" :label="0">禁止</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="点赞数">
                <el-input-number v-model="editArticle.thumbup" :min="0" :max="200"></el-input-number>
              </el-form-item>
              <el-form-item label="更新日期">
                <el-date-picker type="date" placeholder="选择日期" v-model="editArticle.updatetime"></el-date-picker>
              </el-form-item>
              <el-form-item label="评论数">
                <el-input type="textarea" v-model="editArticle.comment"></el-input>
              </el-form-item>
            </el-form>-->
      <div style="padding: 0px 10px 10px 10px;">
        <div>
          <img :src="editArticle.image" style="width: 100%;height: 120px">
        </div>
        <div >
        <h2 style="font-weight: bold" >{{editArticle.title}}</h2>
      </div>

        <div  v-html="editArticle.content" class="ql-editor" style="clear: both;height: 80px;overflow: hidden;">
          <a id="editDialog">{{editArticle.content}}</a>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button type="primary" >提交</el-button>
        </div>
      </div>
    </el-dialog>
    <!--预览界面-->
    <el-dialog title="文章预览"  :visible.sync="editFormVisible" width="70%" >
      <!--      <el-form :model="editArticle" label-width="80px" >
              <el-form-item label="文章标题" >
                <el-input v-model="editArticle.title" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="审核状态">
                <el-radio-group v-model="editArticle.state">
                  <el-radio class="radio" :label="1">通过</el-radio>
                  <el-radio class="radio" :label="0">禁止</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="点赞数">
                <el-input-number v-model="editArticle.thumbup" :min="0" :max="200"></el-input-number>
              </el-form-item>
              <el-form-item label="更新日期">
                <el-date-picker type="date" placeholder="选择日期" v-model="editArticle.updatetime"></el-date-picker>
              </el-form-item>
              <el-form-item label="评论数">
                <el-input type="textarea" v-model="editArticle.comment"></el-input>
              </el-form-item>
            </el-form>-->
      <div style="padding: 0px 10px 10px 10px;">
      <div>
        <img :src="editArticle.image" style="width: 100%;height: 120px">
      </div>
      <div>
        <h2 style="font-weight: bold" >{{editArticle.title}}</h2>
      </div>
      <div v-html="editArticle.content" class="ql-editor">
        {{editArticle.content}}
      </div>

      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {

  data () {
    return {
      /*预览*/
      editFormVisible: false,
      /*编辑*/
      editFormVisible1: false,
      editArticle: {},
      //编辑页封面内容
      centent: '',
      pageSize: 5,
      pageCount: 1,
      tableData: [],
      multipleSelection: []
    }
  },
  methods: {
    multiDelete(){
      var _this = this
      console.log(this.multipleSelection.length)
      for(var i = 0;i< this.multipleSelection.length;i++){
        this.$axios.delete('http://localhost:8012/article/article/'+this.multipleSelection[i].id,{
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
    //多选框点击函数
    handleSelectionChange(val){
      this.multipleSelection = val

    },
    createNew(){
      this.editFormVisible = true
      this.editArticle={}
    },
    editArticleFunc () {
      /*判断是修改还是新增*/
      if(this.editArticle.id ){
        var _this = this
        this.$axios.put('http://localhost:8012/article/article/' + this.editArticle.id, this.editArticle, {
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
      }else {
        var _this = this
        this.$axios.post('http://localhost:8012/article/article/', this.editArticle, {
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
      this.getArticle()
    },
    /* 点击编辑 */
    handleEditClick (index, row) {
      this.editArticle = row
      this.editFormVisible1 = true
      this.content = document.getElementById("table").innerText
      console.log(this.content)
/*      console.log(this.editArticle + this.editFormVisible)*/
    },
    test(){
      this.content = document.getElementById("editDialog").innerText
      console.log(this.content)
    },
    /* 点击查看 */
    handleShowClick (index, row) {
      this.editFormVisible = true
      this.editArticle = row
      console.log(this.editArticle)
    },
    /* 分页获取文章 */
    getArticle () {
      var _this = this
      this.$axios.post('http://localhost:8012/article/article/search/' + this.pageCount + '/' + this.pageSize, {

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
    this.getArticle()

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
