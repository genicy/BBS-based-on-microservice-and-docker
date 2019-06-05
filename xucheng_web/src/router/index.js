import Vue from 'vue'
import Router from 'vue-router'
import Main from '../components/Main'
import article_detail from '../components/article/article_detail'
import login from '../components/Login'
import article_submit from '../components/article/article_submit'
import article_column from '../components/article/column_request'
import labels from '../components/qa/label'
import problem from '../components/qa/problem'
import footer from '../components/Footer'
import labelall from '../components/qa/labelall'
import problem_detail from '../components/qa/problem_detail'
import problem_submit from '../components/qa/problem_submit'
import user from '../components/user/user'
import newInfo from '../components/user/newInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Main',
      name: 'Main',
      component: Main,
      children:[]
    },
    {
      path: '/article_detail',
      name: 'article_detail',
      component: article_detail
    }
    ,
    {
      path: '/article_submit',
      name: 'article_submit',
      component: article_submit
    }
    ,
    {
      path: '/article_column',
      name: 'article_column',
      component: article_column
    }
    ,
    {
      path: '/login',
      name: 'login',
      component: login
    }
    ,
    {
      path: '/labels',
      name: 'labels',
      component: labels
    }
    ,
    {
      path: '/problem',
      name: 'problem',
      component: problem
    }
    ,
    {
      path: '/footer',
      name: 'footer',
      component: footer
    }
    ,
    {
      path: '/labels/all',
      name: 'labelall',
      component: labelall
    }
    ,
    {
      path: '/problem_detail',
      name: 'problem_detail',
      component: problem_detail
    }
    ,
    {
      path: '/problem_submit',
      name: 'problem_submit',
      component: problem_submit
    }
    ,
    {
      path: '/user',
      name: 'user',
      component: user
    }
    ,
    {
      path: '/newInfo',
      name: 'newInfo',
      component: newInfo
    }

  ]
})
