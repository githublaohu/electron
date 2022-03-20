/**
 * 应用配置
 */

function createDefaultView(){
  var defaultObject = {};
  defaultObject.data = {};
  defaultObject.methods={};
  defaultObject.data.pagination={currentPage: 1,pageSize: 10,total: 0};
  defaultObject.data.form={};
  defaultObject.data.columns = {};
  defaultObject.data.list=[];
  defaultObject.data.dialogVisible=false;
  defaultObject.data.dialogTitle="新增";
  defaultObject.data.isView=false;
  return defaultObject;
}
window.createDefaultView = createDefaultView;

// 后端注入配置
let INJECTION_CONFIG = {}
const default_config = {
  dev: {
    urlPrefix: 'http://127.0.0.1:9090/', // api请求前缀
    uploadUrl: 'http://127.0.0.1:9090/api/uploadImgs' // 上传地址
  },
  // 开发联调
  union: {
    urlPrefix: '',
    uploadUrl: ''
  },
  // 测试
  test: {
    "urlPrefix": "",
    "uploadUrl": ""
  },
  // 正式部署
  prod: {
    urlPrefix: '', // api请求前缀
    uploadUrl: '' // 上传地址
  }
};
// 开发环境cookie模拟
let DEV_COOKIE_MOCK='';
// 本地开发模拟api
const IS_MOCK = process.env && process.env.MOCK_ENV=='open'?true:false;

switch (process.env.APP_ENV) {
  case 'development':
    INJECTION_CONFIG = Object.assign({}, default_config.dev);
    break;
  case 'union':
    INJECTION_CONFIG = Object.assign({}, default_config.union, g_config)
    break;
  case 'test':
    INJECTION_CONFIG = Object.assign({}, default_config.test, g_config)
    break;
  case 'production':
    INJECTION_CONFIG = Object.assign({}, default_config.prod, g_config)
    break;
  default:
    INJECTION_CONFIG = Object.assign({}, default_config.prod, g_config)
    console.warn('process.env.APP_ENV error!')
}
// 上传配置 uploadImgs
const UPLOAD_CONFIG = {
  uploadUrl: INJECTION_CONFIG.uploadUrl,
  uploadDir: {
    systemAdImg: 'systemAdImg',
    systemFootNavImg: 'systemFootNavImg'
  }
}
// api 请求地址
const API_BASE_URL = INJECTION_CONFIG.urlPrefix;

console.log(`injection_config:`, INJECTION_CONFIG);
console.log(process.env);

export {
  API_BASE_URL,
  UPLOAD_CONFIG,
  IS_MOCK,
  DEV_COOKIE_MOCK
}
