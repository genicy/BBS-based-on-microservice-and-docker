<template>
  <div style="min-width: 1100px">
    <headers v-on:info="handle"></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 20px 120px; ">
      <el-col :span="24" style="padding: 10px 20px; background-color: white;height: -webkit-fill-available;">
        <el-col :span="24" >
          <a style="float: left;font-weight: bold;font-size: 20px;line-height: 1.2;border-left: 1px solid #409EFF;color: #409EFF">&nbsp;通知提醒</a></el-col><br/><br/>
          <div v-for="(item ,index) in infom">
             <el-col ><a style="color: gray;float: left">{{item.time}}</a></el-col>
            <el-col style="line-height: 2.5"><a style="color: #409EFF;float: left">{{item.nickname}}&nbsp;&nbsp;
              <a style="color: black">{{item.action}}&nbsp;&nbsp;</a><a style="color:  #409EFF">{{item.object}}</a></a>
              <el-tag v-on:click="deleteInfo(index)" style="float: right;cursor: pointer" type="warning">标为已读</el-tag>
            </el-col>
          </div>
        <el-button v-on:click="deleteAll()" type="warning">全部已读</el-button>

      </el-col>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import headers from '../../components/Header'
  export default {
    name:'app',
    components:{
      headers
    },
    data() {
      return {
        infom:[]
      };
    },
    methods: {
      deleteInfo(inde){
        this.infom.splice(inde,1)
      },
      deleteAll(){
        this.infom=[]
      },
      handle:function (info){
        console.log('这不是测试：'+info)
        this.infom = info
      }
    },
    watch: {
      infom: function () {
      }

    },

    mounted(){
      var _this = this
      var userid = localStorage.getItem('user_id')
      var info = localStorage.getItem('info')
      console.log("这不是测试"+info)

      this.infom= info
/*      this.$axios.get("http://localhost:8009/info/getAllInfo/ws="+userid)
        .then(function (res) {
        _this.infom.push(res.data.data)

      })*/
      console.log("这不是测试"+this.infom)

    }


  }
</script>

<style scoped>

</style>
z
