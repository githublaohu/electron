/**
 * 页面路由拦截器配置、路由权限处理
 * // todo 白名单
 */
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import store from "@/store";
import Routes from "@/router/routes"; // 所有路由信息
import _ from "lodash";

NProgress.configure({ showSpinner: false });

export async function routerBeforeEach(to, from, next) {
  NProgress.start();
  // 全部路由
  if (
    !store.state.permission.routes ||
    store.state.permission.routes.length < 1
  ) {
    await store.dispatch("permission/$_initRoutes", _.cloneDeep(Routes));
  }
  // todo 配置权限拦截
  await store.dispatch("permission/$_asideMenu", _.cloneDeep(Routes));
  NProgress.done();
  next();
}

export function routerAfterEach() {
  NProgress.done();
}
