<template>
  <div style="min-width: 1100px">
    <headers></headers>
    <div style="background-color: ghostwhite;height: -webkit-fill-available;padding: 20px 80px; ">
      <el-col :span="24" style="padding: 10px 20px; background-color: white;height: -webkit-fill-available;">
        <el-col :span="24" style="border-left: 2px solid #409EFF;height: 30px">
          <a style="float: left;font-weight: bold;font-size: large;line-height: 1.8">&nbsp;标签</a></el-col><br/><br/>
        <el-col :span="24">
        <a style="float: left">标签是最有效的内容组织形式，正确的使用标签能更快的发现和解决你的问题。</a>
       </el-col>
        <el-col :span="23" style="padding: 10px 20px;background-color: ghostwhite;margin-left: 20px">
          <div style="height: 120px;float: left">

            <el-input style="width: 240px" v-model="input" placeholder="添加关注的标签"></el-input>
            <el-button style="background-color: #409EFF;margin: 10px 0 10px 30px;width: 80px" type="primary" icon="el-icon-plus" size="small">关注</el-button>
            <a style="color: gray">(左右拖动进行导航标签排序，取消关注即可移除)</a>
            <draggable>
            <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              style="float: left;margin-top: 10px"
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            </draggable>

          </div>
          <div style="margin-top: 10px;float: left;width: 100%">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item style="font-weight: bold;font-size: medium">常用标签</el-breadcrumb-item>
            <el-breadcrumb-item ><a style="cursor: pointer" v-on:click="tolabelall()">全部</a></el-breadcrumb-item>
          </el-breadcrumb>
          </div>
        </el-col>
        <el-col :span="5" style="border: 1px solid gainsboro;margin: 10px 20px;border: 0" v-for="(item, index) in labels">
          <div style="float: left;width: 100%;height: 40px;border-bottom: 1px solid gainsboro;margin-bottom: 20px">
            <a style="font-weight: bold;line-height: 2.5; ">{{item.name}}</a>
          </div>
          <el-popover
            v-for="(item1, index) in item.label"
            placement="top-start"
            width="300"
            trigger="click">
            <div>
              <el-col :span="24">{{item1.labelname}}</el-col>
              <el-col :span="24">{{item1.summarys}}</el-col>
              <el-col :span="24">
                <a style="float: left;color: #409EFF;line-height: 3">查看</a>
                <div v-show="item1.follow">
                  <el-button  v-on:click="ULFollow(item1)" style="float: right" size="small">已关注</el-button>
                  <a style="float: right;line-height: 2.5">{{item1.fans}}人关注&nbsp;</a></div>
                <div v-show="!item1.follow">
                  <el-button  v-on:click="ULFollow(item1)" style="float: right" size="small">关注</el-button>
                  <a style="float: right;line-height: 2.5">{{item1.fans}}人关注&nbsp;</a></div>
              </el-col>
            </div>
          <el-tag slot="reference" v-on:click="isFollow(item1)" style="margin: 3px;cursor: pointer;float: left;border: 0">{{item1.labelname}}
          </el-tag>

          </el-popover>
        </el-col>
      </el-col>
    </div>
  </div>
</template>

<script>
  import main from '../../main'
  import Vue from 'vue'
  import headers from '../../components/Header'
  import draggable from 'vuedraggable'
    export default {
      components:{
        headers,
        draggable
      },
      data() {
        return {
          userid:'',
          user_token:'yangxucheng ',
          labels:[],
          input:'',
          dynamicTags: [],
          inputVisible: false,
          inputValue: ''
        };
      },
      methods: {
        getLabelGroupByType(){
          var _this = this

          this.$axios.get("http://localhost:8013/base/label/type")
            .then(function (res) {
              res.data.data.forEach((item, index)=>{
                _this.$axios.post("http://localhost:8013/base/label/search",{
                  type:item
                }).then(function (res) {
                  _this.labels.push({
                    name:item,
                    label:res.data.data
                  })
                })
              })
            })
          console.log(this.labels)
        },
        handleClose(tag) {
          this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        tolabelall(){
          this.$router.push('/labels/all')
        },
        isFollow(item) {
          if(this.userid!=undefined && this.userid!=''){
            var _this = this
            var rediskey = 'ul_'+this.userid+'_'+item.id
              this.$axios.get("http://localhost:8013/qa/problem/getULFollow/"+rediskey).then(function (res) {
                  Vue.set(item,'follow',res.data.flag)
                console.log(item.follow)
              })
          }

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
        cancelULFollow(id){

        },
        ULFollow(item){
          if(this.userid!=undefined && this.userid!=''){
            var _this = this
            this.$axios.post("http://localhost:8013/qa/problem/followLabel/"+this.userid+"/"+item.id,{},{
              headers:{
                Authorization:this.user_token
              }
            }).then(function (res) {
              if(res.data.flag){
                item.fans+=1
                _this.dynamicTags.push(item.labelname)
              }
              else{
                item.fans+=-1
              }
                Vue.set(item,'follow',res.data.flag)
            })


          }else{
           this.$router.push({name:'login',params:{path:this.$route}})
          }
        }
      },
      mounted(){
        this.getLabelGroupByType()
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
