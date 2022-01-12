package top.lingkang.holder;

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
import top.lingkang.utils.SpringBeanUtils;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public abstract class FinalHolder {
    public static FinalManager manager = SpringBeanUtils.getBean(FinalManager.class);

    public static String login(String id) {
        return login(id, false);
    }

    public static String login(String id, boolean remember) {
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
            if (session != null) {
                token = session.getToken();
                sessionManager.removeSession(session.getToken());
            }
        } catch (Exception e) {
        }

        // 生成会话，如果存在token则覆盖会话
        if (token == null)
            token = tokenGenerate.generateToken();

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
            String rememberToken = tokenGenerate.generateRemember();
            FinalSession rememberSession = new DefaultFinalSession(properties.getRememberTokenPrefix() + id,
                    properties.getRememberTokenPrefix() + rememberToken);
            sessionManager.addFinalSession(rememberSession.getToken(), rememberSession);

            // 将记住我令牌放到cookie中
            if (requestContext != null && properties.getUseCookie()) {
                CookieUtils.addToken(
                        requestContext.getResponse(),
                        properties.getRememberName(),
                        rememberSession.getToken(),
                        (int) (properties.getMaxValidRemember() / 1000L)
                );
            }
        }

        // 会话创建监听
        sessionListener.create(token, id, requestContext == null ? null : requestContext.getRequest());

        return token;
    }

    public static boolean isLogin() {
        return isLogin(getToken());
    }

    public static boolean isLogin(String token) {
        return manager.getSessionManager().existsToken(token);
    }

    public static void logout(String token) {
        try {
            manager.getSessionManager().removeSession(token);

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
        } catch (Exception e) {
        }
    }

    public static void logout() {
        try {
            logout(getToken());
        } catch (Exception e) {
        }
    }

    public static FinalSession getSession() {
        return getSession(getToken());
    }

    public static FinalSession getSession(String token) {
        return manager.getSessionManager().getSession(token);
    }

    public static String getToken() {
        return manager.getToken();
    }

    public static String[] getRole(String token) {
        return manager.getSessionManager().getRoles(token);
    }

    public static String[] getRole() {
        return getRole(getToken());
    }

    public static String[] getPermission(String token) {
        return manager.getSessionManager().getPermission(token);
    }

    public static String[] getPermission() {
        return getPermission(getToken());
    }

    public static void addRoles(String... role) {
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

    public static void addPermission(String... permission) {
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
