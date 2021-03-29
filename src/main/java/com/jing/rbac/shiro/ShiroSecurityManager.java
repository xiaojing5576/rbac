package com.jing.rbac.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ShiroSecurityManager {

    @Resource
    private ShiroRealm shiroRealm;

    @Bean("securityManager")
    public SecurityManager securityManager() {
        // 将自定义 Realm 加进来
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(shiroRealm);
        log.info("====securityManager注册完成====");
        return securityManager;
    }

}
