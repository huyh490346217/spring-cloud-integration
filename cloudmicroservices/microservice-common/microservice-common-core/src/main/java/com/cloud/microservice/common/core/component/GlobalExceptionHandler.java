package com.cloud.microservice.common.core.component;

import com.cloud.microservice.common.core.consts.CommonConstant;
import com.cloud.microservice.common.core.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
//import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handleAccessDeniedException(AccessDeniedException e) {
//        String msg = SpringSecurityMessageSource.getAccessor()
//                .getMessage("AbstractAccessDecisionManager.accessDenied", e.getMessage());
//        log.error(" access denied exception message ", msg, e);
//        return R.builder().code(CommonConstant.FAIL).msg(msg).build();
        return R.builder().code(CommonConstant.FAIL).msg(e.getMessage()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleBodyVlidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        log.error("bad arguments exception ", fieldErrors.get(0).getDefaultMessage());
        return R.builder().code(CommonConstant.FAIL).msg(fieldErrors.get(0).getDefaultMessage()).build();

    }

    /**
     * exception 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleGlobalException(Exception e) {
        log.error(" global exception message :" + e.getCause().getMessage(), e);

        return R.builder().code(CommonConstant.FAIL).msg(e.getCause().getMessage()).build();
    }
}
