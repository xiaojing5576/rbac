package com.jing.rbac.shiro;

import com.alibaba.fastjson.JSON;
import com.jing.rbac.response.ResponseResult;
import com.jing.rbac.response.ResultCode;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoginFilter extends UserFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        super.redirectToLogin(request, response);
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(new ResponseResult<>(ResultCode.SESSION_ERR)));
    }

}
