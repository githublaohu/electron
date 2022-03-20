# electron
lampup小组下的atom项目是以流量与API为核心的业务型网关。主要是解决业务需求与API管理。

## 特性

* 支持多种注册中心( etcd , nacos, consul , eureka)

* 代理级别有：API，class，应用

* 支持多租户

* 支持功能绑定

## 功能列表

### 收集 

* 统计

* 请求记录

* 链路跟踪

### 识别

* 识别

### 路由

* 热备

* 隔离

* 路由

### 安全

* 认证与鉴权

* 参数校验

* 秒杀

* 限流与熔断

### RPC



### 注入

* 实例注入

* 接口注入

* 资源注入

### 结果处理

* 数据注入


## client

### java  electron-clinet 使用

在应用服务的pom.xml 加入下面配置

```xml
<dependency>
	<groupId>cn.lampup</groupId>
	<artifactId>electron-service-example</artifactId>
	<version>${revision}</version>
</dependency>

```

启动配置
```yaml
electron:
    registry: 127.0.0.1:2379
    applicationName: {项目名}
    userInfoClass: {class 完全路径}

```


## Installation

### 第一步安装 etcd

### 第二步安装 MySQL

### 第三步安装 electron-console

### 第四步安装 electron-core

### 第五步 启动业务 服务
