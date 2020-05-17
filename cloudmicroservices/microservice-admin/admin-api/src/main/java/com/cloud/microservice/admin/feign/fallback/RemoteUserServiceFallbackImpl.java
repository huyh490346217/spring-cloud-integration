package com.cloud.microservice.admin.feign.fallback;

import com.cloud.microservice.admin.feign.RemoteUserService;
import com.cloud.microservice.common.core.vo.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    @Setter
    private Throwable throwable;



    @Override
    public R getUserInfoByUserName(String userName, String requestFrom) {
        log.error("feign interface call failed, user name is ", userName, throwable);
        return null;
    }
}
