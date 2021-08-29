package top.lingkang.config;

import top.lingkang.FinalManager;
import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;
import top.lingkang.session.FinalSession;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 15:44
 * @description 开放上下文供调用
 */
public class FinalContextHolder {

    /**
     * 获取会话
     */
    public static FinalSession getFinalSession() {
        return FinalManager.getFinalRequest().getFinalSession();
    }

    /**
     * 用户登录 返回登录的会话
     *
     * @return FinalSession
     */
    public static FinalSession login(String id) {
        return FinalManager.login(id);
    }

    /**
     * 判断是否登录
     */
    public static boolean isLogin() {
        return FinalManager.isLogin();
    }

    /**
     * 注销当前登录的用户
     */
    public static void logout() {
        FinalManager.logout();
    }

    /**
     * 注销指定token的会话
     */
    public static void logout(String token) {
        FinalManager.logout(token);
    }

    /**
     * 获取当前登录者的id
     */
    public static String getId() {
        return FinalManager.getFinalRequest().getFinalSession().getId();
    }

    /**
     * 当前登录者添加角色
     */
    public static void addRoles(List<String> roles) {
        FinalManager.addRoles(roles);
    }

    /**
     * 当前登录者添加角色
     */
    public static void addRoles(String... roles) {
        FinalManager.addRoles(roles);
    }

    /**
     * 获取当前用户的角色
     *
     * @return
     */
    public static FinalRoles getFinalRoles() {
        return FinalManager.getFinalRoles();
    }

    public static void updateFinalRoles(FinalRoles finalRoles) {
        FinalManager.updateFinalRoles(finalRoles);
    }

    /**
     * 当前登录者添加权限
     */
    public static void addPermission(List<String> permission) {
        FinalManager.addPermission(permission);
    }

    /**
     * 当前登录者添加权限
     */
    public static void addPermission(String... permission) {
        FinalManager.addPermission(permission);
    }

    public static FinalPermission getPermission() {
        return FinalManager.getPermission();
    }

    public static void updatePermission(FinalPermission permission) {
        FinalManager.updatePermission(permission);
    }

    public static long getSessionMaxValid() {
        return FinalManager.getSessionMaxValid();
    }

}
