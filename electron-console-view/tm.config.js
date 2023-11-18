module.exports = {
  "description": "tm demo config",
  "author": "ricopter@qq.com",
  "webpackConfig":{
    "dev":{
      // 本地代理
      "devServerConfig":{
        "proxy": {
          "/": {
            "target": "http://101.42.236.13:9991/lamp/electron/",
            // "target": "http://101.42.236.13:9991/lamp/electron/",
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
