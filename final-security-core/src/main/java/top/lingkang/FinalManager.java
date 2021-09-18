package top.lingkang;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.MessageConstants;
import top.lingkang.error.*;
import top.lingkang.http.FinalRequest;
import top.lingkang.http.FinalResponse;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.http.impl.FinalResponseSpringMVC;
import top.lingkang.session.FinalSession;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.ArraysUtils;
import top.lingkang.utils.AssertUtils;
import top.lingkang.utils.StringUtils;
import top.lingkang.utils.TokenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

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
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        AssertUtils.isNull(servletRequestAttributes, "获取request上下文失败！");
        return new FinalRequestSpringMVC(servletRequestAttributes.getRequest());
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
            throw new FinalNotLoginException(MessageConstants.TOKEN_CANNOT_NULL);
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
            throw new FinalTokenException(MessageConstants.TOKEN_INVALID);
        }

        // 检查是否失效了
        if (!finalSession.isValidInternal(sessionMaxValid)) {
            // 调用 session 监听
            sessionListener.delete(finalSession);
            throw new FinalTokenException(MessageConstants.TOKEN_EXPIRE);
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
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        FinalRequestSpringMVC requestSpringMVC = new FinalRequestSpringMVC(servletRequestAttributes.getRequest());

        // cookie中获取
        String token = requestSpringMVC.getCookieValue(finalSecurityProperties.getTokenName());
        if (token != null) {
            return token;
        }

        // 请求域中获取
        token = requestSpringMVC.getParam(finalSecurityProperties.getTokenName());
        if (token != null) {
            return token;
        }

        // 请求头中获取
        token = requestSpringMVC.getHeader(finalSecurityProperties.getTokenName());
        if (token != null) {
            return token;
        }

        // ThreadLocal 中获取
        Object requestToken = servletRequestAttributes.getAttribute(finalSecurityProperties.getTokenName(), RequestAttributes.SCOPE_REQUEST);
        if (requestToken != null) {
            return (String) requestToken;
        }
        throw new FinalTokenException(MessageConstants.REQUEST_NOT_EXIST_TOKEN);
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
        throw new FinalTokenException(MessageConstants.NOT_LOGIN);
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

    /**
     * 当前用户添加角色
     */
    public static void addRoles(String... roles) {
        if (ArraysUtils.isEmpty(roles)) {
            throw new FinalInitException(MessageConstants.CANNOT_CONFIG_EMPTY_ROLE);
        }
        addRoles(Arrays.asList(roles));
    }

    /**
     * 当前用户添加角色
     */
    public static void addRoles(List<String> roles) {
        if (roles.isEmpty()) {
            throw new FinalInitException(MessageConstants.CANNOT_CONFIG_EMPTY_ROLE);
        }
        String token = checkGetToken();
        List<String> rol = sessionManager.getRoles(token);
        if (ArraysUtils.isEmpty(rol)) {
            rol = new ArrayList<>();
        }
        rol.addAll(roles);
        sessionManager.addRoles(token, new ArrayList<>(new TreeSet<>(rol)));
    }

    public static List<String> getRoles() {
        return sessionManager.getRoles(checkGetToken());
    }

    public static void updateRoles(List<String> roles) {
        updateRoles(checkGetToken(), roles);
    }

    public static void updateRoles(String token, List<String> roles) {
        if (ArraysUtils.isEmpty(roles)) {
            throw new FinalException(MessageConstants.CANNOT_CONFIG_EMPTY_ROLE);
        }
        sessionManager.updateRoles(token, roles);
    }

    public static void deleteRoles() {
        deleteRoles(checkGetToken());
    }

    public static void deleteRoles(String token) {
        sessionManager.deleteRoles(token);
    }

    /**
     * 当前用户添加权限
     */
    public static void addPermission(String... permission) {
        if (ArraysUtils.isEmpty(permission)) {
            throw new FinalInitException(MessageConstants.CANNOT_CONFIG_EMPTY_PERMISSION);
        }
        addPermission(Arrays.asList(permission));
    }

    /**
     * 当前用户添加权限
     */
    public static void addPermission(List<String> permission) {
        if (permission.isEmpty()) {
            throw new FinalInitException(MessageConstants.CANNOT_CONFIG_EMPTY_PERMISSION);
        }
        String token = checkGetToken();
        List<String> per = sessionManager.getPermission(token);
        if (ArraysUtils.isEmpty(per)) {
            per = new ArrayList<>();
        }
        per.addAll(permission);
        sessionManager.addPermission(token, new ArrayList<>(new TreeSet<>(per)));
    }

    public static List<String> getPermission() {
        return sessionManager.getPermission(checkGetToken());
    }

    /**
     * 更新当前用户的权限
     */
    public static void updatePermission(List<String> permission) {
        sessionManager.updatePermission(checkGetToken(), permission);
    }

    /**
     * 更新指定token的权限
     */
    public static void updatePermission(String token, List<String> permission) {
        if (permission.isEmpty()) {
            throw new FinalException(MessageConstants.CANNOT_CONFIG_EMPTY_PERMISSION);
        }
        sessionManager.updatePermission(token, permission);
    }


    /**
     * 删除当前用户的权限
     */
    public static void deletePermission() {
        deletePermission(checkGetToken());
    }

    /**
     * 删除指定token的权限
     */
    public static void deletePermission(String token) {
        sessionManager.deletePermission(token);
    }

    private static String checkGetToken() {
        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            throw new FinalInitException(MessageConstants.NOT_LOGIN);
        }
        return token;
    }

    // -----------------  权限 相关 ---------------------------------------- end


    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
