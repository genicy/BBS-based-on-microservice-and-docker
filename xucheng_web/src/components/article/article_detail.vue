<template>
<div style="min-width: 1100px">
<headers></headers>

  <el-row :gutter="20" style="margin: 0;background-color: ghostwhite" >
    <div style="margin: 20px 10px 20px 150px;" >
    <el-col :span="17"><div class="grid-content bg-purple" style="background-color: white;padding:20px 20px 30px 20px">
      <div>
        <h2 style="font-weight: bold" >{{article.title}}</h2>
      </div>
      <div>
        <img :src="article.userAvatar" class="littleImg" style="margin: 10px;cursor: pointer"/>
        <div>
        <span style="float: left;line-height: 4;font-size: small">{{article.userNickname}}
          <a style="color: darkgrey">in</a>
          <a style="color: aqua;font-size: small;cursor: pointer">{{article.column}}</a>
           <el-button type="warning" round size="mini" style="margin: 0 0 0 20px">+ 关注</el-button>
        </span><br/>
        </div>
        <div>
          <div>
        <span style="float: left;margin-left:-170px;line-height: 5;font-size: small">
          <a style="color: darkgrey">发表于{{article.createtime}}</a>
          <a style="color: darkgrey">浏览量：{{article.visits}}</a>
           <a style="color: darkgrey">点赞数：{{article.thumbup}}</a>
          <a style="color: aqua;font-size: small;cursor: pointer"></a>
        </span>
          </div>
        </div>
      </div>
      <div >
        <img :src="article.image" v-show="article.image!=''&&article.image!=undefined" style="width: 100%;height: 120px">
      </div>
      <div class="ql-container ql-snow" v-bind:style="{marginTop:(article.image!=''&&article.image!=undefined)?10+'px':120+'px'}">
      <div class="ql-editor" v-html="article.content">
        {{article.content}}
      </div>
      </div>
      <div style="margin-left: 300px">
          <a v-on:click="thumbup()" style="cursor: pointer" class="thumbs_button fa fa-thumbs-up" title="点赞，支持一下"> 点赞</a>
      </div>

    </div>

    </el-col>
      <el-col :span="7" style="float: right">
        <div class="grid-content bg-purple;" style="margin-top: 50px;background-color:white;height: 100px;padding:10px">
          <el-menu default-active="1" class="el-menu-demo" mode="horizontal" style="width: 96%">
            <el-menu-item index="1" style="    padding: 0;line-height: 6;">&nbsp;&nbsp;<a style="color: blue">{{article.column}}</a>的其他热门文章</el-menu-item>
          </el-menu>
          <br>
          <el-col :span="24" v-for="(item, index) in articles" style="margin-bottom: 40px;" >
            <div class="grid-content bg-purple-dark" style="">
              <div v-on:click="articleDetail(item)" style="cursor: pointer"><!--可点击-->
                <el-col :span="24" style="padding: 0 0 10px 0">
                  <span style="float: left;font-size:medium;line-height: 1;font-family: 微软雅黑;">{{item.title}}</span>
                </el-col>
                <!--这个地方仅仅作为将正文内容显示出来后提取文字用，提取完毕后立刻使showSummary=fasle-->
                <el-col :span="24" v-show="showSumary" id="summary">
                <span v-html="item.content"
                      style="float: left;line-height: 1;margin-left: 10px ">{{item.content}}</span>
                </el-col>
                <span v-html="item.summarys" class="summary" style="">{{item.summarys}}</span>
              </div>
            </div>

          </el-col>
