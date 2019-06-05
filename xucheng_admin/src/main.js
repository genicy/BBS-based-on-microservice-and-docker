// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import 'font-awesome/css/font-awesome.min.css'
import AMap from 'vue-amap'

Vue.use(AMap)

// 初始化vue-amap
AMap.initAMapApiLoader({
  // 高德key
  key: 'af7cb4d06bfb16859093d26e51283a07',
  // 插件集合 （插件按需引入）
  plugin: ['AMap.Geolocation', 'AMap.Geocoder']
})

Vue.use(VueRouter)
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI)
router.beforeEach((to, from, next) => {
  // 获取用户登录成功后储存的登录标志
  let getFlag = localStorage.getItem('isLogin')
  // 如果登录标志存在且为isLogin，即用户已登录
  if (getFlag) {
    next()
    // 如果已登录，还想想进入登录注册界面，则定向回首页
    if (!to.meta.isLogin) {
      // Vue.prototype全局
      Vue.prototype.$message('请先退出系统')
      next({
        path: '/home'
      })
    }
    // 如果登录标志不存在，即未登录
  } else {
    // 用户想进入需要登录的页面，则定向回登录界面
    if (to.meta.isLogin) {
      next({
        path: '/login'
      })
      Vue.prototype.$message('请先登录')
      // 用户进入无需登录的界面，则跳转继续
    } else {
      next()
    }
  }
})

router.afterEach(route => {
  window.scroll(0, 0)
})
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
