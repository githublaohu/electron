# tpl-admin-vue

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

## 安装依赖开发
- 必要的环境: node8.12.0+，npm6.4.1+

```bash
# 全局安装tm-cli
npm i -g tm-cli

# 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 开启本地api mock localhost:5000服务
npm run mock:server

# 开启开发服务 localhost:9090
npm run dev
```
### 发布打包

```
# 打包联调环境
npm run build:union

# 打包测试环境
npm run build:test

# 打包正式生产环境
npm run build

```

#### 注意修改地方
- /common/utils/request.js 每个项目对应的api请求状态码不相同，注意修改里面的拦截器
- tm.config.js 为tm-cli 自定义配置项目 proxyTable 为常用开发api代理转发
- http://127.0.0.1:9090/#/demo 为不带侧边栏页面
- http://127.0.0.1:9090/#/dashboard 带侧边栏页面(具体配置参考路由)

#### 模板参考文档
- [路由文档](https://panjiachen.github.io/vue-element-admin-site/zh/guide/essentials/router-and-nav.html#%E9%85%8D%E7%BD%AE%E9%A1%B9)
