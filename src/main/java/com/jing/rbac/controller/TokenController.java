package com.jing.rbac.controller;

import com.jing.rbac.response.ResponseResult;
import com.jing.rbac.response.ResultCode;
import com.jing.rbac.service.UserService;
import com.jing.rbac.shiro.model.CasUserAuthResponse;
import com.jing.rbac.shiro.util.CasClient;
import com.jing.rbac.vo.LoginResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/3/29 15:36
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/

@Slf4j
@RequestMapping("/token")
@RestController
public class TokenController {


    @Resource
    private UserService userService;

    @Resource
    private CasClient casClient;


    @PostMapping("/login")
    public ResponseResult<?> login(HttpServletRequest request){
        String token = request.getHeader("Token");
        Cookie[] cookies = request.getCookies();
        CasUserAuthResponse response = casClient.getUserAuthInfo(token, cookies);
        if (response == null) {
            log.info("cas返回为空，登录失败");
            return new ResponseResult<>(ResultCode.SESSION_ERR);
        } else {
            LoginResultVO model = userService.getLoginInfo(response.getUserId());
            if (model == null) {
                log.info("数据库查询为空，登录失败");
                return new ResponseResult<>(ResultCode.USER_NOTEXIST);
            }
            return new ResponseResult<>(ResultCode.OK, model);
        }
}

}
