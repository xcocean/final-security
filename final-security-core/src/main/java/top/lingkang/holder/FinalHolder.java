package top.lingkang.holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import top.lingkang.FinalManager;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.config.FinalProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.session.FinalSession;
import top.lingkang.session.FinalSessionData;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.CookieUtils;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Lazy
@Component
public class FinalHolder {

    @Autowired
    private FinalManager manager;

    public String login(String id) {
        return login(id, false);
    }

    public String login(String id, boolean remember) {
        FinalProperties properties = manager.getProperties();
        SessionManager sessionManager = manager.getSessionManager();
        FinalTokenGenerate tokenGenerate = manager.getTokenGenerate();
        FinalSessionListener sessionListener = manager.getSessionListener();

        if (properties.getOnlyOne()) { // 用户只能存在一个会话
            FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                sessionManager.removeSession(session.getToken());
            }
        }

        String token = null;
        try {
            FinalSession session = sessionManager.getSession(getToken());
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

        FinalSession session = new DefaultFinalSession(id, token);

        // 添加会话
        // ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        sessionManager.addFinalSession(token, session);// 共享会话时，会出现会话覆盖


        // 将token放到当前线程的变量中
        FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
        if (requestContext != null) {
            requestContext.setToken(token);

            // 将令牌放到cookie中
            if (properties.getUseCookie()) {
                CookieUtils.addToken(
                        requestContext.getResponse(),
                        properties.getTokenName(),
                        token,
                        (int) (properties.getMaxValid() / 1000L)
                );
            }

            if (manager.getProperties().getUseViewSession()) {
                requestContext.getRequest().getSession().setAttribute(
                        FinalConstants.VIEW_SESSION_NAME,
                        new FinalSessionData(id, token, null, null)
                );
            }
        } else {
            FinalContextHolder.setRequestContext(new FinalRequestContext(token));
        }

        // 记住我
        if (remember) {
            String rememberToken = manager.getRememberToken();
            if (rememberToken == null) {
                rememberToken = tokenGenerate.generateRemember();
            }
            rememberToken = properties.getRememberTokenPrefix() + rememberToken;
            FinalSession rememberSession = new DefaultFinalSession(properties.getRememberTokenPrefix() + id,
                    rememberToken);
            sessionManager.addFinalSession(rememberSession.getToken(), rememberSession);

            // 将记住我令牌放到cookie中
            if (requestContext != null && requestContext.getResponse() != null) {
                if (properties.getUseCookie()) {
                    CookieUtils.addToken(
                            requestContext.getResponse(),
                            properties.getRememberName(),
                            rememberToken,
                            (int) (properties.getMaxValidRemember() / 1000L)
                    );
                }
                // 放到响应头
                requestContext.getResponse().addHeader(properties.getRememberName(), rememberSession.getToken());
            } else {
                // 此时是无请求会话，放在本地线程中
                requestContext.setRemember(rememberToken);
            }
        }

        // 会话创建监听
        sessionListener.create(token, id,
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
        try {
            FinalSession removeSession = manager.getSessionManager().removeSession(token);
            // 移除记住我
            manager.getSessionManager().removeSession(manager.getProperties().getRememberTokenPrefix() + manager.getRememberToken());

            FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
            if (requestContext != null) {
                if (manager.getProperties().getUseViewSession()) {
                    requestContext.getRequest().getSession().removeAttribute(FinalConstants.VIEW_SESSION_NAME);
                }
                if (requestContext.getResponse() != null) {
                    CookieUtils.tokenToZeroAge(manager.getProperties().getRememberName(), requestContext.getResponse());
                    if (manager.getProperties().getUseCookie()) {
                        CookieUtils.tokenToZeroAge(manager.getProperties().getTokenName(), requestContext.getResponse());
                    }
                }
            }
            // 会话创建监听
            manager.getSessionListener().delete(token, removeSession == null ? null : removeSession.getId(),
                    requestContext == null ? null : requestContext.getRequest(),
                    requestContext == null ? null : requestContext.getResponse()
            );
        } catch (Exception e) {
        }
    }

    public void logout() {
        try {
            logout(getToken());
        } catch (Exception e) {
        }
    }

    public FinalSession getSession() {
        return getSession(getToken());
    }

    public FinalSession getSession(String token) {
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

    public void addRoles(String... role) {
        manager.getSessionManager().addRoles(getToken(), role);

        if (manager.getProperties().getUseViewSession() && FinalContextHolder.getRequestContext() != null) {
            HttpSession session = FinalContextHolder.getRequestContext().getRequest().getSession();
            if (session.getAttribute(FinalConstants.VIEW_SESSION_NAME) != null) {
                FinalSessionData sessionData = (FinalSessionData) session.getAttribute(FinalConstants.VIEW_SESSION_NAME);
                sessionData.setRole(role);
                session.setAttribute(FinalConstants.VIEW_SESSION_NAME, sessionData);
            }
        }
    }

    public void addPermission(String... permission) {
        manager.getSessionManager().addPermission(getToken(), permission);

        if (manager.getProperties().getUseViewSession() && FinalContextHolder.getRequestContext() != null) {
            HttpSession session = FinalContextHolder.getRequestContext().getRequest().getSession();
            if (session.getAttribute(FinalConstants.VIEW_SESSION_NAME) != null) {
                FinalSessionData sessionData = (FinalSessionData) session.getAttribute(FinalConstants.VIEW_SESSION_NAME);
                sessionData.setPermission(permission);
                session.setAttribute(FinalConstants.VIEW_SESSION_NAME, sessionData);
            }
        }
    }
}
