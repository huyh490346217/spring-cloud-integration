package com.cloud.authserver.config;

import com.cloud.common.security.consts.CustomizeSecurityConstant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManagerBean;

    //UserDetailsService is required.
    private UserDetailsService userDetailsService;

    private static final String QQ_RESOURCE_ID = "qq";

    private RedisConnectionFactory redisConnectionFactory;

    private PasswordEncoder passwordEncoder;

    private final DataSource dataSource;

    private final ClientDetailsService clientDetailsService;

//    @Bean
//    public ClientDetailsService clientDetailsService(DataSource dataSource){
//        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
//        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
//        return clientDetailsService;
//    }

    //配置客户端详细信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 从内存中读取客户端信息
        // @formatter:off
//        clients.inMemory().withClient("aiqiyi")
////                .resourceIds(QQ_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                //modify scope add server 授权范围， 可以设置成所有的
//                .scopes("get_user_info", "get_fanslist", "server", "amanyworkdisok")
//                .secret(BCrypt.hashpw("secret", BCrypt.gensalt()))
//                .redirectUris("http://localhost:10102/clients/tokens/redirect")
//                .autoApprove(true)
//                .autoApprove("get_user_info")
//                .and()
//                .withClient("youku")
//                .resourceIds(QQ_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("get_user_info", "get_fanslist")
//                .secret(BCrypt.hashpw("secret", BCrypt.gensalt()))
//                .redirectUris("http://localhost:8082/youku/qq/redirect");
        // @formatter:on


        // 从DB中读取客户端信息
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        clients.withClientDetails(clientDetailsService);
    }

    //token 的存储方式
    @Bean
    public TokenStore tokenStore(){
        //内存存储token
//        return new InMemoryTokenStore();

        // 需要使用 redis 的话，放开这里
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CustomizeSecurityConstant.CLOUD_PREFIX + CustomizeSecurityConstant.OAUTH_PREFIX);
        return tokenStore;
    }


//    @Bean
//    public ApprovalStore approvalStore() {
//        TokenApprovalStore store = new TokenApprovalStore();
//        store.setTokenStore(tokenStore());
//        return store;
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManagerBean)
                .userDetailsService(userDetailsService) // 这里就算是内存中的也要加上,否则在refresh token的时候会报userdetailservice找不到异常
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    //token 端点的安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        oauthServer.realm(QQ_RESOURCE_ID).allowFormAuthenticationForClients();
        oauthServer
//                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //资源服务器检查token这个很重要，如果没有就会报403
                .allowFormAuthenticationForClients(); //表单认证，申请token
    }

}