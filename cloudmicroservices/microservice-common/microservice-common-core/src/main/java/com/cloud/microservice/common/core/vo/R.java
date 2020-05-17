package com.cloud.microservice.common.core.vo;

import com.cloud.microservice.common.core.consts.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回消息体
 */
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1219109270886265543L;

    /**
     * 返回码
     */
    private int code = CommonConstant.SUCCESS;

    /**
     * 返回消息
     */
    private String msg = "Success";

    /**
     * 返回数据实体
     */
    private T data;

    public R(){
        super();
    }

    public R(T data){
        super();
        this.data = data;
    }

    public  R(T data, String msg){
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e){
        super();
        this.msg = e.getMessage();
        this.code = CommonConstant.FAIL;
    }

    public static <T> R ok(T data){
        return R.builder().data(data).build();
    }

    public static R fail(int code)  {
        return R.builder().code(code).build();
    }

    public static R fail(int code, String msg){
        return R.builder().code(code).msg(msg).build();
    }

    public static R fail(){
        return fail(CommonConstant.FAIL);
    }

    public static R fail(String msg){
        return fail(CommonConstant.FAIL, msg);
    }
}
