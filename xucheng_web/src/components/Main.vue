<template >
  <el-container style="min-width: 1100px">
    <!--引入组件-->
    <headers v-on:articles="handleSearch"></headers>
    <el-container style="background-color: ghostwhite">
      <!--频道栏-->
      <el-aside width="20%" style="padding: 20px">
        <el-menu
          default-active="-1">
          <el-menu-item index="-1">
            <span slot="title" v-on:click="changeChannel('')">全部</span>
          </el-menu-item>
          <el-menu-item v-for="(item ,index) in channel" :index="index.toString().trim()">
            <span slot="title" v-on:click="changeChannel(item.id)">{{item.name}}</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!--main-->
      <el-main>
        <el-carousel :interval="5000" arrow="always">
          <el-carousel-item v-for="(item, index) in lunbo" :key="index">
            <img :src="item" >
          </el-carousel-item>
        </el-carousel>
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" >
          <el-menu-item index="1" v-on:click="getNewArticle()">最新</el-menu-item>
          <el-menu-item index="2" v-on:click="getHotArticle()">热门</el-menu-item>
          <el-menu-item index="3" v-on:click="getFollowArticle()">关注</el-menu-item>
        </el-menu>
        <el-row style="margin: 20px">
          <nofollow v-show="thumbup==2" id="nofollow"></nofollow>
          <el-col :span="24" v-for="(item, index) in articles" style="margin-bottom: 40px;" >
            <div class="grid-content bg-purple-dark">
              <img :src="item.userAvatar" class="littleImg" style="margin: 10px;cursor: pointer"/>
              <span style="float: left;line-height: 5;font-size: small">{{item.userNickname}} <a
                style="color: darkgrey">in</a>
              <a style="color: aqua;font-size: small;cursor: pointer">{{item.column}}</a>
              </span>
              <div v-on:click="articleDetail(item)" style="cursor: pointer;"><!--可点击-->
              <el-col :span="24" style="padding: 0 0 10px 0">
                <span style="float: left;line-height: 1;font-family: 微软雅黑;font-size: large">{{item.title}}</span>
              </el-col>
              <!--这个地方仅仅作为将正文内容显示出来后提取文字用，提取完毕后立刻使showSummary=fasle-->
              <el-col :span="24" v-show="showSumary" id="summary">
                <span v-html="item.content"
                      style="float: left;line-height: 1;margin-left: 10px ">{{item.content}}</span>
              </el-col>
              <el-col :span="24">
                <span v-html="item.summarys" class="summary" style="">{{item.summarys}}</span>
              </el-col>
              <img v-show="item.image!=''&& item.image!=null" :src="item.image"
                   style="border-radius: 0;width: 100%;height: 150px;margin-top: 10px"/>
              </div>
            </div>

          </el-col>
        </el-row>
      </el-main>

      <!--活动栏-->
      <el-aside width="25%"  style="padding: 10px 0 0 0;height: auto">
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" style="width: 96%">
        <el-menu-item index="1" style="    padding: 0;line-height: 6;">即将截至</el-menu-item>
        <el-menu-item index="2" style="margin-left: 140px;padding: 0;line-height: 6;">查看更多></el-menu-item>
      </el-menu>
        <div>
          <img :src="gathering.image" style="border-radius:0;width: 290px;height: 180px;margin: 10px 0 10px -30px">
          <el-row>
            <el-col :span="24">
              <div class="grid-content bg-purple-dark" style="margin: 10px 0 0 20px;line-height: 1;float: left">
                <a style="font-size: smaller;font-family: 微软雅黑">{{gathering.name}}</a><br/>
              </div>
            </el-col>
          </el-row>
        </div>

        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" style="width: 96%">
          <el-menu-item index="1" style="    padding: 0;line-height: 6;">推荐作者</el-menu-item>
          <el-menu-item index="2" style="margin-left: 140px;padding: 0;line-height: 6">换一批<i class="el-icon-refresh"></i></el-menu-item>
        </el-menu>
        <div style="width: 90%;float: left;margin-bottom: 10px" v-for="(item, index) in randAuthor">
          <div style="float: left;width: 20%"><img :src="item.user.avatar" class="littleImg"></div>
          <div style="float: left;width: 75%;height: 30px;margin-top: -80px">
            <a style="float: left;font-size: small">{{item.name}}</a>
          </div>
          <div style="float: left;width: 75%;height: 30px;margin-top: -60px"><a style="float: left;font-size:small;color: gainsboro">
            共有{{item.user.followcount}}个人关注</a></div>
          <div>
            <el-button v-on:click="test(index)" style="z-index: 10000;position:absolute;float: right;margin: 10px 0px 0 0;background-color: rgb(64, 158, 255);max-height:60px; " type="primary" size="small" icon="el-icon-plus">关注</el-button>
           </div>
        </div>


      </el-aside>
    </el-container>
    <el-footer>footer</el-footer>
  </el-container>

