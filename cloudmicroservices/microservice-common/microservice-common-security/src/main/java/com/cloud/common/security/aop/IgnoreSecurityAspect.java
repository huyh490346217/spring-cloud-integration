package com.cloud.common.security.aop;

import com.cloud.common.security.annotation.IgnoreSecurity;
import com.cloud.common.security.consts.IgnoreSecurityConstant;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * 如果使用ignore security 注解，需要在header 中添加 from 关键字，后期可以from的值可以通过api 动态设置到redis 中获取
 * 不写死
 */
@Component
@AllArgsConstructor
@Aspect
@Slf4j
public class IgnoreSecurityAspect {
    private final HttpServletRequest request;

    @SneakyThrows
    @Around("@annotation(ignoreSecurity)")
    public Object around(ProceedingJoinPoint point, IgnoreSecurity ignoreSecurity) {
        String header = request.getHeader(IgnoreSecurityConstant.REQUEST_FROM);
        if (ignoreSecurity.value()
                && !IgnoreSecurityConstant.REQUEST_FROM_MESSAGE.equals(header)){
            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
            throw new AccessDeniedException("Inner call, Access is denied");
        }

        return point.proceed();
    }
}
