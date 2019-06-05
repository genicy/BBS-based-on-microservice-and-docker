<template >
  <el-container >
    <el-header>
      <el-col :span="4" v-if="!isCollapse"><div class="grid-content bg-purple" style="border-right:1px solid white">
        <span class="el-dropdown-link sysicon"><img src="../../assets/logo.png" /> </span>
        FANSHU</div></el-col>
      <el-col :span="2" v-else-if="isCollapse"><div class="grid-content bg-purple" style="border-right:1px solid white">
        <span class="el-dropdown-link sysicon"><img src="../../assets/logo.png" /> </span>
      </div></el-col>
      <el-col :span="18">
        <div class="grid-content bg-purple-light">
          <a style="float: left;color: gainsboro;margin-left: 10px" class="btn btn-default" @click="changeCollapse">
            <i class="fa fa-align-justify" style="cursor: pointer"></i></a>
          <el-dropdown style="float: right">
              <span class="el-dropdown-link userinfo-inner">
          <span class="user-info">{{sysUserName}}</span>
          <img :src="sysUserAvatar" type="primary" style="float: right"/>
        </span>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>我的消息</el-dropdown-item>
              <el-dropdown-item>设置</el-dropdown-item>
              <!--click无效，el-dropdown-item没有自定义click事件-->
              <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
<!--        <span class="el-dropdown-link userinfo-inner">
          <span class="user-info">{{sysUserName}}</span>
          <img :src="sysUserAvatar" style="float: right"/>
        </span>-->
        </div></el-col>
    </el-header>
    <el-container>
      <el-aside class="default" v-bind:style="{width:width+'px'}">
        <el-menu default-active="1-4-1" style="height: 100%;overflow-y: hidden;position: relative;" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
          <el-submenu index="1" >
            <template slot="title" >
              <i class="el-icon-location"></i>
              <span slot="title" >系统</span>
            </template>
            <el-submenu index="1-1">
              <span slot="title"><router-link to="/1">用户频道</router-link></span>
              <el-menu-item index="1-1-1" style="padding-right: 20px"><router-link to="/1">用户</router-link></el-menu-item>
            </el-submenu>

           <el-submenu index="1-2">
              <span slot="title"><router-link to="/2">文章频道</router-link></span>
              <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/2">文章</router-link></el-menu-item>
             <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/22">频道</router-link></el-menu-item>
             <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/23">专栏</router-link></el-menu-item>
            </el-submenu>
            <el-submenu index="1-3">
              <span slot="title"><router-link to="/3">活动频道</router-link></span>
              <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/3">活动</router-link></el-menu-item>
            </el-submenu>
            <el-submenu index="1-4">
              <span slot="title"><router-link to="/5">招聘频道</router-link></span>
              <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/5">企业</router-link></el-menu-item>
              <el-menu-item index="1-2-1" style="padding-right: 20px"><router-link to="/51">职位</router-link></el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2" >
          <template slot="title" >
            <i class="el-icon-edit-outline"></i>
            <span slot="title">监控</span>
          </template>
          <el-submenu index="2-1">
            <span slot="title"><router-link to="/4">图表</router-link></span>
          </el-submenu>
          </el-submenu>
<!--          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>-->
        </el-menu>
      </el-aside>
      <el-main>
        <el-col :span="24" class="content-wrapper">
          <div>

            <router-view></router-view>
          <!--<childcomponents></childcomponents>--></div>
        </el-col>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import {LoginUsers} from '../../api/admin.js'
import usercomponents from './user/User.vue'

export default {
  components: {
    childcomponents: usercomponents
  },
  data () {
    return {
      isCollapse: false,
      sysName: 'VUEADMIN',
      collapsed: false,
      sysUserName: this.$route.params.username,
      sysUserAvatar: LoginUsers.avatar,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    },
    changeCollapse () {
      this.isCollapse = !this.isCollapse
    },
    logout: function () {
      var _this = this
      this.$confirm('确认退出嘛', '提示').then(function () {
        localStorage.removeItem('isLogin')
        _this.$router.push('/login')
      }).catch({

      })
    }
  },
  computed: {
    width: function () {
      if (!this.isCollapse) return 204
      else return 65
    }
  }
}

</script>

<style scoped >
  /*取消router-link的下划线*/
  a {
    text-decoraction: none;
  }
  .router-link-active {
    text-decoration: none;
  }
  .user-info {
    float: right;margin-left: 10px;cursor: pointer;
    color: #fff;
  }
  img {
    cursor: pointer;
    width: 40px;
    height: 40px;
    border-radius: 20px;
    margin: 10px 0px 10px 10px;
    float: left;
  }
  .el-aside default{
    width: 204px;
  }
    .el-aside default isCollapse{
      width: 64px;
    }
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    height: 120px;
    padding: 0;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

/*  .el-main {
    background-color: #E9EEF3;
    color: #333;
    padding: 0;
    text-align: center;
    line-height: 160px;
  }*/
  .el-container {
    height: 500px;
  }

  body > .el-container {
    margin-bottom: 40px;

  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

</style>
