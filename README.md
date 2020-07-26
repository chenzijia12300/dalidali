# dalidali（开发中）
### 项目介绍
dalidali是一个模仿哔哩哔哩视频网站的后端系统，在技术上选择了Spring Boot,Spring Cloud,Mybatis Plus,Docker,RabbitMQ,Elasticsearch等基本技术。

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
