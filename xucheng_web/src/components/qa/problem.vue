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
          <el-menu default-active="1" class="el-menu-demo" mode="horizontal" >
            <el-menu-item index="1" v-on:click="newProblems()">最新</el-menu-item>
            <el-menu-item index="2" v-on:click="waitProblems()">等待</el-menu-item>
            <el-menu-item index="3" v-on:click="hotProblems()">热门</el-menu-item>
          </el-menu>
          <div v-for="(item, index) in problems" style="margin: 6px 0px ;background-color: white;height: 110px;padding-left: 30px">
          <div style="height: 40px;width: 40px;float: left;border: 1px solid #409EFF;margin:5px 10px 0 0;border-radius: 2px">
            <el-col  style="font-size: smaller;color: #409EFF">{{item.thumbup}}</el-col>
            <el-col style="font-size: smaller">有用</el-col>
          </div>
            <div style="height: 20px;width: 90%;float: right;margin-top: 5px">
              <a style="float: left;font-size: smaller;color: gray"><a style="color: aqua;">{{item.replyname}}</a>&nbsp;&nbsp;&nbsp;&nbsp;于{{item.replytime}}时回答</a>
            </div>
            <div style="height: 30px;width: 90%;float: right;cursor: pointer" v-on:click="problem_detail(item)">
              <a  style="font-size: larger;float: left">{{item.title}}</a>

            </div>
            <div style="height: 30px;width: 90%;float: right;">
              <div v-for="item in item.labels" style="float: left;margin-top: 10px">  <el-tag style="border: 0;margin: 0 1px">{{item}}</el-tag>
              </div>
              <a style="font-size: smaller;color: gray;float: right;margin-top: 10px">浏览量： {{item.visits}}| {{item.createtime}}&nbsp;&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;&nbsp;<a>{{item.nickname}}</a></a>

            </div>
            <div style="height: 40px;width: 40px;float: left;border: 1px solid #E6A23C;border-radius: 2px">
             <el-col size="small" style="font-size: smaller;color: #E6A23C">{{item.reply}}</el-col>
              <el-col size="small" style="font-size: smaller">回答</el-col>


            </div>
          </div>
          <el-pagination
            background
            @current-change="handleCurrentChange"
            layout="prev, pager, next"
            :page-size="pageSize"
            :total="1000">
          </el-pagination>
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
            <el-button type="primary" style="margin: 20px;background-color: #409EFF;width: 80%;float: left" @click.native.prevent="addProblem()" >提问题</el-button>
          </div>
        </el-col>
      </div>
    </div>
</template>

<script>

  import draggable from 'vuedraggable'
  import headers from '../../components/Header'
  import Vue from 'vue'


    export default {
        name: "problem",
      components:{
        headers,
        draggable
      },
      data() {
        return {
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
          userid:'',
          activeIndex: '1',
          activeIndex2: '1',
          activeName:'first',
        };
      },
      methods: {
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
        newProblems(){
          this.qaType='1'
          var _this = this
          this.$axios.get("http://localhost:8013/qa/problem/newlist/"+this.labelid+"/"+this.pageCount+"/"+this.pageSize,{
          }).then(function (res) {
            _this.problems = res.data.data.rows
            console.log(_this.problems)
            _this.problems.forEach((item1, index)=>{
              _this.$axios.post("http://localhost:8013/qa/problem/problemLabel/"+item1.id,{
              })
                .then(function (res) {
                  var labels=[]
                  res.data.forEach((item2, index)=>{
                    labels.push(item2.data.labelname)
                  })
                  Vue.set(item1,'labels',labels)
                })
            })
          })
        },
        waitProblems(){
          this.qaType='2'
          var _this = this
          this.$axios.get("http://localhost:8013/qa/problem/waitlist/"+this.labelid+"/"+this.pageCount+"/"+this.pageSize,{
          }).then(function (res) {
            _this.problems = res.data.data.rows
            _this.problems.forEach((item1, index)=>{
              _this.$axios.post("http://localhost:8013/qa/problem/problemLabel/"+item1.id,{
              })
                .then(function (res) {
                  var labels=[]
                  res.data.forEach((item2, index)=>{
                    labels.push(item2.data.labelname)
                  })
                  Vue.set(item1,'labels',labels)
                })
            })
          })
        },
        hotProblems(){
          this.qaType='3'
          var _this = this
          this.$axios.get("http://localhost:8013/qa/problem/hotlist/"+this.labelid+"/"+this.pageCount+"/"+this.pageSize,{
          }).then(function (res) {
            _this.problems = res.data.data.rows
            _this.problems.forEach((item1, index)=>{
              _this.$axios.post("http://localhost:8013/qa/problem/problemLabel/"+item1.id,{
              })
                .then(function (res) {
                  var labels=[]
                  res.data.forEach((item2, index)=>{
                    labels.push(item2.data.labelname)
                  })
                  Vue.set(item1,'labels',labels)
                })
            })
          })
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
        problem_detail(item){
          this.$router.push('/problem_detail')
          localStorage.setItem('problem_detail',JSON.stringify(item))
        },
        getAllProblems(){
          var _this = this
          this.$axios.post("http://localhost:8013/qa/problem/search/"+this.pageCount+"/"+this.pageSize,{
          }).then(function (res) {
              _this.problems = res.data.data.rows
            _this.problems.forEach((item, index)=>{
              _this.$axios.post("http://localhost:8013/qa/problem/problemLabel/"+item.id,{})
                .then(function (res) {
                  var labels=[]
                  res.data.forEach((item, index)=>{
                    labels.push(item.data.labelname)
                  })
                  Vue.set(item,'labels',labels)
                })
            })
            })

        },
        addProblem(){
          this.$router.push('/problem_submit');
        }
      },
      mounted() {
        this.userid = localStorage.getItem("user_id")
        if(this.userid!='' && this.userid!=undefined){
          this.showLogin = true
        }
        this.getAllLabel()
        this.getAllProblems();

      },
/*      computed:{
          labels:function () {
            console.log(this.alllabels.size)
            this.alllabels.forEach(function (value,key) {
              console.log(key)
            })

            return [...(this.alllabels.keys())];
          }
      }*/

    }
</script>

<style scoped>

</style>