<!--          <el-menu default-active="1" class="el-menu-demo" mode="horizontal" style="width: 96%">
            <el-menu-item index="1" style="    padding: 0;line-height: 6;">即将截至</el-menu-item>
            <el-menu-item index="2" style="margin-left: 140px;padding: 0;"><h5>查看更多></h5></el-menu-item>
          </el-menu>
          <div>
            <img :src="gathering.image" style="border-radius:0;width: 290px;height: 180px;margin: 10px 0 10px -30px">
            <el-row>
              <el-col :span="24">
                <div class="grid-content bg-purple-dark" style="margin-top: -80px;line-height: 0;float: left">
                  <a style="font-size: smaller;font-family: 微软雅黑">{{gathering.name}}</a><br/>
                </div>
              </el-col>
            </el-row>
          </div>-->
        </div></el-col>
      <el-col :span="17">
        <div class="a1">
          <img :src="article.userAvatar" class="middleImg" style="margin: 10px 10px 10px 30px;cursor: pointer"/>
          <div style="float: left;width: 50%;height: 25%">
        <span style="float: left;">
          <a style="font-size: medium;cursor: pointer;line-height: 4;">{{article.column}}</a>
          <a style="color: gray;font-size: smaller;line-height: 3;">创建于：{{article.columnCreatetime}}</a>
          <br/>
        </span>
          </div>
          <div  style="float: left;width: 50%;height: 25%">
            <a style="color: gray;font-size: small;float:left;line-height: 3;">共有{{article.followcount}}人关注了她/他</a>
          </div>
          <div style="width: 25%;float: left">
            <el-button type="primary" style="margin: -10px 0 0 40px">+ 关注</el-button>
          </div>
          <div style="margin: 10px; width: 90%;border-top: 1px solid lightsteelblue;float: left">
            <a style="color: gray;font-size: small;float:left;line-height: 3;">{{article.columnsummary}}</a>
          </div>
        </div>
      </el-col>

      <!--评论-->
      <el-col :span="17" v-for="(item ,index) in comments">
        <h3 v-if="index==0" style="float: left">评论</h3><br/>
        <div class="a2">
        <div style="height: 100px;width: 50px;float: left" >
          <img :src="item.userAvatar" class="commetImg"></img>
        </div>
        <div style="height: 15px;width: 90%;float: left" >
          <a v-on:click="comment(index)" style="float: right;cursor: pointer;font-size: small">回复&nbsp;&nbsp;</a>
          <a style="float: right;cursor: pointer;font-size: small">举报&nbsp;&nbsp;</a>
          <a style="float: right;cursor: pointer;font-size: small">{{index+1}}楼&nbsp;&nbsp;</a>
        </div>
        <div style="height: auto;width: 90%;float: left" >
          <a style="float:left;cursor: pointer">&nbsp;&nbsp;{{item.userNickname}}</a>
          <a style="float:left;cursor: pointer;font-size: small;margin-top: 5px">&nbsp;&nbsp;2019-05-19 17:20<!--{{item.createtime}}--></a>
          <span style="word-wrap: break-word;word-break: break-word;float: left;max-width:80%;font-weight: lighter">{{item.comment}}</span>
        </div>
        <div style="height: 20px;width: 90%;float: left;margin-top: -15px" >
          <a v-on:click="commentThumbup(item)" style="float: right;cursor: pointer" :class="item.thumbupclass">{{item.thumbup}}</a>
        </div>
      </div>
        <div class="a3" v-for="(item1,indexchild) in item.childComments">
          <div style="height: 100px;width: 50px;float: left" >
            <img :src="article.userAvatar" class="commetImg"></img>
          </div>
          <div style="height: 15px;width: 80%;float: left" >
            <a v-on:click="childComment(index, indexchild)" style="float: right;cursor: pointer;font-size: small">回复&nbsp;&nbsp;</a>
            <a style="float: right;cursor: pointer;font-size: small">举报&nbsp;&nbsp;</a>
          </div>
          <div style="height: auto;width: 90%;float: left" >
            <a style="float:left;cursor: pointer">&nbsp;&nbsp;{{item1.userNickname}} 回复 {{item1.replyNickname}}</a>
            <a style="float:left;cursor: pointer;font-size: small;margin-top: 5px">&nbsp;&nbsp;2019-05-19 17:20<!--{{item1.createtime}}--></a>
            <span style=";margin-left: 20px;word-wrap: break-word;word-break: break-word;float: left;max-width:80%;font-weight: lighter">&nbsp;&nbsp;{{item1.comment}}</span>
          </div>
          <div style="height: 20px;width: 90%;float: left;margin-top: -10px;margin-right: -120px" >
            <a v-on:click="commentThumbup(item1)" style="float: right;cursor: pointer" :class="item1.thumbupclass">{{item1.thumbup}}</a>
          </div>
          <el-input
            v-show="index==commentNum&&childCommentNum==indexchild"
            style="width: 90%;float: left;clear: both;"
                    placeholder="请输入回复内容"
            v-model="commentContent"
                    >
            <el-button slot="append" v-on:click="sendComment(item1)">确定</el-button>
          </el-input>
        </div>
        <el-input
          v-model="commentContent"
          v-show="index==commentNum&&childCommentNum==-1"
          style="width: 90%;float: left;clear: both;margin-left: 40px"
          placeholder="请输入回复内容">
          <el-button slot="append" v-on:click="sendComment(item)">确定</el-button>
        </el-input>
      </el-col>
      <el-col :span="17" style="border-top: 1px solid gainsboro; margin-top: 30px">
      <el-pagination
        layout="prev, pager, next"
        style="clear: both;"
        :page-size="10"
        :total="50">
      </el-pagination>
        <el-input
          type="textarea"
          v-model="taskComment"
          style="width: 90%;float: left;clear: both;margin-left: 40px;"
          placeholder="请输入回复内容">
        </el-input>
        <el-button v-on:click="sendTaskComment()" style="float: left;margin-left: 40px" type="primary">确定</el-button>
      </el-col>

    </div>

  </el-row>
