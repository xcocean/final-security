package top.lingkang;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.entity.SessionEntity;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.http.FinalRequest;
import top.lingkang.http.FinalResponse;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.http.impl.FinalResponseSpringMVC;
import top.lingkang.security.FinalRoles;
import top.lingkang.session.FinalSession;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.AssertUtils;
import top.lingkang.utils.TokenUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/13 14:03
 * @description
 */
public class FinalManager {
    // 管理所有会话
    private static ConcurrentMap<String, SessionEntity> concurrentMap = new ConcurrentHashMap<String, SessionEntity>();
    public static SessionListener sessionListener;

    private static FinalRequest finalRequest;
    private static FinalResponse finalResponse;
    private static FinalSecurityProperties finalSecurityProperties;
    private static FinalTokenGenerate finalTokenGenerate;
    private static FinalExceptionHandler finalExceptionHandler;

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

    public static boolean isLogin() {
        return concurrentMap.containsKey(getToken());
    }

    public static void logout() {
        concurrentMap.remove(getToken());
    }

    /**
     * 检查 token是否有效
     */
    public static boolean checkToken(String token) {
        return concurrentMap.containsKey(token);
    }

    public static boolean checkToken() {
        return concurrentMap.containsKey(getToken());
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

        return null;
    }

    // -----------------  login 相关 ---------------------------------------- end

    // -----------------  session 相关 ---------------------------------------- start

    public static void setSessionListener(SessionListener sessionListener) {
        FinalManager.sessionListener = sessionListener;
    }

    protected static void addFinalSession(String id, SessionEntity sessionEntity) {
        concurrentMap.put(id, sessionEntity);
    }

    public static FinalSession getFinalSession(String token) {
        SessionEntity entity = concurrentMap.get(token);
        if (entity != null)
            return entity.getFinalSession();

        AssertUtils.isNotNull(null, "当前会话还未登录！请先登录");
        return null;
    }

    /**
     * 创建会话
     */
    public static FinalSession createFinalSession(String id) {
        String token = getFinalTokenGenerate().generateToken();
        FinalSession finalSession = new DefaultFinalSession(id, token);
        // 首次登录可以在ThreadLocal中获取
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        servletRequestAttributes.setAttribute(finalSecurityProperties.getTokenName(), token, RequestAttributes.SCOPE_REQUEST);
        // 添加到cookie中
        TokenUtils.addToken(servletRequestAttributes.getResponse(), finalSecurityProperties.getTokenName(), token);
        // 存储到统一管理
        SessionEntity entity = new SessionEntity();
        entity.setFinalSession(finalSession);
        addFinalSession(token, entity);

        // 通知监听
        FinalManager.sessionListener.create(id, token);
        return finalSession;
    }


    // -----------------  session 相关 ---------------------------------------- end

    // -----------------  权限 相关 ---------------------------------------- start

    public static void addRoles(List<String> roles) {
        String token = getToken();
        FinalRoles finalRoles = concurrentMap.get(token).getFinalRoles();
        finalRoles.addRoles(roles);
        concurrentMap.get(token).setFinalRoles(finalRoles);
    }

    // -----------------  权限 相关 ---------------------------------------- end


    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
