/* Layout */
import Layout from '../views/layout/Layout'



/**
 * 管理员理由
 * 工作空间
 * 0. 项目首页
 *    2. 用户管理
 * 	  3. 项目管理
 *
 */
import adminHome from '../views/home/adminHome'



/**
 *
 * 用户路由
 *      工作空间
 * 0. 项目首页
 *    1. 用户空间与资源
 *    2. 添加空间
 * 4.  动作管理
 * 5.  组织管理
 * 6.  用户管理
 * */
import home from '../views/home/home'


/**
 * 项目路由
 * 1. 项目首页
 *    1. 各种统计信息
 *    2. 在线实例
 * 2. 接口管理
 *      1. 只有简单的查询
 * 3. 实例管理
 *      1. 只有简单的查询
 * 4.  动作绑定
 * 4.  动作管理
 *      1. 动作列表
 *      2. 添加动作
 *      3. 查询主要动作
 *      4. 作废动作
 *      5. 主动作列表
 *      6. 添加从动作
 *      7. 作废从动作
 *      8. 复制动作
 * 4.  组织管理
 *       1. 添加组织
 *       2. 删除组织
 *       3. 修改组织
 *       4. 查询组织
 * 5. 请求流程记录
 *        1. 查询操作
 * 6. 统计报表
 *        1. 查询操作
 * 7. 警告记录
 *        2. 查询操作
 * 8. 成员管理
 *        1. 查询用户
 *        2. 设置权限
 *        3. 修改权限
 *        4. 查询权限
 */



var userRouter = []
var adminRouter = []

// 普通用户路由
var router = [
  {
    path: '/home',
    component: Layout,
    meta: {
      title: '首页',
      icon: 'el-icon-house'
    },
    children: [{
      path: '',
      component: home,
      name: 'home',
      meta: {
        title: '首页',
        icon: 'el-icon-house'
      }
    }]
  }
]
userRouter.push(...router)

// 管理员路由
router = [
  {
    path: '/home',
    component: Layout,
    meta: {
      title: '首页',
      icon: 'el-icon-house'
    },
    children: [{
      path: '',
      component: () => import('@/views/home/adminHome'),
      name: 'home',
      meta: {
        title: '首页',
        icon: 'el-icon-house'
      }
    }]
  },

  {
    path: '/user',
    component: Layout,
    name: 'user',
    meta: {
      title: '用户中心',
      icon: 'el-icon-user'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/user/index'),
        name: 'index',
        meta: {
          title: '用户中心',
          icon: 'el-icon-user'
        }
      }
    ]
  },

  {
    // hidden: true,
    path: '/team',
    component: Layout,
    name: 'user',
    meta: {
      title: '团队详情',
      icon: 'el-icon-user'
    },
    children: [
      {
        path: 'detail',
        component: () => import('@/views/team/detail'),
        name: 'detail',
        meta: {
          title: '团队详情',
          icon: 'el-icon-user'
        }
      }
    ]
  },
  {
    path: '/example',
    component: Layout,
    name: 'example',
    meta: {
      title: '实例&接口',
      icon: 'el-icon-star-off'
    },
    children: [{
      path: '',
      component: () => import('@/views/example/index'),
      name: 'index',
      meta: {
        title: '实例',
        icon: 'el-icon-star-off'
      }
    },
    {
      path: 'interface',
      component: () => import('@/views/interface/index.vue'),
      name: 'index',
      meta: {
        title: '接口',
        icon: 'el-icon-magic-stick'
      }
    }
    ]
  },
  {
    path: '/organization-authority',
    component: Layout,
    name: 'team',
    meta: {
      title: '组织权限',
      icon: 'el-icon-school'
    },
    children: [{
      path: '',
      component: () => import('@/views/organization-authority/index'),
      name: 'index',
      meta: {
        title: '组织权限',
        icon: 'el-icon-school'
      }
    }]
  }
]
adminRouter.push(...router)

let userUseRoute = []

var notRouter = {
  '/login': 1
}

export function getRouterData(router, route) {

  if (route.path && notRouter[route.path] === 1) {
    return
  }

  const userInfoString = localStorage.getItem('userInfo')

  // 如果登录了,添加响应的路由
  if (userInfoString !== null) {
    if (userUseRoute.length) {
      return
    }
    const userInfo = JSON.parse(userInfoString)
    const data = userInfo['uiType'] === 'admin' ? adminRouter : userRouter
    userUseRoute = data
    router.options.routes = data
    router.addRoutes(data)
    router.push('home')
  } else {
    router.push('login')
  }

  return router
}

// 不可用路由，请自行配置顶部的管理员路由 && 用户路由
const myRoutes = [{
  path: '/home',
  component: Layout,
  children: [{
    path: '',
    component: home,
    meta: {
      title: '首页',
      icon: 'el-icon-house',
      affix: true

    }
  }]
},
  {
    path: '/user',
    component: Layout,
    name: 'user',
    meta: {
      title: '用户中心',
      icon: 'el-icon-user'
    },
    children: [{
      path: '',
      component: () => import('@/views/user/index'),
      name: 'index',
      meta: {
        title: '用户中心',
        icon: 'el-icon-user'
      }
    }]
  },
  {
    path: '/team',
    component: Layout,
    name: 'team',
    meta: {
      title: '团队',
      icon: 'el-icon-user'
    },
    children: [{
      path: '',
      component: () => import('@/views/team/index'),
      name: 'index',
      meta: {
        title: '团队',
        icon: 'el-icon-user'
      }
    }, {
      path: 'detail',
      hidden: true,
      component: () => import('@/views/team/detail'),
      name: 'index',
      meta: {
        title: '团队',
        icon: 'el-icon-user'
      }
    }]
  },
  {
    path: '/example',
    component: Layout,
    name: 'example',
    meta: {
      title: '实例&接口',
      icon: 'el-icon-star-off'
    },
    children: [{
      path: '',
      component: () => import('@/views/example/index'),
      name: 'index',
      meta: {
        title: '实例',
        icon: 'el-icon-star-off'
      }
    },
      //{
      //     path: 'interface',
      //     component: () => import('@/views/interface/index'),
      //     name: 'index',
      //     meta: {
      //       title: '接口',
      //       icon: 'el-icon-magic-stick'
      //     }
      //   }
    ]
  },
  {
    path: '/organization-authority',
    component: Layout,
    name: 'team',
    meta: {
      title: '组织权限',
      icon: 'el-icon-school'
    },
    children: [{
      path: '',
      component: () => import('@/views/organization-authority/index'),
      name: 'index',
      meta: {
        title: '组织权限',
        icon: 'el-icon-school'
      }
    }]
  },

  {
    path: '/test',
    component: Layout,
    name: 'test',
    meta: {
      title: '测试查看页面',
      icon: 'el-icon-school'
    },
    children: [{
      path: '',
      component: () => import('@/views/organization/example'),
      name: 'example',
      meta: {
        title: '实例',
        icon: 'el-icon-school'
      }
    }]
  }
]

export {
  myRoutes
}
