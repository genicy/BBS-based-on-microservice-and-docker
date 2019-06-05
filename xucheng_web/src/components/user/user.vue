<template>
  <div style="min-width: 1100px">
    <headers></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 1px 50px; ">
      <el-col :span="24" style="height: 280px;padding-bottom: 40px">
        <div style="width: 24%;height: 100%;float: left">
          <img class="largeImg" :src="userAvatar">
          <el-col style="margin-bottom: 10px;font-size: small;font-weight: bold;margin-left: 20px"><a >&nbsp;&nbsp;&nbsp;关注：<a v-on:click="toMyFollow()" style="color: aquamarine;font-size: large">{{user.followcount}}</a></a>
            <a style="font-size: small;font-weight: bold">&nbsp;&nbsp;&nbsp;粉丝：<a v-on:click="toMyFans()" style="color: aquamarine;font-size: large">{{user.fanscount}}</a></a></el-col>
          <el-col style="margin-left: 30px">
            <i class="fa fa-github fa-lg" style="cursor: pointer"> </i>&nbsp; &nbsp;
            <i class="fa fa-linkedin-square fa-lg" style="cursor: pointer"> </i>&nbsp; &nbsp;
            <i class="fa fa-qq fa-lg" style="cursor: pointer"> </i>&nbsp; &nbsp;
            <i class="fa fa-weibo fa-lg" style="cursor: pointer"> </i>&nbsp; &nbsp;</el-col>
        </div>
        <div style="width: 36%;height: 100%;float: left">
          <el-col><a style="float: left;font-weight: bold;font-size: xx-large;line-height: 3">{{user.nickname}}
            <a style="font-size: medium;color: aquamarine;margin-left: 60px">查看完整信息 </a></a>
          </el-col>
          <el-col><a style="float: left;color: gray;line-height: 2;font-size: large"><i class="fa fa-map-marker"></i>&nbsp; &nbsp;填写现居城市</a></el-col>
          <el-col><a style="float: left;color: gray;line-height: 2;font-size: large"><i  class="fa fa-graduation-cap" ></i>&nbsp; &nbsp;填写毕业院校</a></el-col>
          <el-col> <a style="float: left;color: gray;line-height: 2;font-size: large"><i class="fa fa-briefcase" aria-hidden="true"></i>&nbsp; &nbsp;填写所在公司</a></el-col>
          <el-col><a style="float: left;color: gray;line-height: 2;font-size: large"><i class="fa fa-link" aria-hidden="true"></i>&nbsp; &nbsp;填写个人网站</a></el-col>
        </div>
        <div style="width: 40%;height: 100%;float: left;padding: 40px 20px 0 20px">

          <div style="height: 100%;width: 100%;">
            <div style="height: 20%;padding-top:5px;background-color: gainsboro;border-radius: 3px"><i style="color: gray;cursor: pointer" v-on:click="editP()" class="fa fa-edit">编辑</i></div>
            <div v-if="edit" style="border-radius: 4px">
              <el-input
                type="textarea"
                placeholder="请输入内容"
                v-model="textarea"
                maxlength="30"
                :rows=5
                show-word-limit
              >

              </el-input>
              <el-button style="float: right;margin: 10px 0 0 10px" type="success" v-on:click="submitE()">提交</el-button>
              <el-button style="float: right;margin: 10px 0 0 10px" type="info" v-on:click="cancelE()">取消</el-button>
            </div>
            <div v-else style="height: 80%;background-color: whitesmoke;padding: 20px;border-radius: 4px">
              <el-col style="color: gray">{{user.personality}}</el-col>
            </div>
          </div>
        </div>
        </el-col>
      <el-col :span="5">
        <el-menu
          default-active="2"
          @select="select"
          class="el-menu-vertical-demo"
        >
          <el-menu-item index="1">
            <i class="fa fa-envira"></i>
            <span slot="title">我的足迹</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="fa fa-pencil-square-o"></i>
            <span slot="title">我的回答</span>
          </el-menu-item>
          <el-menu-item index="3" >
            <i class="fa fa-search-minus"></i>
            <span slot="title">我的提问</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="fa fa-file-text"></i>
            <span slot="title">我的文章</span>
          </el-menu-item>
          <el-menu-item index="5">
            <i class="fa fa-book"></i>
            <span slot="title">我的专栏</span>
          </el-menu-item>
          <el-menu-item index="6">
            <i class="fa fa-users"></i>
            <span slot="title">我的声望</span>
          </el-menu-item>
          <el-menu-item index="7">
            <i class="fa fa-address-card-o"></i>
            <span slot="title">个人档案</span>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="18">
        <div v-if="index==1"></div>
        <div v-if="index==2">
          <h4>我的回答({{answers.length}})</h4>
          <div style="margin: 20px 0px 10px 20px;height: 150px;" v-for="(item, index) in answers">
            <!--这个地方仅仅作为将正文内容显示出来后提取文字用，提取完毕后立刻使showSummary=fasle-->
            <el-col :span="24" v-show="showSumary" :id="index">
                <span v-html="item.content"
                      style="float: left;line-height: 1;margin-left: 10px ">{{item.content}}</span>
            </el-col>

            <div style="font-size: x-large;float: left;font-weight: bold"><el-col>{{item.title}}</el-col></div>
            <div ><el-col ><img :src="userAvatar" class="littleImg"><a style="float: left;line-height: 3;font-weight: bold;margin-left: 10px">{{user.nickname}}</a></el-col></div>
            <a class="summary" style="font-size: large;color: gray">{{item.summarys}}</a>

            <el-col style="color: gray;margin-top: 5px">
              <el-tag style="float: left" ><a ><i class="el-icon-caret-top">{{item.thumbup}}赞同</i></a></el-tag>
              <el-tag type="danger" style="float: right" ><a >删除</a></el-tag>
              <!--<a style="float: left;margin-left: 100px">于{{item.createtime}}时回答</a>-->
            </el-col>
          </div>
        </div>
        <div v-if="index==3">
          <h4>我的提问({{problems.length}})</h4>
          <div style="margin: 20px 0px 10px 20px;height: 150px;" v-for="(item, index) in problems">
            <!--这个地方仅仅作为将正文内容显示出来后提取文字用，提取完毕后立刻使showSummary=fasle-->
            <el-col :span="24" v-show="showSumary" :id="index+'p'">
                <span v-html="item.content"
                      style="float: left;line-height: 1;margin-left: 10px ">{{item.content}}</span>
            </el-col>

            <div style="font-size: x-large;float: left;font-weight: bold"><el-col>{{item.title}}</el-col></div>
            <div ><el-col ><img :src="userAvatar" class="littleImg"><a style="float: left;line-height: 3;font-weight: bold;margin-left: 10px">{{user.nickname}}</a></el-col></div>
            <a class="summary"  style="font-size: large;color: gray">{{item.summarys}}</a>
            <el-col style="color: gray;margin-top: 5px">
              <el-tag style="float: right" type="warning" v-show="item.solve==0">待解决</el-tag>
              <el-tag style="float: right" type="success" v-show="item.solve!=0">已采纳</el-tag>
              <el-tag style="float: left" ><a ><i class="el-icon-caret-top">{{item.reply}}</i></a></el-tag>
              <el-tag type="danger" style="float: left;margin-left: 5px">删除</el-tag>
              <!--<a style="float: left;margin-left: 100px">于{{item.createtime}}时提问</a>-->
            </el-col>
          </div>
        </div>
        <div v-if="index==4">
          <h4>我的文章({{articles.length}})</h4>
          <div style="margin: 20px 0px 10px 20px;height: 150px;" v-for="(item, index) in articles">
            <!--这个地方仅仅作为将正文内容显示出来后提取文字用，提取完毕后立刻使showSummary=fasle-->
            <el-col :span="24" v-show="showSumary" :id="index+'a'">
                <span v-html="item.content"
                      style="float: left;line-height: 1;margin-left: 10px ">{{item.content}}</span>
            </el-col>

            <div style="font-size: x-large;float: left;font-weight: bold"><el-col>{{item.title}}</el-col></div>
            <div ><el-col ><img :src="userAvatar" class="littleImg"><a style="float: left;line-height: 3;font-weight: bold;margin-left: 10px">{{user.nickname}}</a></el-col></div>
            <a class="summary"  style="font-size: large;color: gray">{{item.summarys}}</a>
            <el-col style="color: gray;margin-top: 5px">

              <el-tag style="float: left;margin-right: 5px" ><a ><i class="el-icon-caret-top">{{item.thumbup}}点赞</i></a></el-tag>
              <el-tag style="float: left" type="success" >评论{{item.comments}}</el-tag>
              <el-tag type="danger" style="float: right;margin-left: 5px">删除</el-tag>
              <!--<a style="float: left;margin-left: 100px">于{{item.createtime}}时提问</a>-->
            </el-col>
          </div>
        </div>
      </el-col>
    </div>
  </div>
