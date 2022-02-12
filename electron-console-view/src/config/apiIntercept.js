/** *
 * easy api 全局配置以及拦截
 */
import { Message } from "element-ui";
import store from "@/store";
import log from "@/common/plugin/log";
import { showLoading, hideLoading } from "@/common/plugin/fetch/loading";

// 记录和显示错误
function errorLog(error, onClose = function () {}) {
  // 添加到日志打印到控制台
  if (process.env.NODE_ENV === "development") {
    log.danger(">>>>>> Error >>>>>>");
    console.log(error);
  }
  // 显示提示
  Message({
    message: error.message,
    type: "error",
    duration: 5 * 1000,
    onClose,
  });
}
// 创建一个错误
function errorCreate(msg) {
  const error = new Error(msg);
  errorLog(error);
  throw error;
}

/**
 * 本地接口请求配置
 * @type {{HARD_MODE: boolean, WHITE_LIST: [], MOCK_MODE: boolean, BLACK_LIST: [], ACCESS_CODE: []}}
 */
export const globalFetchConfiguration = {
  mock: false, // 是否使用mock，全局有效（可选项）
  computed: {}, // 全局计算属性（可选项）
  headers: {}, // 全局头部（可选项）
  hooks: {
    // 全局钩子（可选项）
    afterFetch: [],
  },
  // loading: true,
  plugins: {
    // 注册吐司消息插件和加载动画插件（可选项）
    // toast(options) {
    //   console.log('===toast====',options);
    //   showLoading();
    //   return e => hideLoading();
    // },
    loading(options) {
      console.log("===loading====", options);
      showLoading();
      return () => {
        hideLoading();
      };
    },
  },
};
/**
 * 请求拦截-发送前
 */
export function interceptRequestSuccess(config) {
  // console.log('===config===',config);
  // todo 超时处理
  // 在请求发送之前处理
  const oidcInfo = store ? store.getters.oidcInfo : undefined;
  if (oidcInfo) {
    config.headers.Authorization = `${oidcInfo.token_type} ${oidcInfo.access_token}`;
  }
  return config;
}

export function interceptRequestFail(error) {
  // console.warn('=interceptRequestFail==', error);
  // 对请求错误处理
  errorLog(error);
  console.log(error); // for debug
  return Promise.reject(error);
}

/**
 * 响应拦截
 */
export async function interceptResponseSuccess(response) {
  // console.warn('--------interceptResponseSuccess--------',response);
  // todo api 错误拦截以及过滤配置
  // todo 登陆过期判断
  // dataAxios 是 axios 返回数据中的 data
  const dataAxios = response.data;
  // 这个状态码是和后端约定的,code为非success是抛错 可结合自己业务进行修改
  const { code } = dataAxios;
  // todo 根据实际系统 code 进行判断
  if (code === undefined) {
    // 如果没有 code 代表这不是项目后端开发的接口
    return response; // easyapi 坑点，必须返回 response
  } else {
    // 有 code 代表这是一个后端接口 可以进行进一步的判断
    switch (code) {
      case 200:
        // [ 示例 ] code === 0 代表没有错误 dataAxios.data
        return response; // easyapi 坑点，必须返回 response
      case "xxx":
        // [ 示例 ] 其它和后台约定的 code
        errorCreate(`[ code: xxx ] ${dataAxios.msg}: ${response.config.url}`);
        break;
      default:
        // 不是正确的 code
        errorCreate(`${dataAxios.msg}: ${response.config.url}`);
        break;
    }
  }
}

export async function interceptResponseFail(error) {
  // console.error('--------interceptResponseFail--------',error);
  if (error && error.response) {
    switch (error.response.status) {
      case 400:
        error.message = "请求错误";
        break;
      case 401:
        error.message = "未授权，请登录";
        break;
      case 403:
        error.message = "拒绝访问";
        break;
      case 404:
        error.message = `请求地址出错: ${error.response.config.url}`;
        break;
      case 408:
        error.message = "请求超时";
        break;
      case 500:
        error.message = "服务器内部错误";
        break;
      case 501:
        error.message = "服务未实现";
        break;
      case 502:
        error.message = "网关错误";
        break;
      case 503:
        error.message = "服务不可用";
        break;
      case 504:
        error.message = "网关超时";
        break;
      case 505:
        error.message = "HTTP版本不受支持";
        break;
      default:
        break;
    }
  }
  const tips = error.desc || error.message;
  error.message =
    error && error.toString().indexOf("timeout") !== -1 ? "网络超时" : tips;

  // 登录过期判断,退出登录
  if (error && error.response && error.response.status === 401) {
    errorLog(error, () => {
      // todo 退出登陆
    });
  } else {
    errorLog(error);
  }
  console.log("err:" + error); // for debug
  return Promise.reject(error);
}
