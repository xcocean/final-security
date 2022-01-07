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
}
