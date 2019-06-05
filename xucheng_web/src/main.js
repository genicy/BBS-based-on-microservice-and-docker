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
import VueCookies from 'vue-cookies'
import {setCookie, getCookie, delCookie} from './util/cookie.js'
import AMap from 'vue-amap'


Vue.use(VueCookies)
Vue.use(AMap)
/*添加实例属性cookieStore，调用通过this.cookieStore.getCookie*
$ 是在 Vue 所有实例中都可用的属性的一个简单约定。这样做会避免和已被定义的数据、方法、计算属性产生冲突
/
 */
Vue.prototype.$cookieStore = {
  setCookie,
  getCookie,
  delCookie
}
// 初始化vue-amap
AMap.initAMapApiLoader({
  // 高德key
  key: 'af7cb4d06bfb16859093d26e51283a07',
  // 插件集合 （插件按需引入）
  plugin: ['AMap.Geolocation', 'AMap.Geocoder']
})

Vue.use(VueRouter)
Vue.prototype.$axios = axios
Vue.prototype.$cookie = VueCookies
Vue.config.productionTip = false
Vue.use(ElementUI)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