</template>

<script>
  import headers from '../../components/Header'
  import Vue from 'vue'
  export default {
    components:{
      headers
    },
    data(){
      return {
        showSumary:'true',
        index:2,
        problems:[],
        articles:[],
        answers:[],
        pageSize:10,
        pageCount:1,
        textarea:'',
        edit:false,
        userid:'',
        userAvatar:'',
        user:{}
      }
    },
    methods:{
      editP(){
        this.edit = true
      },
      submitE(){
        this.user.personality = this.textarea
        this.edit=false
      },
      cancelE(){
        this.edit=false
      },
      toMyFollow(){

      },
      toMyFans(){

      },
      myArticle(){


          var _this = this
          this.$axios.post('http://localhost:8013/article/article/search',{
            userid:this.userid
          }).then(function (res) {
            if (res.data.flag === true) {

              _this.articles = res.data.data
              _this.articles.forEach((item, index) => {
                var id =  index+ 'a'
                  var summary = window.document.getElementById(id).innerText
                  Vue.set(item, 'summarys', summary)
                  _this.$axios.post('http://localhost:8013/article/comment/search',{
                    articleid: item.id
                  }).then(function (res) {
                    console.log(res.data.data.length)
                    Vue.set(item, 'comments', res.data.data.length)
                  })
              })
              console.log(_this.articles)
              _this.showSumary = false
            }
          })
      },
      myQa(){
        var _this = this
        this.$axios.post("http://localhost:8013/qa/problem/search/"+this.pageCount+"/"+this.pageSize,{
          userid:this.userid
        }).then(function (resp) {
          _this.problems = resp.data.data.rows
          resp.data.data.rows.forEach((item, index)=>{
               var id =  index+ 'p'
                var summary = window.document.getElementById(id).innerText
                Vue.set(item, 'summarys', summary)
          })
          _this.showSumary = false
          _this.problems = resp.data.data.rows
          console.log(_this.problems)
        })
      },
      myAnswer(){
        var _this = this
        this.$axios.post("http://localhost:8013/qa/reply/search/"+this.pageCount+"/"+this.pageSize,{
          userid:this.userid
        }).then(function (resp) {

          resp.data.data.rows.forEach((item, index)=>{
            _this.$axios.get("http://localhost:8013/qa/problem/"+item.problemid)
              .then(function (res) {
                Vue.set(item,'title',res.data.data.title)
                var summary = window.document.getElementById(index).innerText
                Vue.set(item, 'summarys', summary)
              })
          })
          _this.showSumary = false
          _this.answers = resp.data.data.rows
          console.log(_this.answers)
        })
      },
      select(key, path){
        this.index =key
        if(key=='2'){
          this.myAnswer();
        }
        if(key=='3'){
          this.myQa();
        }
        if(key=='4'){
          this.myArticle();
        }

      }
    },
    mounted(){
      this.userid = localStorage.getItem('user_id')
      if(this.userid!='' && this.userid!=undefined){
        this.user = JSON.parse(localStorage.getItem('user'))
        this.userAvatar = this.user.avatar
        console.log(this.userAvatar)

      }
      this.myAnswer()
    }

  }
</script>

<style scoped>
  .largeImg {
    cursor: pointer;
    width: 150px;
    height: 150px;
    border-radius: 70px;
    margin: 20px 50px 10px 30px;
    float: right;
  }
  .profile__heading--other-item {
    font-size: 14px;
    color: #666666;
    line-height: 18px;
    vertical-align: middle;
    display: block;
    margin-top: 10px;
  }
  .summary {
    float: left;
    line-height: 2;
    font-size: 14px;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    overflow: hidden;
    width: 100%
  }
  .littleImg {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 20px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
</style>