</div>
</template>

<script>
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'
  import Vue from 'vue'
  import headers from '../Header'
    export default {
        name: "article_detail",
      components:{
        headers
      },
      data(){
        return{
          articlethumbup:[],
          commentthumbup: [],
          /*层主评论*/
          taskComment:'',
          /*回复评论*/
          commentContent:'',
          commentNum:0,
          childCommentNum:-2,
          user_token: 'yangxucheng ',
          showSumary: true,
          article:{},
          articles:[],
          gathering:'',
          comments:[],
          userid:''
        }
      },
      methods:{
        getArticle() {
          var _this = this
          this.$axios.post('http://localhost:8013/article/article/search/1/10/1', {
            'userid':this.article.userid
          }).then(function (res) {
            if (res.data.flag === true) {
              _this.articles = res.data.data.rows
              _this.$axios.get('http://localhost:8013/user/user/' + _this.article.userid).then(function (res) {
                _this.articles.forEach((item, index)=>{
                  Vue.set(item, 'userAvatar', res.data.data.avatar)
                Vue.set(item, 'userNickname', res.data.data.nickname)
                var summary = window.document.getElementById("summary").innerText
                Vue.set(item, 'summarys', summary)
                Vue.set(item, 'followcount', res.data.data.followcount)
                })
              })
              _this.$axios.get('http://localhost:8013/article/column/' + _this.article.userid).then(function (res) {
                _this.articles.forEach((item, index)=>{
                  Vue.set(item, 'column', res.data.data.name)
                  Vue.set(item, 'columnsummary', res.data.data['summary'])
                  Vue.set(item, 'columnCreatetime', res.data.data.createtime)
                })
              })
/*              console.log(_this.articles)*/
              _this.showSumary = false
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
/*              console.log(_this.gathering)*/
            }
          })
        },
        thumbup(){
          var userid = localStorage.getItem('user_id')
          var _this = this
          if(userid!=''&&userid!=undefined){
            this.$axios.put('http://localhost:8013/article/article/thumbup/'+this.article.id+'/'+userid,{},{
              headers:{
                Authorization:this.user_token
              }
            }).then(function (res) {
              _this.$message(res.data.message)
            }).catch(function (res) {
              _this.error(res.data.message)
            })
          }else {
            this.$router.push({name:'login',params:{path:'/article_detail'}})
          }
        },
        articleDetail(item){
          this.article = item
          this.getComments()
        },
        comment(index){
          this.commentContent=''
          this.commentNum = index
          this.childCommentNum = -1;
        },
        childComment(index1,index2){
          this.commentContent=''
          this.commentNum = index1
          this.childCommentNum = index2;
        },
        getComments(){
          var _this = this
          /*首先获得层主评论*/
          this.$axios.post("http://localhost:8013/article/comment/search/1/10",{
            articleid: this.article.id,
            taskid: '0'
          }).then(function (res) {
            _this.comments = res.data.data.rows
            _this.comments.forEach((item, index)=>{
              /*与其各层主各自的信息*/
              _this.$axios.get("http://localhost:8013/user/user/"+item.userid).then(function (res) {
                Vue.set(item, 'userAvatar', res.data.data.avatar)
                Vue.set(item, 'userNickname', res.data.data.nickname)
              })
              /*设置点赞状态,这个userid是正在浏览网页的用户id*/
              if(_this.userid!=''&&_this.userid!=undefined){
                var rediskey = 'comment_'+item.id+'_'+_this.userid
                _this.$axios.get("http://localhost:8013/article/comment/getThumbup/"+rediskey).then(function (res2) {
                  if(res2.data.flag){
                    Vue.set(item, 'thumbupclass', "fa fa-thumbs-up")
                  }else{
                    Vue.set(item, 'thumbupclass', "fa fa-thumbs-o-up")
                  }
                })
              }else{
                Vue.set(item, 'thumbupclass', "fa fa-thumbs-o-up")
              }//
              /*设置点赞状态*/
              /*对各个层主的评论进行遍历，找到此层下的所有评论*/
              _this.$axios.post("http://localhost:8013/article/comment/search/1/10",{
                taskid: item.id
              }).then(function (res1) {
                var childComments = res1.data.data.rows
                childComments.forEach((item1, index)=>{
                  /*与他们各自的信息*/
                  _this.$axios.get("http://localhost:8013/user/user/"+item1.userid).then(function (res2) {
                    Vue.set(item1, 'userAvatar', res2.data.data.avatar)
                    Vue.set(item1, 'userNickname', res2.data.data.nickname)
                  })
                  _this.$axios.get("http://localhost:8013/user/user/"+item1.replyid).then(function (res2) {
                    Vue.set(item1, 'replyAvatar', res2.data.data.avatar)
                    Vue.set(item1, 'replyNickname', res2.data.data.nickname)
                  })
                  /*设置点赞状态,这个userid是正在浏览网页的用户id*/
                  if(_this.userid!=''&&_this.userid!=undefined){
                    var rediskey = 'comment_'+item1.id+'_'+_this.userid
                    _this.$axios.get("http://localhost:8013/article/comment/getThumbup/"+rediskey).then(function (res2) {
                      if(res2.data.flag){
                        Vue.set(item1, 'thumbupclass', "fa fa-thumbs-up")
                      }else{
                        Vue.set(item1, 'thumbupclass', "fa fa-thumbs-o-up")
                      }
                    })
                  }else{
                    Vue.set(item1, 'thumbupclass', "fa fa-thumbs-o-up")
                  }
                  //
                })
                /*子层评论*/
                Vue.set(item,'childComments', childComments)

            })

            })
          }).catch(function (res) {
            console.log(res.data.message)
          })

        },
        sendTaskComment(){
          var _this = this
          if(localStorage.getItem('user_id')!=undefined&&localStorage.getItem('user_id')!='') {
            var comment = {
              comment: this.taskComment,
              userid: localStorage.getItem('user_id'),
              articleid: this.article.id,
              replyid: '0',
              createtime: new Date(),
              taskid: '0',
              state: '1'
            }
            this.$axios.post("http://localhost:8013/article/comment/",comment,{
              headers:{
                Authorization:this.user_token
              }
            }).then(function () {
              _this.$message.info('评论已发布')
              _this.comments.childComments.push(comment)
            }).catch(function (res) {
              console.log(res)
            })
          }
        },
        sendComment(item){
          var _this = this
          console.log(item)
          if(localStorage.getItem('user_id')!=undefined&&localStorage.getItem('user_id')!=''){
            var comment={
              comment: this.commentContent,
              userid: localStorage.getItem('user_id'),
              articleid: this.article.id,
              replyid:item.userid,
              createtime:new Date(),
              taskid:item.id,
              state:'1'
            }
            this.$axios.post("http://localhost:8013/article/comment/",comment,{
              headers:{
                Authorization:this.user_token
              }
            }).then(function () {
              _this.$message.info('评论已发布')

              _this.comments.push(comment)
            })


          }else {
            this.$router.push({name:'login',params:{path:'/article_detail'}})
          }
        },
        commentThumbup(item){
          var userid = localStorage.getItem('user_id')
          var _this = this
          if(userid!=''&&userid!=undefined){
            this.$axios.put('http://localhost:8013/article/comment/thumbup/'+item.id+'/'+userid,{},{
              headers:{
                Authorization:this.user_token
              }
            }).then(function (res) {
              if(res.data.flag){
                item.thumbup+=1
                item.thumbupclass="fa fa-thumbs-up"
              }else {
                _this.$message(res.data.message)
              }

            }).catch(function (res) {
              _this.$message.error('不能重复点赞')
            })
          }else {
            this.$router.push({name:'login',params:{path:'/article_detail'}})
          }
        },


      },
      mounted(){
        this.article =  JSON.parse(localStorage.getItem('article'))
        console.log(this.article)
        this.getArticle()
        this.getEnrolltimeGathering()
        var token = localStorage.getItem("user_token")
        if (token) {
          this.user_token += token
          this.userid = localStorage.getItem('user_id')
        }
        this.getComments()
        console.log(this.comments)
      }
    }
