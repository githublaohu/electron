import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/zh-CN'// 设置语言
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/styles/index.scss' // global css
import D2Crud from '@d2-projects/d2-crud'

import App from './App'
import router from './router'
import store from './store'
import '@/assets/icons' // icon
import '@/permission' // 权限控制

Vue.use(ElementUI, { locale })
Vue.use(D2Crud)

Vue.prototype.getOrganization = function() {
  return sessionStorage.getItem('organization')
}

Vue.config.productionTip = false

// 全局注册挂载动作绑定组件，实现指令式使用动作绑定插件，可自行进一步封装
import ActionBind from '@/components/Action/bind' // 引用组件
ActionBind.instance = () => {
  const instance = new Vue({
    render(h) {
      return h(ActionBind, {})
    }
  })

  instance.$mount()

  // 将instance.$el放到想放的位置
  document.body.appendChild(instance.$el)
  return instance.$children[0]
}
const instance = ActionBind.instance()

Vue.prototype[`$actionBind`] = instance['handleCreate']

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
  // created() {
  //   getRouterData(this.$router,this.$route)
  // }
})

console.log(`APP_ENV:${process.env.APP_ENV}, NODE_ENV:${process.env.NODE_ENV}`)
