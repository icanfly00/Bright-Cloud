# fast-cloud
基于Spring Cloud Hoxton.SR6、Spring Cloud OAuth2 &amp; Spring Cloud Alibaba &amp; Element 微服务权限系统，开箱即用。
### fast-cloud 微服务权限系统
![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springcloud-Hoxton.RELEASE-yellow.svg?style=flat-square](https://img.shields.io/badge/springcloud-Hoxton.RELEASE-yellow.svg?style=flat-square)
![https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square](https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square)
![https://img.shields.io/badge/springboot-2.3.1.RELEASE-brightgreen.svg?style=flat-square](https://img.shields.io/badge/springboot-2.3.1.RELEASE-brightgreen.svg?style=flat-square)
![https://img.shields.io/badge/vue-2.6.10-orange.svg?style=flat-square](https://img.shields.io/badge/vue-2.6.10-orange.svg?style=flat-square)

fast-cloud是一款使用Spring Cloud Hoxton.SR6、Spring Cloud OAuth2 & Spring Cloud Alibaba构建的低耦合权限管理系统，前端（fast-cloud-web）采用vue-element-admin构建。该系统具有如下特点：

序号 | 特点
---|---
1 | 前后端分离架构，客户端和服务端纯Token交互； 
2 | 认证服务器与资源服务器分离，方便接入自己的微服务系统
3 | 集成SpringBootAdmin，Skywalking APM
4 | 网关限流，网关黑名单限制，网关日志（WebFlux编程实践）
5 | 微服务Docker化，使用Docker Compose一键部署，K8S集群
6 | 社交登录，认证授权，数据权限，前后端参数校验，Starter开箱即用等
7 | Doc Starter，几行配置自动生成系统api接口文档
8 | OAuth2 4种模式+刷新令牌模式，提供5种获取系统令牌方式
9 | 提供详细的导入教程、使用教程和开发教程，对于想深入了解的用户还提供了搭建教程

### 文档与教程

> 在这浮躁的社会里，还请您耐心阅读文档，99%的问题在文档中都能找到答案。

文档 | 地址
---|---
项目导入教程 | []()
管理页面使用教程 | []()
系统常见问题解答 | []()
系统更新日志 | []()

### 系统架构

<table>
  <tr>
    <td align="center" style="background: #fff"><b>fast-cloud</b></td>
  </tr>
  <tr>
    <td align="center" style="background: #fff"><img src="docs/images/Oauth2.0授权码模式时序图.png"/></td>
  </tr>
   <tr>
    <td align="center" style="background: #fff"><b>fast Skywalking APM</b></td>
  </tr>
  <tr>
    <td align="center" style="background: #fff"><img src="docs/images/skywalking_apm.png"/></td>
  </tr>
   <tr>
    <td align="center" style="background: #fff"><b>fast Kubernetes</b></td>
  </tr>
  <tr>
    <td align="center" style="background: #fff"><img src="docs/images/fast-k8s.png"/></td>
  </tr>
</table>

### 项目地址

 平台  | fast-cloud（后端） |fast-cloud-web（前端）
---|---|---
GitHub | [https://github.com/JacksonTu/fast-cloud](https://github.com/JacksonTu/fast-cloud) |[https://github.com/JacksonTu/fast-cloud-web](https://github.com/JacksonTu/fast-cloud-web)
Gitee  | [https://gitee.com/tumao2/fast-cloud](https://gitee.com/tumao2/fast-cloud) |[https://gitee.com/tumao2/fast-cloud-web](https://gitee.com/tumao2/fast-cloud-web)

### 演示地址

演示地址：[]()

演示环境账号密码：

账号 | 密码| 权限
---|---|---
test | 123456 | 注册账户，拥有查看权限

本地部署账号密码：

账号 | 密码| 权限
---|---|---
admin | 123456 |超级管理员，拥有所有增删改查权限
scott | 123456 | 注册账户，拥有查看，新增权限（新增用户除外） 
jackson | 123456 |系统监测员，负责整个系统监控模块

网关管理用户账号密码：

账号 | 密码| 权限
---|---|---
jackson | 123456 |网关管理模块查看权限
admin | 123456 |网关管理模块所有权限

APM平台相关账号密码：

平台 | 账号| 密码
---|---|---
fast-admin | admin |123456
febs-tx-manager | 无 | 123456

### 服务模块

FEBS模块：

服务名称 | 端口 | 描述
---|---|---
FastSystemApplication| 8000 | 微服务子系统，系统核心模块 
FastUAAApplication| 8001 | 微服务子系统，认证模块 
FastTestpplication|8002 | 微服务子系统，Demo模块
FastGeneratorApplication|8003 | 微服务子系统，代码生成模块
FastJobApplication|8004 | 微服务子系统，任务调度模块
FastGatewayApplication|8005|微服务网关
FastAdminApplication|8006|微服务监控子系统
FastTxManagerApplication|8007|微服务分布式事务控制器

第三方模块：

服务名称 | 端口 | 描述
---|---|---
Nacos| 8848 |注册中心，配置中心 
MySQL| 3306 |MySQL 数据库 
Redis| 6379 | K-V 缓存数据库 
Elasticsearch|9200 | 日志存储
Logstash|9400|日志收集
Kibana|9500|日志展示
Skywalking|11800、12800、8080|Skywalking APM

### 目录结构
```
├─components                       ------ 组件
│  ├─fast-common-core              ------ 系统核心依赖包
│  ├─fast-common-starter           ------ 系统核心自动装配starter
├─docs                  		   ------ 文档
│  ├─conf                          ------ 配置文件
│  ├─images                        ------ 图片
│  └─sql               			   ------ SQL脚本
├─platfrom                    	   ------ 平台服务
│  ├─fast-system-api                   ------ 微服务系统接口模块
│  ├─fast-system-service     		   ------ 微服务系统服务模块
│  ├─fast-uaa-service                  ------ 微服务认证中心
│  ├─
│  ├─
│  ├─
│  └─
├─service                   ------ 业务系统
│  ├─            
│  ├─            
│  ├─        
│  └─            

```
### 系统优化

```
-Xms512m -Xmx512m -Xmn340m -Xss1m -XX:+UseG1GC -XX:+PrintGCDetails
```



### 系统截图

<table>
  <tr>
     <td><img src=""/></td>
     <td><img src=""/></td>
  </tr>
  <tr>
     <td><img src=""/></td>
     <td><img src=""/></td>
  </tr>
  <tr>
     <td><img src=""/></td>
     <td><img src=""/></td>
  </tr>
  <tr>
     <td><img src=""/></td>
  </tr>
</table>

### 服务APM

#### [Skywalking APM]()

<table>
  <tr>
     <td width="50%" align="top"><img src="docs/images/skywalking_global.png"/></td>
     <td width="50%" align="top"><img src="docs/images/skywalking_service.png"/></td>
  </tr>
  <tr>
     <td width="50%" align="top"><img src="docs/images/skywalking_topology.png"/></td>
     <td width="50%" align="top"><img src="docs/images/skywalking_trace.png"/></td>
  </tr>
</table>

### 反馈交流

加入QQ群和大家一起~~交流~~吹水：

![qq](docs/images/QQ.png)
