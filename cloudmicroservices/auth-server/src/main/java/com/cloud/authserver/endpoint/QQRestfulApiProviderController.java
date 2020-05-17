package com.cloud.authserver.endpoint;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 徐靖峰[OF2938]
 * company qianmi.com
 * Date 2018-04-25
 */
@RestController
@RequestMapping("/qq")
public class QQRestfulApiProviderController {

    @RequestMapping("/info/{qq}")
    public QQAccount info(@PathVariable("qq") String qq){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.toString());
        return InMemoryQQDatabase.database.get(qq);
    }

    @RequestMapping("fans/{qq}")
    public List<QQAccount> fans(@PathVariable("qq") String qq){
        return InMemoryQQDatabase.database.get(qq).getFans();
    }



}
