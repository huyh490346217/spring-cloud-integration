package com.cloud.microservicesuser.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cloud.common.security.annotation.IgnoreSecurity;
import com.cloud.common.security.consts.IgnoreSecurityConstant;
import com.cloud.microservice.admin.feign.RemoteUserService;
import com.cloud.microservice.common.core.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/demo/users")
public class UserDemoController {

    private final RemoteUserService remoteUserService;

    @GetMapping("/user/{id}")
    @IgnoreSecurity()
    public R getUserById(@PathVariable("id") String id ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal is " + principal);
        Map<String, String> result = new HashMap<>();
        result.put("userId", id);
        result.put("principal", (String) principal);
        return R.ok(result);
    }

    @GetMapping("/ignore")
//    @IgnoreSecurity()
    public R testIgnoreSecurity() throws Exception {
//        throw new Exception("test exception");
        return R.ok("test ignore security");
    }

    @GetMapping("/none")
    public R testNoneIgnoreSecurity(){
        return R.ok("test none ignore security");
    }

    @GetMapping("/{name}")
    @IgnoreSecurity
    public R getUserByName(@PathVariable("name") String name){
        return remoteUserService.getUserInfoByUserName(name, IgnoreSecurityConstant.REQUEST_FROM_MESSAGE);
    }

}
