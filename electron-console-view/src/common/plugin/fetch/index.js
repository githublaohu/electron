/**
 * @file 接口请求封装
 * */
// 引用包
import easyApi from "@xsyx/easy-api-h5";

// 引入api配置
import {
  interceptRequestSuccess,
  interceptRequestFail,
  interceptResponseSuccess,
  interceptResponseFail,
} from "@/config/apiIntercept";

const { axios } = easyApi();

/**
 * 拦截请求数据
 */
axios.interceptors.request.use(interceptRequestSuccess, interceptRequestFail);
/**
 * 拦截响应数据
 */
axios.interceptors.response.use(
  interceptResponseSuccess,
  interceptResponseFail
);

export default easyApi;
