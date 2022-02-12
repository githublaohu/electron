/**
 * 登录模块
 * 默认路由 注意命名规则 ：views目录+文件名,path：/文件名
 */
import login from "@/views/system/login/";

export default [
  {
    path: "/login",
    component: login,
    hidden: true,
  },
];
