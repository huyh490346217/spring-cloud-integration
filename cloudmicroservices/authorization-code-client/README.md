# spring cloud integration

#### 介绍
spring ，authorization_code client 代码示例

#### 软件架构
软件架构说明

#### 功能列表
### 新增授权码（authorization_code）模式使用说明

1. 尝试直接访问 user 信息

```http
http://localhost:10101/users/1
```

提示需要认证：

```xml
<oauth>
	<error_description>
		Full authentication is required to access this resource
	</error_description>
	<error>
		unauthorized
	</error>
</oauth>
```

2. 尝试获取授权码

```http
http://localhost:10100/oauth/authorize?client_id=aiqiyi&response_type=code&redirect_uri=http://localhost:10102/clients/tokens/redirect
```

接着被主过滤器拦截，302 跳转到登录页，因为 /oauth/authorize 端点是受保护的端点，必须登录的用户才能申请 code。

3. 输入用户名和密码

username=250577914

passpord=123456

如上用户名密码是交给 SpringSecurity 的主过滤器用来认证的

4. 登录成功后，真正进行授权码的申请

oauth/authorize 认证成功，会根据 redirect_uri 执行 302 重定向，并且带上生成的 code，注意重定向到的是 8001 端口，这个时候已经是另外一个应用了。

```http
localhost:10102/clients/tokens/redirect?code=xxxx
```

代码中封装了一个 http 请求，使得 aiqiyi 使用 restTemplate 向 qq 发送 token 的申请，当然是使用 code 来申请的，并最终成功获取到 access_token

```json
{
	"access_token":"9f54d26f-5545-4eba-a124-54e6355dbe69",
	"token_type":"bearer",
	"refresh_token":"f7c176a6-e949-41fa-906d-0dedb0f0c1f7",
	"expires_in":42221,
	"scope":"get_user_info get_fanslist"
}
```

5. 携带 access_token 访问 qq 信息

```http
http://localhost:10101/users/1?access_token=9f54d26f-5545-4eba-a124-54e6355dbe69
```

正常返回信息

> 第 2，3 步为什么需要登录？因为资源服务器和认证服务器在一个应用之中，所以 SpringSecurity 的主过滤器链和 oauth2 的相关过滤器链可能会引发冲突，需要特别注意。
>
> 不想登录之后获取授权码可不可行？暂时博主还没有找到很好的方法做到：在同一个应用中不登录获取授权码，有实现的朋友欢迎发起 pull request。
>
> 注意：一次请求只会经过唯一一个过滤器链，详情见 FilterChainProxy 的实现。


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


#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
