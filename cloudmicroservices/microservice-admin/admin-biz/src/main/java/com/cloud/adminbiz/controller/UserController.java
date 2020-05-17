package com.cloud.adminbiz.controller;

import com.cloud.common.security.annotation.IgnoreSecurity;
import com.cloud.microservice.common.core.vo.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    @IgnoreSecurity
    @GetMapping("/{userName}")
    R getUserInfoByUserName(@PathVariable("userName") String userName){
        return R.ok("user name is " + userName);
    }
}
