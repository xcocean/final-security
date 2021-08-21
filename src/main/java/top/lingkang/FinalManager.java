package top.lingkang;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.MessageConstants;
import top.lingkang.entity.SessionEntity;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.TokenException;
import top.lingkang.http.FinalRequest;
import top.lingkang.http.FinalResponse;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.http.impl.FinalResponseSpringMVC;
import top.lingkang.security.FinalFilterManager;
import top.lingkang.security.FinalRoles;
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

    public static void login(String id) {
        createFinalSession(id);
    }

    public static boolean isLogin(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new NotLoginException(MessageConstants.tokenNotNull);
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
        sessionManager.remove(token);
    }

    public static List<SessionEntity> getAllSessionEntity() {
        return sessionManager.getAllSessionEntity();
    }

    public static int getAllSessionEntityCount() {
        return sessionManager.getAllSessionEntityCount();
    }

    /**
     * 检查 token是否有效
     */
    public static void checkToken(String token) {
        SessionEntity sessionEntity = sessionManager.get(token);
        if (sessionEntity == null) {
            throw new TokenException(MessageConstants.tokenInvalidMsg);
        }
        if (!sessionEntity.getFinalSession().isValidInternal(sessionMaxValid)) {
            // 调用 session 监听
            sessionListener.delete(sessionEntity);
            throw new TokenException(MessageConstants.TokenValidInvalidMsg);
        }
        // 更新session访问时间
        sessionEntity.getFinalSession().updateLastAccessTime();
    }

    public static boolean checkToken() {
        return sessionManager.containsKey(getToken());
    }

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

        throw new TokenException(MessageConstants.requestNotToken);
    }

    // -----------------  login 相关 ---------------------------------------- end

    // -----------------  session 相关 ---------------------------------------- start

    public static void setSessionListener(SessionListener sessionListener) {
        FinalManager.sessionListener = sessionListener;
    }

    public static FinalSession getFinalSession(String token) {
        SessionEntity entity = sessionManager.get(token);
        if (entity != null)
            return entity.getFinalSession();

        AssertUtils.isNotNull(null, "当前会话还未登录！请先登录");
        return null;
    }

    /**
     * 创建会话
     */
    public static FinalSession createFinalSession(String id) {
        // 首次登录可以在ThreadLocal中获取
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();

        // 首先判断是否登录了
        SessionEntity byId = sessionManager.getById(id);
        if (byId != null) {
            FinalSession finalSession = byId.getFinalSession();
            // 登录了更新session的最后访问时间
            finalSession.updateLastAccessTime();
            servletRequestAttributes.setAttribute(finalSecurityProperties.getTokenName(), finalSession.getToken(), RequestAttributes.SCOPE_REQUEST);
            // 添加到cookie中
            TokenUtils.addToken(servletRequestAttributes.getResponse(), finalSecurityProperties.getTokenName(), finalSession.getToken());
            return finalSession;
        }

        String token = getFinalTokenGenerate().generateToken();
        FinalSession finalSession = new DefaultFinalSession(id, token);
        servletRequestAttributes.setAttribute(finalSecurityProperties.getTokenName(), token, RequestAttributes.SCOPE_REQUEST);
        // 添加到cookie中
        TokenUtils.addToken(servletRequestAttributes.getResponse(), finalSecurityProperties.getTokenName(), token);
        // 存储到统一管理
        SessionEntity entity = new SessionEntity();
        entity.setFinalSession(finalSession);
        sessionManager.put(token, entity);

        // 通知监听
        FinalManager.sessionListener.create(id, token);
        return finalSession;
    }

    public static SessionEntity getSessionEntity() {
        return getSessionEntity(getToken());
    }

    public static SessionEntity getSessionEntity(String token) {
        return sessionManager.get(token);
    }

    public static void updateSessionEntity(String token, SessionEntity sessionEntity) {
        // 更新最后访问时间
        sessionEntity.getFinalSession().updateLastAccessTime();
        sessionManager.put(token, sessionEntity);
    }

    // -----------------  session 相关 ---------------------------------------- end

    // -----------------  权限 相关 ---------------------------------------- start

    public static void addRoles(List<String> roles) {
        String token = getToken();
        FinalRoles finalRoles = sessionManager.get(token).getFinalRoles();
        finalRoles.addRoles(roles);
        sessionManager.get(token).setFinalRoles(finalRoles);
    }

    // -----------------  权限 相关 ---------------------------------------- end


    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
