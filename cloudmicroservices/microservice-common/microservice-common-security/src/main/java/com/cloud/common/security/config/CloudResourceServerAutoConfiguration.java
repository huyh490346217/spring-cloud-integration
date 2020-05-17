package com.cloud.common.security.config;

import org.springframework.context.annotation.ComponentScan;

/**
 * 这里的 ComponentScan 扫描制定的目录下的所有文件，对象添加到spring的管理中
 * 在添加了Enablecustomizeresource server 注解后生效
 * 不用添加spring.factories 方式加载spring 管理中
 */
@ComponentScan("com.cloud.common.security")
public class CloudResourceServerAutoConfiguration {
}
