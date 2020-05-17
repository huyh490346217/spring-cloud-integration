package com.cloud.authorizationcodeclient.controller;

import com.cloud.microservice.common.core.vo.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
@Slf4j
public class ClientTokenController {

    private final RestTemplate restTemplate;

    @GetMapping("/tokens/redirect")
    public R getToken(@RequestParam("code") String code) {
        log.info("receive code ", code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", "aiqiyi");
        params.add("client_secret", "secret");
        params.add("redirect_uri", "http://localhost:10102/clients/tokens/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:10100/oauth/token", requestEntity, String.class);
        String token = response.getBody();
        log.info("token => {}", token);
        return R.ok(token);
    }
}
