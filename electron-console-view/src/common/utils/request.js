import axios from 'axios'
import Qs from 'qs'
import { Message, Loading } from 'element-ui'
import Cookies from 'js-cookie'
import { API_BASE_URL,IS_MOCK,DEV_COOKIE_MOCK } from '@/config'
import { getToken, setToken } from '@/common/utils/auth' // 验权

// 全局的 axios 默认值1
axios.defaults.baseURL = API_BASE_URL
axios.defaults.timeout = 20000 // 20s 防止上传api超时
axios.defaults.withCredentials = true
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'
// axios.defaults.headers['Authorization'] = 'lamp de23af25-ff12-4890-aaa8-2342816c20b3'
// setToken('lamp de23af25-ff12-4890-aaa8-2342816c20b3')

let LOADING;
// 开发环境cookie模拟
if(process.env.APP_ENV==='development' && DEV_COOKIE_MOCK){
  Cookies.set('SESSION',DEV_COOKIE_MOCK);
}

/**
 * 本地mock服务改造
 * @param config
 */
function mockUseRequest (config) {
  // 是否开启mock
  if(IS_MOCK && process.env['NODE_ENV']==='development'){
    const currentUrl = config.url;
    const num=currentUrl.indexOf("?");
    let _body = ''
    if(config.method ==='post'){
      _body = Qs.stringify(config.data);
    }
    config.method = 'get';
    if(num !==-1){
      const qUrl=currentUrl.split('?');
      config.url = `${qUrl[0]}.json?${qUrl[1]}&${_body}`;
    }else {
      config.url = _body?`${currentUrl}.json?${_body}`:`${currentUrl}.json`;
    }
  }
}
/**
 * 拦截请求数据
 * Authorization lamp de23af25-ff12-4890-aaa8-2342816c20b3
 */
axios.interceptors.request.use(
  config => {
    // if (store.getters.token) {
    //   environment.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    // }
	console.log('toke==>', getToken())
    if (getToken()) {
      config.headers['Authorization'] =  getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }

    if (!LOADING) {
      LOADING = Loading.service({
        fullscreen: true,
        text: '拼命加载中...',
        background: 'rgba(255,255,255,0)'
      })
    }
    // 开发环境本地mock API
    mockUseRequest(config);
    // 超时处理
    setTimeout(() => {
      LOADING && LOADING.close()
      LOADING = null
    }, 2000)

    return config
  },
  error => {
    LOADING && LOADING.close()
    LOADING = null
    console.log(error) // for debug
    Promise.reject(error)
  }
);

// response 拦截器
axios.interceptors.response.use(
  response => {
    LOADING && LOADING.close()
    LOADING = null
    /**
     * code为非success是抛错 todo结合自己业务进行修改
     */
    const res = response.data
    const resStatus = response.status
    if( response.headers["authorization"] != null ){
        setToken(response.headers["authorization"]);
    }
    console.log(res)
    if (res.code !== 200) {
      Message({
        message: res.rspDesc || res.message || '出错了',
        type: 'error',
        duration: 5 * 1000
      })
      //todo 登陆过期判断
      return Promise.reject(response)
    } else {
      return response.data
    }
  },
  error => {
    console.log('err:' + error) // for debug
    const tips = error.rspDesc || error.message;
    let _msg = error && error.toString().indexOf('timeout')!==-1?'网络超时':tips;
    Message({
      message: _msg,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

/**
 * 默认表单提交方式 https://segmentfault.com/a/1190000015261229
 * Content-Type: application/x-www-form-urlencoded
 */
axios.postApi = function(url, params, opts = {}) {
  return axios.post(url,Qs.stringify(params), ...opts)
};
/**
 * json方式提交
 * Content-Type: application/json 、multipart/form-data
 */
axios.postJsonApi = function(url, params, opts = {}) {
  return axios.post(url, params, ...opts)
};
/**
 * get 方式请求
 */
axios.getApi = function(url, params, opts = {}) {
  return axios.get(url,{ params:params ,...opts})
};

export default axios