</script>

<style scoped>
  .littleImg {
    cursor: pointer;
    width: 50px;
    height: 50px;
    border-radius: 28px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
  .commetImg {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 8px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
  .middleImg {
    cursor: pointer;
    width: 60px;
    height: 60px;
    border-radius: 30px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
  .a1{
    margin: 20px 0 0 40px;
    padding: 0 0 0 30px;
    width: 700px;
    height: 150px;
    border: 1px solid darkgrey;
    border-radius: 5px;
  }
  .a2{

    margin: 20px 0 0 40px;
    padding: 0 0 0 30px;
    width: 700px;
    background-color: gainsboro;
    border-radius: 5px;
    border: 1px;
  }
  .a3{
    clear: both;
    margin: 20px 0 0 60px;
    padding: 0 0 0 30px;
    width: 600px;
    background-color: gainsboro;
    border-radius: 5px;

  }
  .thumbs_button{
    float: left;
    width:145px;
    text-align: center;
    margin:5px auto;
    height: 45px;
    line-height: 45px;
    background-color:#409EFF;
    color:#fbfbfb;
    text-align:center;
    text-decoration:none;
    font-weight:bold;
    font-size:16px;
    transition: all 0.3s;
    border-radius: 25px;
    border-radius:  25px;
    -webkit-border-radius: 25px ;
    -moz-border-radius: 25px ;
    -o-border-radius: 25px ;
  }
  .reward_button{
    float: left;
    width:145px;
    text-align: center;
    margin:5px auto;
    height: 45px;
    line-height: 45px;
    background-color:#409EFF;;
    color:#fbfbfb;
    text-align:center;
    text-decoration:none;
    font-weight:bold;
    font-size:16px;
    border-left:1px solid #fbfbfb;
    transition: all 0.3s;
    border-radius:  25px;
    -webkit-border-radius: 25px ;
    -moz-border-radius: 25px ;
    -o-border-radius: 25px ;
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
