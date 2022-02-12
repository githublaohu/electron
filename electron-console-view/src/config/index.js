/**
 * 应用配置中心入口
 */
// import ck from "js-cookie";
import { switchAppEnv } from "@/common/utils";
import { IS_DEV_ENV } from "./constants";
// 开发环境
if (IS_DEV_ENV) {
  // ck.set('SESSION', 'ZjhiODEwNmEtNDZkYi00OGJiLWFiNDUtNmY4MjA3ODVkNTA4');
}
// 后端注入配置
/* eslint-disable */
let INJECTION_CONFIG = g_config;
const site_host = window.location.host;
// 默认配置
const config = {
  // 本地开发环境
  development: {
    staticUrl: "/",
    urlPrefix: `//${site_host}/api`, // api请求前缀
    uploadUrl: `//${site_host}/api/uploadImage`, // 上传地址
    // kyLinConfig sso 配置
    platformKey: "", // sso key
    ssoBaseUrl: "",
  },
  // 部署(正式/测试)
  production: {
    urlPrefix: "",
    uploadUrl: "",
    platformKey: "", // sso key // todo 正式环境
    ssoBaseUrl: "", // todo 正式环境
  },
};
// 应用环境切换配置
INJECTION_CONFIG = switchAppEnv(config, INJECTION_CONFIG);
// api 请求地址
const API_BASE_URL = INJECTION_CONFIG.urlPrefix;

console.log(process.env);
console.log("injection_config:", INJECTION_CONFIG);

export { API_BASE_URL, INJECTION_CONFIG };
