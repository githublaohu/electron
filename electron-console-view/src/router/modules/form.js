/** *
 * description: 表单
 * Author: ricopter@qq.com
 * Data: 2020/3/30
 * Time: 21:19
 * */
import layout from "@/components/layout/";
import formValidateNormal from "@/views/form/validateNormal";
import formValidatePromise from "@/views/form/validatePromise";

export default [
  {
    path: "/form",
    component: layout,
    redirect: "noRedirect",
    meta: {
      title: "form",
      icon: "he-icon-form",
    },
    children: [
      {
        path: "validateNormal",
        name: "formValidateNormal",
        component: formValidateNormal,
        meta: {
          title: "form-validate-normal",
        },
      },
      {
        path: "validatePromise",
        name: "formValidatePromise",
        component: formValidatePromise,
        meta: {
          title: "form-validate-promise",
        },
      },
    ],
  },
];
