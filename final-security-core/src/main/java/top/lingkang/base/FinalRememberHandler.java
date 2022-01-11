package top.lingkang.base;

import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FinalRememberHandler {
    /**
     * 返回true时，将放行请求，前提条件是在下面的接口实现类执行了登录动作
     */
    boolean doLogin(String id, FinalSession rememberSession, HttpServletRequest request, HttpServletResponse response);
}
