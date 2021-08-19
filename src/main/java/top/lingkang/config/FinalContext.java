package top.lingkang.config;

import top.lingkang.FinalManager;
import top.lingkang.session.FinalSession;
import top.lingkang.utils.AssertUtils;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 15:44
 * @description 开放上下文供调用
 */
public class FinalContext {

    /**
     * 获取会话
     */
    public static FinalSession getFinalSession() {
        return FinalManager.getFinalRequest().getFinalSession();
    }

    /**
     * 用户登录
     */
    public static void login(String id) {
        AssertUtils.isNotNull(id, "登录id不能为空！");
        FinalManager.login(id);
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

    }

    /**
     * 获取当前登录者的id
     */
    public static String getId() {
        return FinalManager.getFinalRequest().getFinalSession().getId();
    }

    public static void addRoles(List<String> roles) {

    }

}
