<template >
  <div style="min-width: 1200px;" >
    <headers :value="test"></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 20px 30px; ">
    <el-col :span="17" style="padding: 20px 30px; background-color: white;height: -webkit-fill-available;">
      <div style="margin-bottom: 20px;height: 30px;border-left: 2px solid blue"><a style="float: left;line-height: 2">&nbsp;&nbsp;&nbsp;&nbsp;专栏申请</a></div>
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
    </el-form>
      <el-button type="primary" style="background-color:#409EFF;float: left" v-on:click="column_submit()">提交</el-button>
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
  import headers from '../../components/Header'
    export default {
        name: "column_request",
      components:{
        headers
      },
      data(){
          return {
            test:'组件测试',
            editColumn:{
              name:'',
              userid:'',
              summary:'',
              createtime:'',
              state:'0',
            },
            user_token:'yangxucheng '
          }
      },
      methods:{
        column_submit(){
          var _this = this
          this.$axios.post("http://localhost:8013/article/column/",this.editColumn,{
            headers:{
              Authorization:this.user_token
            }
          }).then(function (res) {
            if(res.data.flag){
              _this.$message.success('专栏申请中，等待审核中')
              /*去我的个人资料界面*/
             /* _this.$router.push('/Main')*/
            }
          })
        }
      },
      mounted(){
        var token = localStorage.getItem("user_token")
        if (token) {
          this.user_token += token
          this.editColumn.userid = localStorage.getItem('user_id')
        }else {
          this.$router.push('/Main')
        }
      }

    }
</script>

<style scoped>

</style>
