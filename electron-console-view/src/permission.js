import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getRouterData } from './router/routerData'
import { getToken } from '@/common/utils/auth' // 验权

const whiteList = ['/login', '/system/homeAdPopup', '/system/footNavSet', '/register'] // 不重定向白名单
router.beforeEach(async(to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      console.log('登录跳转的路径==>', store.getters.roles.length, to)
      if (store.getters.roles.length === 0) {
        try {
          await store.dispatch('GetInfo')
          // 判断当前用户是否已经设置路由
          getRouterData(router, to)
          next()
        } catch (e) {
          console.log('登录错误==>', e)
          store.dispatch('FedLogOut').then(() => {
            Message.error('Verification failed, please login again')
            next({ path: '/' })
          })
        }
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
