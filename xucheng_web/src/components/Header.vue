<template>
  <el-container>
  <el-header style="height: 60px;">
    <div style="width: 15%;float: left;"><img src='../assets/logo.png' class="littleImg"/>FANSHU
    </div>
    <div style=" float: left">
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal"
               style="border: 0;">
        <el-menu-item index="1" v-on:click="toArticle()">文章</el-menu-item>
        <el-menu-item index="2" v-on:click="toQa()"><a target="_blank">问答</a></el-menu-item>
        <el-menu-item index="3" v-on:click="toArticle()">活动</el-menu-item>
        <el-menu-item index="4" v-on:click="toArticle()">交友</el-menu-item>
        <el-menu-item index="5" v-on:click="toArticle()">吐槽</el-menu-item>
        <el-menu-item index="6" v-on:click="toArticle()">招聘</el-menu-item>
      </el-menu>
    </div>
    <div style="float: left;margin-left: 20px;width: 160px">
      <el-input  v-model="key" placeholder="请输入内容">
        <i v-on:click="search()" slot="suffix" style="cursor: pointer" class="el-input__icon el-icon-search"></i>
      </el-input>
    </div>
    <div style="float: right;padding: 0 50px 0 0px" v-show="isLogin">
      <el-button type="primary" style="background-color: #409EFF;
    border-color: #409EFF;margin-right: 20px" icon="el-icon-edit" v-on:click="writeArticle()">写文章</el-button>
      <el-badge is-dot class="item" style="margin: 0 -25px 0 0;">
      </el-badge>
      <i v-on:click="goNewInfo()" class="fa fa-bell fa-lg" style="color: #a1a6ab;margin-right: 25px;cursor: pointer"></i>
      <el-badge is-dot class="item" style="margin: 0 -25px 0 0;">
      </el-badge>
      <i class="fa fa-comments fa-lg" style="color: #a1a6ab;"></i>

      <el-dropdown style="float: right">
              <span class="el-dropdown-link userinfo-inner">
         <img class="littleImg" :src=userAvatar type="primary" v-on:click="toUser()" style="float: right"/>
        </span>

        <el-dropdown-menu slot="dropdown" style="clear: both">
          <el-dropdown-item>我的消息</el-dropdown-item>
          <el-dropdown-item>设置</el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </div>
    <div style="float: right;padding: 0 50px 0 50px" v-show="!isLogin">
      <el-button type="primary" v-on:click="login()" plain style="border: 1px solid"> 登录</el-button>
      <el-button type="primary" plain >注册</el-button>
    </div>
  </el-header>
  </el-container>
</template>



<script>
    export default {
        name: "Header",
      /*w无用，做测试*/
      props:{
        value: String,
        required: true
      },
      data(){
          return{
            websock:'',
            info:{},
            artices:'',
            key: '',
            userAvatar: '../assets/logo.png',
            isLogin: false,
            activeIndex:'1',
            userid:''
          }
      },
      mounted() {

          this.isLogin = false
          this.userid = localStorage.getItem('user_id')
          if(this.userid!='' && this.userid!=undefined){
            this.isLogin = true
            this.userAvatar = JSON.parse(localStorage.getItem('user')).avatar
            console.log(this.userAvatar)
          }
        this.initWebSocket()




/*          console.log(this.value)*/
      },
      destroyed(){
        this.websock.close()
        this.websocketclose();
      },
      methods: {
        goNewInfo(){
          this.$router.push({name: 'newInfo', params:{info : this.info}})
        },
        initWebSocket: function () {

          // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
          this.websock = new WebSocket("ws://localhost:8009/websocket/"+this.userid);
          this.websock.onopen = this.websocketonopen;
          this.websock.onerror = this.websocketonerror;
          this.websock.onmessage = this.websocketonmessage;
          this.websock.onclose = this.websocketclose;
        },
        websocketonopen: function () {
          console.log("WebSocket连接成功");
        },
        websocketonerror: function (e) {
          console.log("WebSocket连接发生错误");
        },
        websocketonmessage: function (e) {

          this.info = eval("("+ e.data+")")
          this.$notify({
            title: '提示',
            dangerouslyUseHTMLString: true,
            message: '<a style="color: aqua">'+this.info.nickname+'</a> ' +  '<a style="color: gray">'+this.info.action+ '</a> '+ '<a style="font-weight: bold">'+ this.info.object,
            duration: 0
          });
          localStorage.setItem('info',this.info)
          console.log("得到的消息:"+ e.data);
        },
        websocketclose: function (e) {
          console.log("connection closed )");
        },
        logout() {
          this.websock.close()
          this.websocketclose();
          localStorage.removeItem('user_id')
          localStorage.removeItem('user')
          localStorage.removeItem('user_token')
          localStorage.removeItem('columninfo')
          this.isLogin = false

        },
        login(){
          var path = this.$route.path
          console.log(path)
          this.$router.push({name: 'login', params:{path: path}})
        },
        toArticle(){
          this.$router.push('/Main')
        },
        toQa(){
          this.$router.push('/problem')
        },
        toUser(){
          this.$router.push('/user')
        },

        search(){
          if(this.key!='' && this.key!=null){
            var _this = this
            this.$axios.post("http://localhost:8007/article/"+this.key+"/1/100")
              .then(function (res) {
              _this.articles = res.data.data.rows
                console.log(_this.articles)
                _this.$emit("articles", _this.articles)
            })
          }
        },
        writeArticle(){
          var _this = this
          if(this.isLogin){
            this.$axios.post("http://localhost:8013/article/column/search",{
              userid: this.userid
            })
              .then(function (res) {
                var columninfo= res.data.data[0]

                if(columninfo!=undefined&&columninfo!=null && columninfo.state!="0"){
                  localStorage.setItem('columninfo',JSON.stringify(columninfo))
                  _this.$router.push({name:'article_submit',params:{path: _this.$route}})
                }
                else if(columninfo!=undefined&&columninfo!=null && columninfo.state=="0"){
                  _this.$message.info('专栏正在申请中')
                }
                else{
                  _this.$message.info('需要先创建自己的专栏')
                  _this.$router.push('/article_column')
                }
                console.log(res.data.data)
              })
          }else {
            this.$router.push({name:'login',params:{path: _this.$route}})
          }
        }

      }


    }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .router-link-active {
    color: greenyellow;
  }

  .el-button--primary {
    border: 0;
    background-color: white;
  }

  .littleImg {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 20px;
    margin: 10px 0px 10px 10px;
    float: left;
  }

  .body {
    margin: 0;
  }

  .el-header {
    box-shadow: 1px 1px 1px #888888;
    text-align: center;
    line-height: 60px;

  }
</style>
