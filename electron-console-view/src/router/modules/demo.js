/**
 * demo 路由模块
 */
import layout from "@/components/layout/";
import normal from "@/views/demo/normal";
import multiFilter from "@/views/demo/multiFilter";

export default [
  {
    path: "/demo",
    component: layout,
    redirect: "noRedirect",
    meta: {
      title: "常见页面",
      icon: "he-icon-dashboard",
    },
    children: [
      {
        path: "multiFilter",
        name: "multiFilter",
        component: multiFilter,
        meta: {
          title: "多条件列表",
          icon: "he-icon-international",
        },
      },
      {
        path: "normal",
        name: "normal",
        component: normal,
        meta: {
          title: "常规列表",
          icon: "he-icon-international",
        },
      },
      // {
      //   path: 'list',
      //   name: 'demoList',
      //   component: importPage('demo/home'),
      //   meta: {
      //     title: 'list',
      //     icon: 'he-icon-tree-table',
      //   },
      //   redirect: 'noRedirect',
      //   children: [
      //     {
      //       path: 'home',
      //       name: 'listHome',
      //       component: importPage('demo/home'),
      //       meta: {
      //         title: 'list home',
      //         icon: 'he-icon-guide',
      //       },
      //     },
      //     {
      //       path: 'form',
      //       component: importPage('demo/home'),
      //       name: 'listForm',
      //       meta: {
      //         title: 'list form',
      //         icon: 'he-icon-form',
      //       },
      //     },
      //   ],
      // },
    ],
  },
];
