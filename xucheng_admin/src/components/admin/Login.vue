<template>
  <div>
<!--    <router-link to="/1">Go to Main</router-link>
    &lt;!&ndash; 路由出口 &ndash;&gt;
    &lt;!&ndash; 点击<router-link>的时候指定的页面将渲染在这里 &ndash;&gt;
    <router-view></router-view>-->
    <el-form :model="formLabelAlign" :rules="rules" ref="ruleForm2" label-position="left" label-width="0px" class="demo-ruleForm login-container">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="account">
        <el-input type="text" v-model="formLabelAlign.account" auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="formLabelAlign.password" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click.native.prevent="onSubmit" >登录</el-button>
        <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
      </el-form-item >
    </el-form>

  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
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
      this.$axios.post('http://localhost:8012/user/admin/login', {
        loginname: this.formLabelAlign.account,
        password: this.formLabelAlign.password
      }).then(function (res) {
        console.log(res.data.flag)
        if (res.data.flag === true) {
          console.log(res.data.flag)
          localStorage.setItem('isLogin', true)
          localStorage.setItem('admin_token', res.data.data.token)
          localStorage.setItem('admin_id', res.data.data.admin.id)
          that.$router.push({name: 'Main', params: {username: that.formLabelAlign.account}})
        } else {
          that.$message({
            showClose: true,
            message: res.data.message,
            type: 'error'
          })
        }
      }).catch(function (res) {
        this.$message({
          showClose: true,
          message: res.data.message,
          type: 'error'
        })
      })
    }
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
    margin: 180px auto;
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
