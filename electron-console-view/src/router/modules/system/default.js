/**
 * 默认路由 注意命名规则 ：views目录+文件名,path：/文件名
 */
import layout from "@/components/layout/";
import defaultIndex from "@/views/system/default/";
import default404 from "@/views/system/default/404";
import default401 from "@/views/system/default/401";
import defaultRedirect from "@/views/system/default/redirect";

export default [
  // 默认页面
  {
    path: "",
    component: layout,
    children: [
      {
        name: "defaultIndex",
        path: "/",
        component: defaultIndex,
        meta: {
          title: "首页",
          icon: "el-icon-s-home",
          tips: "这是一个页面", // 页签提示，可选，如未设置则跟title一致
          // affix:true, // 是否固定页签不允许删除
          // aliveId: 'fullPath',
        },
      },
    ],
  },
  {
    name: "default404",
    path: "/404",
    component: default404,
  },
  {
    name: "default401",
    path: "/401",
    component: default401,
  },
  {
    path: "*",
    redirect: "/404",
  },
  {
    path: "/redirect",
    component: layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path(.*)",
        component: defaultRedirect,
      },
    ],
  },
];
