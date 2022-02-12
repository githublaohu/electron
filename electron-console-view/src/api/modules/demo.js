/**
 * @file 接口请求配置
 * @author 姚麟 <yaolin@xsyxsc.com>
 * @version 0.1.0
 * @copyright 兴盛优选 2020
 * */

export default {
  getList: {
    // 完整的访问方法名
    url: "/demo/list", // 请求的地址（必填）
    method: "POST", // 请求方法 post get（可选项）
    // 以下皆为可选项
    // mockUrl: 'http://auto.publish.xsyxsc.cn:3001/webApi/getProjects', // mock 地址（可选项）
    // mockData: { rspCode: 'success', data: [] }, // 静态 mock 数据
    // cached: '5000', // 表示接口在多久以内，缓存临时数据（可选项）
    // sensitive: '11', // 灵敏期，在这个时间段内，不会走缓存
    // concurrency: false, // 是否允许并发（可选项）
    // mock: true, // 是否访问 mock（可选项）， 若配置了全局mock 则仍然根据子项生效
    // fit: (rsp, params) => rsp,
    // // 拦截返回数据格式化,
    // repeat: 0, // 重试次数
    // feed: (rsp, params) => {
    //   // 补偿机制，如果重试无效，则进入兜底返回
    //   // 补偿机制
    // },
    // fail(rsp, params) {
    //   // 全局错误处理
    // },
  },
  getProduct: {
    url: "/demo/getProduct",
    method: "POST",
  },
  /** * 订单取消管理 ** */
  // 订单取消列表
  cancelList: {
    url: "/order/cancel/list",
    method: "POST",
  },
  // 订单详情查询
  cancelDetail: {
    url: "/order/cancel/getOrderItemsList",
    method: "POST",
  },
  // 售后类型
  afterSaleType: {
    url: "/order/cancel/getReasonType",
    method: "POST",
  },
  // 订单取消
  cancelSubmit: {
    url: "/order/cancel/doCancel",
    method: "POST",
  },
};
