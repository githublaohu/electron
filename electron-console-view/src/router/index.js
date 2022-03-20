import Vue from 'vue'
import Router from 'vue-router'


/* Layout */
import Layout from '../views/layout/Layout'
/* 路由模块 */
import loginPage from '../views/login'
import err404Page from '../views/errorPage/404'

import { myRoutes } from './routerData'



/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
 **/


Vue.use(Router)

export const constantRouterMap = [
  // {
  //   path: '/redirect',
  //   component: layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: '/redirect/:path*',
  //       component: redirectpage
  //     }
  //   ]
  // },
  // 默认页面
  {
    path: '/',
    component: Layout,
    redirect: '/home'
  },
  {
    path: '/login',
    component: loginPage,
    hidden: true
  },
  {
    path: '/404',
    component: err404Page,
    hidden: true
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
];


/**
 * 公共页面
 * 登录页面
 * 普通用户页面
 * admin用户页面
 * 404页面
 */
const router = new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: [
    ...constantRouterMap
    // ...myRoutes
  ],
  layout: Layout
});
router.beforeEach((to, from, next) => {
  if (to.query.title != undefined && to.query.title != "") {
    to.meta.title = to.query.title;
  }
  next()
});

export default router
