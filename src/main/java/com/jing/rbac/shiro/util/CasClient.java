package com.jing.rbac.shiro.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.rbac.response.ResponseResult;
import com.jing.rbac.shiro.model.CasUserAuthResponse;
import com.jing.rbac.shiro.model.UserRegistModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CasClient {

    @Value("${cas.serviceId}")
    private String serviceId;

    @Value("${cas.url.login}")
    private String casLoginUrl;

    @Value("${cas.url.addUser}")
    private String casAddUserUrl;

    @Value("${cas.url.deleteUser}")
    private String casDeleteUserUrl;

    public CasUserAuthResponse getUserAuthInfo(String token, Cookie[] cookies) {
        try {
            String cookie = getTargetCookie(cookies, serviceId);
            if ( !StringUtils.hasText(cookie)) {
                return null;
            }
            Mono<String> res = WebClient.create().post()
                    .uri(casLoginUrl)
                    .header("serviceId", serviceId)
                    .header("token", token)
                    .header("cookie", serviceId + "=" + cookie + ";")
                    .attribute("token", token)
                    .retrieve()
                    .bodyToMono(String.class);
            log.info("the vas authentication:{}", res.block());
            ResponseResult<Map> response = JSONObject.parseObject(res.block(), ResponseResult.class);
            CasUserAuthResponse casAuthInfo = new CasUserAuthResponse();
            casAuthInfo = JSONObject.parseObject(JSON.toJSONString(response.getData()), CasUserAuthResponse.class);
            casAuthInfo.setTargetCookie(cookie);
            casAuthInfo.setToken(token);
            Subject subject = SecurityUtils.getSubject();
            subject.login(casAuthInfo);
            return casAuthInfo;
        } catch (Exception e) {
            log.error("request the vas catch a exception,\r\n:{}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    public String getTargetCookie(Cookie[] cookies, String key) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public String addCasUser(String token, UserRegistModel user, boolean admin) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("serviceId", serviceId);
        Map<String, Object> detail = new HashMap<>();
        detail.put("userId", user.getUserId());
        detail.put("userName", user.getName());
        detail.put("admin", admin);
        params.put("data", detail);
        Mono<String> res = WebClient.create().post()
                .uri(casAddUserUrl)
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class);
        String resp = res.block();
        log.info("the vas authentication:{}", resp);
        ResponseResult<String> response = JSONObject.parseObject(resp, ResponseResult.class);
        if (response.getCode().equals("200")) {
            log.info("添加用户成功：{}", user);
            return user.getUserId();
        } else if (response.getCode().equals("001")||response.getCode().equals("1")) {
            log.info("添加用户，用户已存在：{}", user);
            return user.getUserId();
        }
        return null;
    }

    public void delCasUser(String token, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("serviceId", serviceId);
        params.put("userId", userId);
        Mono<String> res = WebClient.create().post()
                .uri(casDeleteUserUrl)
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class);

        String resp = res.block();
        ResponseResult<String> response = JSONObject.parseObject(resp, ResponseResult.class);
    }


}
