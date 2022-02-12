/**
 * icons 演示 路由模块
 */
import layout from "@/components/layout/";
import iconsIndex from "@/views/icons/index";

export default [
  {
    path: "/icons",
    component: layout,
    redirect: "noRedirect",
    children: [
      {
        path: "index",
        name: "index",
        component: iconsIndex,
        meta: {
          title: "图标",
          icon: "he-icon-icon",
        },
      },
    ],
  },
];
