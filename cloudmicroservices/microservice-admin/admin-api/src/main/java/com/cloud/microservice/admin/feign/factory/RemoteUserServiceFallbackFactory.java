package com.cloud.microservice.admin.feign.factory;

import com.cloud.microservice.admin.feign.RemoteUserService;
import com.cloud.microservice.admin.feign.fallback.RemoteUserServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable) {
        RemoteUserServiceFallbackImpl remoteUserServiceFallback = new RemoteUserServiceFallbackImpl();
        remoteUserServiceFallback.setThrowable(throwable);
        return remoteUserServiceFallback;
    }
}
