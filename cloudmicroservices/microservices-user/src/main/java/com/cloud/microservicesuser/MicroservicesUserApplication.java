package com.cloud.microservicesuser;

import com.cloud.common.security.annotation.EnableCustomizeResourceServer;
import com.cloud.microservice.common.core.annotation.EnableCustomizeFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCustomizeResourceServer
@EnableDiscoveryClient

// TODO 测试 feign 接口
@EnableCustomizeFeignClients
public class MicroservicesUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicesUserApplication.class, args);
    }

}
