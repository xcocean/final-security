package top.lingkang.helper;

import top.lingkang.FinalManager;
import top.lingkang.session.FinalSession;
import top.lingkang.utils.SpringBeanUtils;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public abstract class FinalHolder {
    public static FinalManager manager = SpringBeanUtils.getBean(FinalManager.class);

    public static void login(String id) {
        manager.login(id);
    }

    public static boolean isLogin() {
        return isLogin(getToken());
    }

    public static boolean isLogin(String token) {
        return manager.getSessionManager().existsToken(token);
    }

    public static void logout(String token) {
        manager.getSessionManager().removeSession(token);
    }

    public static void logout() {
        manager.getSessionManager().removeSession(getToken());
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
    }

    public static void addPermission(String... permission) {
        manager.getSessionManager().addPermission(getToken(), permission);
    }
}
