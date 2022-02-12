/**
 * 路由实例化，拦截器配置
 */
import Vue from "vue";
import Router from "vue-router";
import { routerBeforeEach, routerAfterEach } from "@/config/routerIntercept";
import Routes from "./routes";

Vue.use(Router);

// 注入默认配置、路由表
const index = new Router({
  routes: Routes,
});
// 注入路由拦截器
index.beforeEach(routerBeforeEach);
index.afterEach(routerAfterEach);

export default index;
