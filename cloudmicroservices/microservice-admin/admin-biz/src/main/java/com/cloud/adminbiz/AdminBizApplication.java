package com.cloud.adminbiz;

import com.cloud.common.security.annotation.EnableCustomizeResourceServer;
import com.cloud.microservice.common.core.annotation.EnableCustomizeFeignClients;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 启动 spring security resource
@EnableCustomizeResourceServer
//启动注册中心client
@EnableDiscoveryClient

//
//@EnableCustomizeFeignClients
public class AdminBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBizApplication.class, args);
    }

}
