<template>
  <div style="min-width: 1100px">
    <headers></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 20px 60px; ">
      <el-col :span="24" style="padding: 10px 20px; background-color: white;height: -webkit-fill-available;">
        <el-col :span="24" >
          <a style="float: left;font-weight: bold;font-size: 28px;line-height: 1.8">&nbsp;标签</a></el-col><br/><br/>
        <el-col :span="24">
          <a style="float: left;font-size: large">&nbsp;&nbsp;标签是最有效的内容组织形式，正确的使用标签能更快的发现和解决你的问题。</a>
        </el-col>
        <el-col :span="5" style="border: 1px solid gainsboro;margin: 10px 20px" v-for="(item, index) in labels">
          <div style="float: left;width: 100%;height: 110px;">
            <a style="font-weight: bold;line-height: 2.5;; ">{{item.labelname}}</a>
            <el-col style="font-size: small;color: gray">{{item.summarys}}</el-col>
          </div>
          <div v-show="!item.follow" style="float: left;width: 100%;height: 50px;background-color: gainsboro">
            <el-button style="background-color: #409EFF;float: left;margin: 10px 0 10px 20px;width: 80px" v-on:click="ULFollow1(item)" type="primary" icon="el-icon-plus" size="small">关注</el-button>
            <a style="line-height: 3.5;"> 共有{{item.fans}}关注</a>
          </div>
          <div v-show="item.follow" v-on:click="ULFollow1(item)" style="float: left;width: 100%;height: 50px;background-color: gainsboro">
            <el-button style="background-color: #409EFF;float: left;margin: 10px 0 10px 20px;width: 80px" type="primary" icon="el-icon-minus" size="small">取关</el-button>
            <a style="line-height: 3.5;"> 共有{{item.fans}}关注</a>
          </div>
        </el-col>
      </el-col>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import headers from '../../components/Header'
  import draggable from 'vuedraggable'
  export default {
    name:'app',
    components:{
      headers,
      draggable
    },
    data() {
      return {
        labels:[],
        input:'',
        userid: '',
        user_token:'yangxucheng ',
        dynamicTags: ['标签一', '标签二', '标签三'],
        inputVisible: false,
        inputValue: ''
      };
    },
    methods: {
      getAllLabel(){
        var _this = this
        this.$axios.get("http://localhost:8013/base/label")
          .then(function (res) {
            _this.labels = res.data.data
            if(_this.userid!=''&&_this.userid!=undefined){
              _this.labels.forEach((item, index)=>{
                var rediskey = 'ul_'+_this.userid+'_'+item.id
                _this.$axios.get("http://localhost:8013/qa/problem/getULFollow/"+rediskey).then(function (res) {
                  Vue.set(item,'follow',res.data.flag)
                  console.log(item.follow)
                })
              })
            }
          })
        console.log(this.labels)
      },
      handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },

      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.dynamicTags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      ULFollow1(item){
        if(this.userid!=undefined && this.userid!=''){
          var _this = this
          this.$axios.post("http://localhost:8013/qa/problem/followLabel/"+this.userid+"/"+item.id,{},{
            headers:{
              Authorization:this.user_token
            }
          }).then(function (res) {
            if(res.data.flag){
              item.fans+=1
            }
            else{
              item.fans+=-1
            }
            Vue.set(item,'follow',res.data.flag)
          })
        }else{
          this.$router.push({name:'login',params:{path:this.$route}})
        }
      },
    },

    mounted(){
        this.getAllLabel();
        var token = localStorage.getItem("user_token")
       if (token) {
         this.user_token += token
         this.userid = localStorage.getItem('user_id')
      }
    }


  }
</script>

<style scoped>

</style>
