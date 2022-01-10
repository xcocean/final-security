package top.lingkang.helper;

import top.lingkang.FinalManager;
import top.lingkang.session.FinalSession;
import top.lingkang.utils.SpringBeanUtils;

import java.util.List;

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
        return manager.isLogin();
    }

    public static boolean isLogin(String token) {
        return manager.isLogin(token);
    }

    public static FinalSession getSession() {
        return manager.getSession();
    }

    public static FinalSession getSession(String token) {
        return manager.getSession(token);
    }

    public static String getToken() {
        return manager.getToken();
    }

    public static List<String> getRole(String token) {
        return manager.getSessionManager().getRoles(token);
    }

    public static List<String> getRole() {
        return getRole(getToken());
    }

    public static List<String> getPermission(String token) {
        return manager.getSessionManager().getPermission(token);
    }

    public static List<String> getPermission() {
        return getPermission(getToken());
    }
}
