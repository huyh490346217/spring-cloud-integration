package com.cloud.microservicesuser.config;

import com.cloud.common.security.annotation.IgnoreSecurity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 资源服务器对外直接暴露URL
 * 一部分是yml 中配置，另外是通过注解添加的
 */
@Data
@Slf4j
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${security.oauth2.client.ignore-urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.client")
public class PermitAllUrlPropertiesTest implements InitializingBean {
    private List<String> ignoreUrls = new ArrayList<String>();

    @Autowired
    private WebApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mappingHandlerMapping.getHandlerMethods();

        if (null != map) {
            map.keySet().forEach(requestMappingInfo -> {
                HandlerMethod handlerMethod = map.get(requestMappingInfo);

//                Pattern pattern = Pattern.compile("\\{(.*?)\\}");
                String p = "\\{(.*?)\\}";

                //获取方法上边的注解 替代path variable 为 *
                IgnoreSecurity method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), IgnoreSecurity.class);
                log.debug("IgnoreSecurity method", method);
                Optional.ofNullable(method)
                        .ifPresent(inner -> {
                            log.debug("method ", inner);
                            requestMappingInfo.getPatternsCondition().getPatterns()
                                    .forEach(url -> {
                                        log.debug("url ", url, " replace url " + url.replaceAll(p, "*"));
                                        ignoreUrls.add(url.replaceAll(p, "*"));
                                    });
                        });

                IgnoreSecurity controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), IgnoreSecurity.class);
                log.debug("IgnoreSecurity controller", controller);
                Optional.ofNullable(controller)
                        .ifPresent(inner -> {
                                    log.debug("IgnoreSecurity controller inner" + inner);
                                    requestMappingInfo.getPatternsCondition().getPatterns()
                                            .forEach(url -> {
                                                log.debug("controller url ", url, " replace url " + url.replaceAll(p, "*"));
                                                ignoreUrls.add(url.replaceAll(p, "*"));
                                            });
                                }
                        );
            });
        }
    }
}

