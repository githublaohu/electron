import Vue from "vue";
import "normalize.css/normalize.css"; // A modern alternative to CSS resets
import ElementUI from "element-ui";
import locale from "element-ui/lib/locale/lang/zh-CN"; // 设置语言
import "element-ui/lib/theme-chalk/index.css";
import "element-ui/lib/theme-chalk/display.css"; // css响应隐藏类
import "./assets/styles/index.scss"; // global css
import "@xsyx/x-monitor-web";
// 组件
import HanzoElement from "@xsyx/hanzo-element";
import "@xsyx/hanzo-element/dist/hanzo.min.css";
import App from "./App";
import router from "./router";
import store from "./store";
// api
import api from "./api";
import log from "./common/plugin/log";

Vue.use(ElementUI, { locale, size: "small" });
Vue.use(HanzoElement);
Vue.config.productionTip = false;
Vue.prototype.$api = api; // api 全局挂载
Vue.prototype.$log = log;

// eslint-disable-next-line no-new
new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
console.log(`APP_ENV:${process.env.APP_ENV}, NODE_ENV:${process.env.NODE_ENV}`);

// 错误上报
// if(process.env.NODE_ENV ==='production'){
//   try {
//     new XMonitorWeb({
//       key:'', // 应用唯一key（必填）
//       url:"https://flog.xsyxsc.com/r", // 错误上报地址（必填）
//     })
//   }catch (e) {
//     console.warn('XMonitorWeb err:',e)
//   }
// }
