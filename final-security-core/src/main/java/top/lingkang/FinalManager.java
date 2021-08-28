package top.lingkang;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.MessageConstants;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.error.FinalInitException;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.TokenException;
import top.lingkang.http.FinalRequest;
import top.lingkang.http.FinalResponse;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.http.impl.FinalResponseSpringMVC;
import top.lingkang.session.FinalSession;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.AssertUtils;
import top.lingkang.utils.StringUtils;
import top.lingkang.utils.TokenUtils;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 14:03
 * @description
 */
public class FinalManager {
    // 管理所有会话
    private static SessionManager sessionManager;
    public static SessionListener sessionListener;

    private static FinalRequest finalRequest;
    private static FinalResponse finalResponse;
    private static FinalSecurityProperties finalSecurityProperties;
    private static FinalTokenGenerate finalTokenGenerate;
    private static FinalExceptionHandler finalExceptionHandler;
    private static long sessionMaxValid;

    public static long getSessionMaxValid() {
        return sessionMaxValid;
    }

    public static void setSessionMaxValid(long sessionMaxValid) {
        FinalManager.sessionMaxValid = sessionMaxValid;
    }

    public static FinalRequest getFinalRequest() {
        if (finalRequest instanceof FinalRequestSpringMVC) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return new FinalRequestSpringMVC(servletRequestAttributes.getRequest());
        }
        AssertUtils.isNull(null, "获取request上下文失败！");
        return null;
    }

    public static FinalResponse getFinalResponse() {
        if (finalResponse instanceof FinalResponseSpringMVC) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return new FinalResponseSpringMVC(servletRequestAttributes.getResponse());
        }
        AssertUtils.isNull(null, "获取response上下文失败！");
        return null;
    }

    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    public static void setSessionManager(SessionManager sessionManager) {
        FinalManager.sessionManager = sessionManager;
    }

    public static void setFinalResponse(FinalResponse finalResponse) {
        FinalManager.finalResponse = finalResponse;
    }

    public static void setFinalRequest(FinalRequest finalRequest) {
        FinalManager.finalRequest = finalRequest;
    }

    public static FinalSecurityProperties getFinalSecurityProperties() {
        return finalSecurityProperties;
    }

    public static void setFinalSecurityProperties(FinalSecurityProperties finalSecurityProperties) {
        FinalManager.finalSecurityProperties = finalSecurityProperties;
    }

    public static FinalTokenGenerate getFinalTokenGenerate() {
        return finalTokenGenerate;
    }

    public static void setFinalTokenGenerate(FinalTokenGenerate finalTokenGenerate) {
        FinalManager.finalTokenGenerate = finalTokenGenerate;
    }

    public static FinalExceptionHandler getFinalExceptionHandler() {
        return finalExceptionHandler;
    }

    public static void setFinalExceptionHandler(FinalExceptionHandler finalExceptionHandler) {
        FinalManager.finalExceptionHandler = finalExceptionHandler;
    }

    // -----------------  login 相关 ---------------------------------------- start

    public static FinalSession login(String id) {
        AssertUtils.isNotNull(id, "登录id不能为空！");
        return createFinalSession(id);
    }

    public static boolean isLogin(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new NotLoginException(MessageConstants.TOKEN_CANNOT_NULL);
        }
        // 检查token是否有效
        FinalManager.checkToken(token);
        return true;
    }

    public static boolean isLogin() {
        // 检查会话里的令牌
        String token = FinalManager.getToken();
        return isLogin(token);
    }

    public static void logout() {
        logout(getToken());
    }

    public static void logout(String token) {
        sessionManager.removeSession(token);
    }


    /**
     * 检查 token是否有效
     */
    public static void checkToken(String token) {
        FinalSession finalSession = sessionManager.getFinalSession(token);
        if (finalSession == null) {
            throw new TokenException(MessageConstants.TOKEN_INVALID);
        }

        // 检查是否失效了
        if (!finalSession.isValidInternal(sessionMaxValid)) {
            // 调用 session 监听
            sessionListener.delete(finalSession);
            throw new TokenException(MessageConstants.TOKEN_EXPIRE);
        }

        // 更新session访问时间
        if (finalSecurityProperties.isAccessUpdateSessionTime()) {
            sessionManager.updateLastAccessTime(token);
        }

    }

    public static boolean checkToken() {
        return sessionManager.hasToken(getToken());
    }

    /**
     * 获取token
     */
    public static String getToken() {
        // ThreadLocal 中获取
        Object requestToken = getServletRequestAttributes().getAttribute(finalSecurityProperties.getTokenName(), RequestAttributes.SCOPE_REQUEST);
        if (requestToken != null) {
            return (String) requestToken;
        }

        // 请求域中获取
        String token = getFinalRequest().getHttpServletRequest().getParameter(finalSecurityProperties.getTokenName());
        if (token != null) {
            return token;
        }

        // cookie中获取
        token = getFinalRequest().getCookieValue(finalSecurityProperties.getTokenName());
        if (token != null) {
            return token;
        }

        throw new TokenException(MessageConstants.REQUEST_NOT_EXIST_TOKEN);
    }

    // -----------------  login 相关 ---------------------------------------- end

    // -----------------  session 相关 ---------------------------------------- start

    public static void setSessionListener(SessionListener sessionListener) {
        FinalManager.sessionListener = sessionListener;
    }

    public static FinalSession getFinalSession(String token) {
        FinalSession finalSession = sessionManager.getFinalSession(token);
        if (finalSession != null)
            return finalSession;

        //AssertUtils.isNotNull(null, "当前会话还未登录！请先登录");
        throw new TokenException(MessageConstants.NOT_LOGIN);
    }

    /**
     * 创建会话
     */
    public static FinalSession createFinalSession(String id) {
        // 首次登录可以在ThreadLocal中获取
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();

        // 1、首先判断是否登录了
        FinalSession byId = sessionManager.getFinalSessionById(id);
        if (byId != null) {
            // 登录了更新session的最后访问时间
            byId.updateLastAccessTime();
            sessionManager.updateLastAccessTime(byId.getToken());
            // 将token放到当前线程的变量中
            servletRequestAttributes.setAttribute(finalSecurityProperties.getTokenName(), byId.getToken(), RequestAttributes.SCOPE_REQUEST);
            // 添加到cookie中
            TokenUtils.addToken(servletRequestAttributes.getResponse(), finalSecurityProperties.getTokenName(), byId.getToken());
            return byId;
        }

        // 2、未登录的情况
        String token = getFinalTokenGenerate().generateToken();
        FinalSession finalSession = new DefaultFinalSession(id, token);
        servletRequestAttributes.setAttribute(finalSecurityProperties.getTokenName(), token, RequestAttributes.SCOPE_REQUEST);
        // 添加到cookie中
        TokenUtils.addToken(servletRequestAttributes.getResponse(), finalSecurityProperties.getTokenName(), token);
        // 存储到统一会话管理
        sessionManager.putFinalSession(token, finalSession);

        // 通知监听
        FinalManager.sessionListener.create(id, token);
        return finalSession;
    }


    // -----------------  session 相关 ---------------------------------------- end

    // -----------------  权限 相关 ---------------------------------------- start

    public static void addRoles(List<String> roles) {
        if (roles.isEmpty()) {
            throw new FinalInitException(MessageConstants.CANNOT_CONFIG_EMPTY_ROLE);
        }
        sessionManager.addFinalRoles(getToken(), roles);
    }

    // -----------------  权限 相关 ---------------------------------------- end


    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
