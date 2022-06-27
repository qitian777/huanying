# huanying
#### 介绍

本项目为自建网站前后端分离后端服务器部分，项目完成版可参见：https://huanyingol.cn/

网站实现了影视信息的展示、分类、搜索功能及用户登录功能。

用户登录功能包括注册、用户信息及密码修改、聊天、收藏、评论等。



#### 各模块功能说明

generator：mybatis-plus代码生成

eureka-server：spring-cloud服务器

item-cloud-provider：用户收藏功能实现，同时是cloud生产者

item-server：影视信息的展示、分类、搜索，主要的非登录业务

mail-server：rabbitmq邮件异步发送

server-api：各模块通用类

user-server：用户相关功能



#### 软件架构

本项目基于前后端分离使用了springboot+jwt+vue的架构。后端服务器整合的主要项目及实现功能如下：

security：后端权限管理及token鉴权

redis：存储用户登录信息及邮箱验证码

validator：参数校验

RabbitMq: 异步邮件发送

websocket：在线聊天

mybatis-plus：代码生成、分页等数据库相关功能

jwt：前后端分离的身份验证

kaptcha：验证码

swagger：接口文档

commons-codec：前端传来的登录密码解密

cloud：收藏功能为单独模块，由spring-cloud实现与用户模块交互
