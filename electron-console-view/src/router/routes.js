/**
 * 路由表
 */
// 系统默认路由
import systemDefault from "./modules/system/default";
import systemLogin from "./modules/system/login";
// 业务路由
import demo from "./modules/demo";
import form from "./modules/form";
import icons from "./modules/icons";
import echartRoute from "./modules/echart";

// 路由导出
export default [
  ...systemDefault,
  ...systemLogin,
  ...demo,
  ...form,
  ...icons,
  ...echartRoute,
];
