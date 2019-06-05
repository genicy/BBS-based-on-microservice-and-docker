<template>
  <div style="min-width: 1280px">
    <img src="../assets/back.jpg" style="z-index: -10000; float: left;margin:-100px 0 0 -675px;position: absolute;width: 1348px;height: auto"/>
    <el-form :model="formLabelAlign" style="position: relative"
             :rules="rules" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
      <h3 class="title" STYLE="color: #409EFF">FANSHU</h3>
      <el-form-item prop="account">
        <el-input type="text" v-model="formLabelAlign.account" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="formLabelAlign.password" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <div style="margin: -20px 0 60px 0">

        <a v-on:click=""  style="cursor: pointer;color: #409EFF;font-size: 14px;float: right">没有账号，立即注册</a>
      </div>

      <el-form-item >
        <el-button type="primary" style="background-color: #409EFF;width: 50%;float: left" @click.native.prevent="onSubmit" >登录</el-button>
        <a href="/#/Main/" style="color: gray;font-size: 14px;float: right;">忘记密码？</a>
      </el-form-item >
    </el-form>

  </div>
</template>

<script>
  import {initWebSocket} from '../util/WebSocket'
  export default {
    name: 'HelloWorld',
    data () {
      return {
        path:'',
        rules: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' }
            // { validator: validaePass }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        },
        checked: true,
        formLabelAlign: {
          account: '',
          password: ''
        }
      }
    },
    /* 可看待为getter方法 */
    computed: {
      eventNameReverse: function () {
        return this.eventName.split('').reverse().join('')
      }
    },
    methods: {
      doSomething: function () {
        console.log(this.eventNameReverse)
      },
      onSubmit: function () {
        var that = this
        this.$axios.post('http://localhost:8013/user/user/login', {
          loginname: this.formLabelAlign.account,
          password: this.formLabelAlign.password
        }).then(function (res) {
          console.log(res.data.flag)
          if (res.data.flag === true) {
            console.log(res.data.flag)
            localStorage.setItem('user_token', res.data.data.token)
            localStorage.setItem('user_id', res.data.data.user.id)
            localStorage.setItem('user', JSON.stringify(res.data.data.user))
            if(that.path!=''&&that.path!=undefined){
              that.$router.push(that.path)
            }else{
              that.$router.push('/Main')
            }
          } else {
            that.$message({
              showClose: true,
              message: res.data.message,
              type: 'error'
            })
          }
        }).catch(function (res) {
          that.$message({
            showClose: true,
            message: res.message,
            type: 'error'
          })
        })
      }
    },
    mounted(){

      this.path = this.$route.params.path
    }
  }
</script>

<style lang="scss" scoped>
  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 100px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }

</style>
