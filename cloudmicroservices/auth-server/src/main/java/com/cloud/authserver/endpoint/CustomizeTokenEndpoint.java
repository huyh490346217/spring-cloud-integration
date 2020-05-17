package com.cloud.authserver.endpoint;

import com.cloud.microservice.common.core.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/oken")
public class CustomizeTokenEndpoint {

    private final RedisTemplate redisTemplate;

//    @PostMapping("/tokens")
//    public R getTokenList(Map<String, Object> params){
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.opsForValue().multiGet(pages)
//    }

    @GetMapping("/")
    public R getTokenList(){
        return R.ok("hello");
    }
}