</template>

<script>
  import Vue from 'vue'
  import 'font-awesome/css/font-awesome.min.css'
  import headers from '../components/Header'
  export default {
    name: "Main",
    components :{
      headers
    },
    data() {
      return {
        lunboimg:'http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg',
        lunbo:['http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg',
          'http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg',
          'http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg',
          'http://localhost:8004/cover/ffc7c40d-6936-45d7-9b73-2e27566fce8620190301_3986673_2019_1154436_30008_0.jpg'],
        randAuthor:[],
        /*用户自己的id与关注的id*/
        userid:'-1',
        user_token:'yangxucheng ',
        isLogin: false,
        /*控制文章浏览类型，最新，热门，关注*/
        thumbup:0,
        channelid:'',
        showSumary: true,
        activeIndex: '1',
        isLogin: false,
        channel: [],
        articles: [],
        gathering: '',
        sysUserAvatar: '../assets/logo.png'
      }
    },
    methods: {
      test(index) {
        alert('test'+ index)
      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      getChannel() {
        var _this = this
        this.$axios.get('http://localhost:8013/article/channel/')
          .then(function (res) {
            if (res.data.flag === true) {
              _this.channel = res.data.data
              console.log(_this.channel)
            }
          })
      },
      getEnrolltimeGathering() {
        var _this = this
        this.$axios.post('http://localhost:8013/gathering/gathering/searchByEnrollTime/1/1', {
          state: "1"
        }).then(function (res) {
          if (res.data.flag === true) {
            _this.gathering = res.data.data.rows[0]
            console.log(_this.gathering)
          }
        })
      },
      getArticle() {

        var _this = this
        this.$axios.post('http://localhost:8013/article/article/search/1/10/'+ this.thumbup, {
          channelid: this.channelid,
          user_id:localStorage.getItem('user_id')
        // },{
        //   headers:{
        //     Authorization:this.user_token
        //   }
        }).then(function (res) {
          if (res.data.flag === true) {
            _this.articles = res.data.data.rows
            _this.articles.forEach((item, index) => {
              _this.$axios.get('http://localhost:8013/user/user/' + item.userid).then(function (res) {
                Vue.set(item, 'userAvatar', res.data.data.avatar)
                Vue.set(item, 'userNickname', res.data.data.nickname)
                var summary = window.document.getElementById("summary").innerText
                Vue.set(item, 'summarys', summary)
                Vue.set(item, 'followcount', res.data.data.followcount)
              })
              _this.$axios.get('http://localhost:8013/article/column/' + item.columnid).then(function (res) {
                Vue.set(item, 'column', res.data.data.name)
                Vue.set(item, 'columnsummary', res.data.data['summary'])
                Vue.set(item, 'columnCreatetime', res.data.data.createtime)
              })
            })
            console.log(_this.articles)
            _this.showSumary = false
          }
        })
      },
      articleDetail(item){
        localStorage.setItem('article',JSON.stringify(item))
        this.$router.push({name: 'article_detail', params:{articleinfo : item}})
      },
      changeChannel(id){

        this.channelid = id
        this.getArticle()
      },
      getHotArticle(){
        this.thumbup = 1
        this.getArticle()
      },
      getNewArticle(){
        this.thumbup = 0
        this.getArticle()
      },
      /*获取关注用户文章，没登录先登录*/
      getFollowArticle() {
        this.thumbup = 2
        var userid = localStorage.getItem('user_id')
        if(userid!=''&&userid!=undefined){
          this.getFollowArticles(userid)
          console.log(this.articles.length)

        }else {
          this.$router.push({name:'login',params:{path:'/Main'}})
        }

      },
      handleSearch:function (articles) {
        var _this = this
        articles.forEach((item, index) => {
          _this.$axios.get('http://localhost:8013/article/article/'+item.id)
            .then(function (res) {
            console.log(res)
            Vue.set(item, 'columnid', res.data.data.columnid)
            Vue.set(item, 'image', res.data.data.image)
            Vue.set(item, 'userid', res.data.data.userid)
            Vue.set(item, 'createtime', res.data.data.createtime)
            Vue.set(item, 'updatetime', res.data.data.updatetime)
            Vue.set(item, 'ispublic', res.data.data.ispublic)
            Vue.set(item, 'istop', res.data.data.istop)
            Vue.set(item, 'visits', res.data.data.visits)
            Vue.set(item, 'thumbup', res.data.data.thumbup)
            Vue.set(item, 'comment', res.data.data.comment)
            Vue.set(item, 'channelid', res.data.data.channelid)
            Vue.set(item, 'url', res.data.data.url)
            Vue.set(item, 'type', res.data.data.type)
            _this.$axios.get('http://localhost:8013/user/user/' + item.userid).then(function (res) {
              Vue.set(item, 'userAvatar', res.data.data.avatar)
              Vue.set(item, 'userNickname', res.data.data.nickname)
              var summary = window.document.getElementById("summary").innerText
              Vue.set(item, 'summarys', summary)
              Vue.set(item, 'followcount', res.data.data.followcount)
            })
            _this.$axios.get('http://localhost:8013/article/column/' + item.columnid).then(function (res) {
              Vue.set(item, 'column', res.data.data.name)
              Vue.set(item, 'columnsummary', res.data.data['summary'])
              Vue.set(item, 'columnCreatetime', res.data.data.createtime)
            })
          })

        })
        this.articles = articles
      },
      getFollowArticles(userid) {
        var _this = this
        this.$axios.post('http://localhost:8013/article/article/search/1/10/'+ this.thumbup, {
          channelid: this.channelid,
          user_id:userid
          },{
            headers:{
              Authorization:this.user_token
            }
        }).then(function (res) {
          if (res.data.flag === true) {
            _this.articles = res.data.data.rows
            if(_this.articles.length==0){
              Vue.component('nofollow', {
                template: `<a id="nofollow" style="text-decoration: underline">没有作者或文章？大佬都在这里</a>`
              })
              document.getElementById('nofollow').hidden=false
            }else{
              document.getElementById('nofollow').hidden=true
            }

            console.log(_this.articles)
            _this.showSumary = false
          }
        })
      },
      /*获取随机推荐作者*/
      getRandAuthor(){
        var _this = this
        this.$axios.post("http://localhost:8013/article/column/search/rand/"+ this.userid)
          .then(function (res) {
            console.log(res.data.data)
            _this.randAuthor = res.data.data
        })
      }
    },
    mounted() {
      this.getChannel()
      this.getEnrolltimeGathering()
      this.getArticle(this.channelid)
      var token = localStorage.getItem("user_token")
      if (token) {
        this.isLogin = true
        this.user_token += token
        this.userid= localStorage.getItem('user_id')
      }
      this.getRandAuthor()
    }
  }
</script>

<style>
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

  el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;

  }

  .el-aside {

    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-input__inner {
    border-radius: 16px;
  }

  .el-main {

    color: #333;
    text-align: center;
    line-height: 160px;
  }

  .el-input__inner {
    height: 36px;
  }

  .summary {
    float: left;
    line-height: 2;
    font-size: 14px;
    color: darkgrey;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    width: 100%
  }

</style>
