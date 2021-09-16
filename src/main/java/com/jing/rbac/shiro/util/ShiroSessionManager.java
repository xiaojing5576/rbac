package com.jing.rbac.shiro.util;

import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/8/14 10:18
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/

@Component
public class ShiroSessionManager {

    //    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }

    //    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie cookie = new SimpleCookie("X-Token");
        //浏览器关闭时，失效此cookie
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        //自定义cookie 中sessionId 的key
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionDAO(sessionDAO());
        //删除过期session
        sessionManager.setDeleteInvalidSessions(true);
        //设置session 过期时间
        sessionManager.setGlobalSessionTimeout(30*60*1000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
}
