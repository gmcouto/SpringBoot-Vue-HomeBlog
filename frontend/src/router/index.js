import Vue from 'vue'
import Router from 'vue-router'
import ParallaxHome from '@/components/ParallaxHome'
import Blog from '@/components/Blog'
import Post from '@/components/Post'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'ParallaxHome',
      component: ParallaxHome
    },
    { path: '/posts', component: Blog },
    { path: '/posts/:id', component: Post },
    { path: '/login', component: Login }
  ]
})
