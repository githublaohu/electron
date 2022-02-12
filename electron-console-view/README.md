# tpl-admin-vue

中后台 vue+element+tm-cli 系统模板项目

#### node 环境安装必要工具：tm-cli [工具文档](http://4g.gitee.io/tm-cli/)

#### 环境版本要求

- tm-cli v0.2.0-beta.10 +
- node v10.15.3 +
- npm v6.4.1 +
- vue 2.5.17
- vue-template-compiler: 2.5.17

## 安装依赖开发

```bash
# 全局安装tm-cli
npm i -g tm-cli

# 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 开启本地api mock localhost:8900服务
npm run mock

# 开启开发服务 localhost:9090
npm run dev
```

### 发布打包

```
npm run build
```

## 目录结构

```
├─mock                               // 本地模拟数据服务
├─src                                // 源代码
│  ├─api                             // 所有请求
│  ├─assets                          // 主题 字体等静态资源
│  │  ├─401_images
│  │  ├─404_images
│  │  ├─icons                        // 项目所有 svg icons
│  │  └─styles
│  ├─config                           // 项目api请求等配置
│  ├─common
│  │  ├─directive                    // 全局指令
│  │  ├─filters                      // 全局 filter
│  │  ├─utils                        // 全局公用方法
│  │  └─vendor                       // 第三方扩展库资源
│  ├─components                      // 全局公用组件
│  ├─router                          // 路由
│  ├─store                           // 全局 store管理
│  └─views                           // 所有页面
└─static                             // 第三方不打包资源

```

#### 注意！

- api 是基于前端统一请求库 [@xsyx/easy-api-h5](https://www.npmjs.com/package/@xsyx/easy-api-h5) (包含了 axios 请求库) 定义,

```
// 后端api 返回响应结果必须是以下格式
{
  "rspCode": "success",
  "rspDesc": '',
  "data": {}
}
```

- config 文件夹内包含了 应用的所有配置项目：路由拦截器、请求拦截器、常量配置
