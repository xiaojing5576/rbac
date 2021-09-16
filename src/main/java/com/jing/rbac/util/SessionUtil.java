package com.jing.rbac.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: huangjingyan-200681
 * @Date: 2021/8/12 15:04
 * @Mail: huangjingyan@eastmoney.com
 * @Description: TODO
 * @Version: 1.0
 **/
@Component
public class SessionUtil {


    public void expireSession(String userId){
        Session sessionByUsername = getSessionByUsername(userId);
        //设置过期时间为1毫秒
        if (sessionByUsername != null){
            sessionByUsername.setTimeout(3000);
        }
    }

    /**
     * 获取指定用户名的Session
     * @param userId
     * @return
     */
    public Session getSessionByUsername(String userId) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();

        SessionDAO sessionDAO = sessionManager.getSessionDAO();
//        sessionManager.getSession()
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Object attribute;
        String userSession = null;
        for (Session session : sessions) {
            attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            userSession = (String) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (StringUtils.isBlank(userSession)) {
                continue;
            }
            if (userId.equals(userSession)) {
                return session;
            }
        }
        return null;
    }
}
