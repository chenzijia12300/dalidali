# dalidali（🕊了）

[![](https://img.shields.io/badge/dalidali-后端项目-green.svg)](https://github.com/chenzijia12300/dalidali)
[![](https://img.shields.io/badge/vue-dalidali-前端项目-green.svg)](https://github.com/chenzijia12300/vue-dalidali)
[![](https://img.shields.io/badge/个人博客-学习笔记-green.svg)](http://blog.kingdreamstudio.com/)
![](https://img.shields.io/badge/QQ号-771240658-green.svg)

### 项目介绍
dalidali是一个模仿哔哩哔哩(bilibili)视频网站的后端系统，在技术上选择了Spring Boot,Spring Cloud,Mybatis Plus,Docker,RabbitMQ,Elasticsearch等基本技术。

### 关联项目
[Android](https://github.com/brokes6/D-BiliBili)
### 项目结构图
![dalidali项目结构图](https://github.com/chenzijia12300/dalidali/blob/master/%E5%93%92%E7%90%86%E5%93%92%E7%90%86%E7%BB%93%E6%9E%84%E5%9B%BE.jpg)

### 项目结构
|模块|简介|
|----|----|
|common|工具类及通用代码模块|
|user|用户模块|
|video|视频模块|
|comment|评论/回复模块|
|gateway|基于Spring Cloud Zuul的网关服务|
|register|基于Spring Cloud Eureka的注册中心|
|admin|管理员模块|

### 开发环境
|工具|版本号|
|----|----|
|JDK|1.8|
|MySQL|8.0.15|
|redis|3.0.503|
|idea|2019.1.1|
|RabbitMQ|3.7.17|
|docker|未知|


### 所用技术
|技术|简介|
|----|----|
|Spring Cloud| 微服务架构 |
|Spring Boot| 简化新Spring应用的初始搭建以及开发过程|
|Mybatis-Plus| Mybatis的增强工具包（JPA+Mybatis）|
|Druid|阿里开源数据连接池（号称世界第一性能连接池）|
|Swagger2|API文档|
|Docker|应用容器引擎|
|Redis|基于内存的高性能Key-Value数据库|
|MinIO|开源协议的对象存储服务|
|RabbitMQ|消息队列|
|MySQL|关系型数据库管理系统|
|lombok|实用工具|

### 页面截图

![页面截图1](https://github.com/chenzijia12300/dalidali/blob/master/1.png)

![页面截图2](https://github.com/chenzijia12300/dalidali/blob/master/2.png)

![页面截图3](https://github.com/chenzijia12300/dalidali/blob/master/3.png)


![页面动图1](https://github.com/chenzijia12300/dalidali/blob/master/2021-01-16-00-04-00.gif)


![页面动图2](https://github.com/chenzijia12300/dalidali/blob/master/2021-01-16-00-06-16.gif)


### 项目后续
正在被社会毒打，暂时没时间更新，后面有可能加一些新的组件把，例如
- Prometheus埋点？
- 从Maven改为Gradle？
- 优化一下文档？
