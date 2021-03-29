package com.jing.rbac.shiro;

import com.alibaba.fastjson.JSONObject;
import com.jing.rbac.service.RoleService;
import com.jing.rbac.service.UserService;
import com.jing.rbac.shiro.model.CasUserAuthResponse;
import com.jing.rbac.vo.LoginResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userId = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //给用户设置角色
        Set<String> roles = userService.getUserRoles(userId);
        authorizationInfo.setRoles(roles);
        log.info("设置roles:{}",roles);
        //给用户设置权限
        Set<String> permissions = new HashSet<>();
        roles.forEach(x->{
            permissions.addAll(roleService.getRolePermissions(x));
        });
        authorizationInfo.setStringPermissions(permissions);
        log.info("设置rights:{}",permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CasUserAuthResponse response = (CasUserAuthResponse) authenticationToken;

        LoginResultVO model = userService.getLoginInfo(response.getUserId());
        if (model != null) {
            SecurityUtils.getSubject().getSession().setAttribute("user", model);
            SecurityUtils.getSubject().getSession().setAttribute("userId", model.getUserId());
            SecurityUtils.getSubject().getSession().setAttribute("roles", model.getRoleCodes());
            SecurityUtils.getSubject().getSession().setAttribute("permissions", JSONObject.toJSONString(model.getPermissionCodes()));
            SecurityUtils.getSubject().getSession().setAttribute("token", response.getCredentials());
            AuthenticationInfo authInfo = new SimpleAuthenticationInfo(response.getUserId(), response.getToken(), "myRealm");
            return authInfo;
        }
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof CasUserAuthResponse;
    }
}
