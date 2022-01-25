package top.lingkang.holder;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.FinalManager;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.config.FinalProperties;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.CookieUtils;


/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalHolder {

    @Autowired
    private FinalManager manager;

    public String login(String id, Object user, String[] role, String[] permission) {
        FinalProperties properties = manager.getProperties();
        SessionManager sessionManager = manager.getSessionManager();
        FinalTokenGenerate tokenGenerate = manager.getTokenGenerate();
        FinalSessionListener sessionListener = manager.getSessionListener();

        if (properties.getOnlyOne()) { // 用户只能存在一个会话
            top.lingkang.session.FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                sessionManager.removeSession(session.getToken());
            }
        }

        String token = null;
        try {
            top.lingkang.session.FinalSession session = sessionManager.getSession(getToken());
            // 是否生成新令牌
            if (manager.getProperties().getGenerateNewToken()) {
                token = tokenGenerate.generateToken();
                sessionManager.removeSession(session.getToken());
            } else {
                token = session.getToken();
            }
        } catch (Exception e) {
            token = tokenGenerate.generateToken();
        }

        FinalSession session = new DefaultFinalSession(id, user, token);

        // 添加会话
        sessionManager.addFinalSession(token, session);
        if (role != null)
            sessionManager.addRoles(token, role);
        if (permission != null)
            sessionManager.addPermission(token, permission);

        // 将token放到当前线程的变量中
        FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
        if (requestContext != null) {
            requestContext.setToken(token);

            // 将令牌放到cookie中
            if (properties.getUseCookie() && requestContext.getResponse() != null) {
                CookieUtils.addToken(
                        requestContext.getResponse(),
                        properties.getTokenName(),
                        token,
                        (int) (properties.getMaxValid() / 1000L)
                );
            }
        } else {
            FinalContextHolder.setRequestContext(new FinalRequestContext(token));
        }

        // 会话创建监听
        sessionListener.create(session,
                requestContext == null ? null : requestContext.getRequest(),
                requestContext == null ? null : requestContext.getResponse()
        );

        return token;
    }

    public boolean isLogin() {
        return isLogin(getToken());
    }

    public boolean isLogin(String token) {
        return manager.getSessionManager().existsToken(token);
    }

    public void logout(String token) {
        top.lingkang.session.FinalSession removeSession = null;
        FinalRequestContext requestContext = null;
        try {
            removeSession = manager.getSessionManager().removeSession(token);

            requestContext = FinalContextHolder.getRequestContext();
            if (requestContext != null && requestContext.getResponse() != null) {
                if (manager.getProperties().getUseCookie()) {
                    CookieUtils.tokenToZeroAge(manager.getProperties().getTokenName(), requestContext.getResponse());
                }
            }
        } catch (Exception e) {
        }
        // 会话删除监听
        manager.getSessionListener().delete(removeSession,
                requestContext == null ? null : requestContext.getRequest(),
                requestContext == null ? null : requestContext.getResponse()
        );
    }

    public void logout() {
        logout(getToken());
    }

    public void logoutById(String id) {
        logout(manager.getSessionManager().getSessionById(id).getToken());
    }

    public top.lingkang.session.FinalSession getSession() {
        return getSession(getToken());
    }

    public top.lingkang.session.FinalSession getSession(String token) {
        return manager.getSessionManager().getSession(token);
    }

    public String getToken() {
        return manager.getToken();
    }

    public String[] getRole(String token) {
        return manager.getSessionManager().getRoles(token);
    }

    public String[] getRole() {
        return getRole(getToken());
    }

    public String[] getPermission(String token) {
        return manager.getSessionManager().getPermission(token);
    }

    public String[] getPermission() {
        return getPermission(getToken());
    }
}
