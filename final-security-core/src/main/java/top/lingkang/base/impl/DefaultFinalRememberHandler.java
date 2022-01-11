package top.lingkang.base.impl;

import top.lingkang.base.FinalRememberHandler;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/11
 */
public class DefaultFinalRememberHandler implements FinalRememberHandler {
    @Override
    public boolean doLogin(String id, FinalSession rememberSession, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
