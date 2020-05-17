# spring cloud integration

#### 介绍
spring cloud 整合，将spring security、 spring gateway、nacos、 feign、histrix整合到一起，

#### 软件架构
软件架构说明

#### 功能列表
1. spring security
  a. client 和user role anthority要放到数据库中控制
  b. 添加auth client service 实现拦截
  c. 添加 IgnoreSecurity 注解，在内部的service 之间调动接口，不需要走auth server
  d. 添加切面拦截 IgnoreSecurity 注解，辨识内部调用，需要在header 中添加特殊关键字检验才能使用
  e. 第三方客户端登录测试步骤详见  spring-cloud-integration\cloudmicroservices\authorization-code-client 中的readme 介绍

2. 添加全局异常处理

3. 添加 eureka server 使用 http://localhost:10003/ 访问 eureka 服务
4. 修改spring boot 和 spring cloud 版本不匹配的问题

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


