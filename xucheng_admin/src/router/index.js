import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/admin/Login'
import Main from '../components/admin/Main'
import User from '../components/admin/user/User'
import gathering from '../components/admin/gathering/gathering'
import article from '../components/admin/article/article'
import updateArticle from '../components/admin/article/updateArticle'
import channel from '../components/admin/article/channel'
import column from '../components/admin/article/column'
import echarts from '../components/admin/charts/echarts'
import enterprise from '../components/admin/recruit/Enterprise'
import recruit from '../components/admin/recruit/recruit'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        isLogin: false
      }
    },
    {
      path: '/home',
      name: 'Main',
      component: Main,
      meta: {
        isLogin: true
      },
      children: [
        {
          path: '/1',
          name: 'User',
          component: User,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/2',
          name: 'article',
          component: article,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/21',
          name: 'updateArticle',
          component: updateArticle,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/22',
          name: 'channel',
          component: channel,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/23',
          name: 'column',
          component: column,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/3',
          name: 'gathering',
          component: gathering,
          meta: {
            isLogin: true
          }
        },

        {
          path: '/4',
          name: 'echarts',
          component: echarts,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/5',
          name: 'enterprise',
          component: enterprise,
          meta: {
            isLogin: true
          }
        },
        {
          path: '/51',
          name: 'recruit',
          component: recruit,
          meta: {
            isLogin: true
          }
        }
      ]
    }
  ]
})
