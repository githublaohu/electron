/**
 * echart 路由模块
 */
import layout from "@/components/layout/";
import echartIndexView from "@/views/demo/echart";

export default [
  {
    path: "/echart",
    component: layout,
    redirect: "noRedirect",
    meta: {
      title: "图表",
      icon: "el-icon-s-marketing",
    },
    children: [
      {
        path: "index",
        name: "echartIndex",
        component: echartIndexView,
        meta: {
          title: "图表演示",
          icon: "el-icon-s-data",
        },
      },
    ],
  },
];
