package com.cloud.microservice.admin.feign;

import com.cloud.common.security.consts.IgnoreSecurityConstant;
import com.cloud.microservice.common.core.consts.ServiceNameConstants;
import com.cloud.microservice.common.core.vo.R;
import com.cloud.microservice.admin.feign.factory.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteUserService",
        value = ServiceNameConstants.ADMIN_SERVICE,
        fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    @GetMapping("/users/{userName}")
    R getUserInfoByUserName(@PathVariable("userName") String userName,
                            @RequestHeader(IgnoreSecurityConstant.REQUEST_FROM) String from);
}
