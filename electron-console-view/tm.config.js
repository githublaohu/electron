module.exports = {
  "description": "tm demo config",
  "author": "ricopter@qq.com",
  "webpackConfig":{
    "dev":{
      // 本地代理
      "devServerConfig":{
        "proxy": {
          "/": {
            //"target": "http://120.78.148.188:9990/lamp/electron/",
            "target": "http://127.0.0.1:9991",
            "pathRewrite": {
              "^/": ""
            },
            "changeOrigin": true
          }
        }
      },
      "devtool":'source-map',

    },
    // 打包自定义配置
    "build": {

    }
  }
}
