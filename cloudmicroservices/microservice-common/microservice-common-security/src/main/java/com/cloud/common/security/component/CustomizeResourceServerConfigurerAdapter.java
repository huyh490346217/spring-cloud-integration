package com.cloud.common.security.component;

import com.cloud.common.security.config.PermitAllUrlProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@AllArgsConstructor
public class CustomizeResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId(QQ_RESOURCE_ID).stateless(true);
        resources.stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        permitAllUrlProperties.getIgnoreUrls().forEach(url-> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated()
                .and().csrf().disable();

////        super.configure(http);
//        // @formatter:off
//        http
//
////                .sessionManagement()
////                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
////                    .and()
////                .requestMatchers()
//                // 保险起见，防止被主过滤器链路拦截
////                .antMatchers("/qq/**")
////                .and()
////                .authorizeRequests().anyRequest().authenticated()
////                .and()
//                .authorizeRequests()
////                .antMatchers("/qq/info/**").access("#oauth2.hasScope('get_user_info')")
////                .antMatchers("/qq/fans/**").access("#oauth2.hasScope('get_fanslist')")
//                .anyRequest().authenticated()
////
////                .and()
////                //允许client code 方式访问auth server，获取token先写死url
////                .requestMatchers().antMatchers("/clients/tokens/redirect").and().authorizeRequests().anyRequest().permitAll()
//        ;
//        // @formatter:on
    }
}
