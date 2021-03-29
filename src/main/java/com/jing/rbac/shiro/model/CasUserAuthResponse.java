package com.jing.rbac.shiro.model;


import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class CasUserAuthResponse implements AuthenticationToken {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 角色
     */
    private String role;
    /**
     * 角色名称
     */
    private String roleName;

    private String token;
    /**
     * 前端cookie
     */
    private String targetCookie;

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

}
