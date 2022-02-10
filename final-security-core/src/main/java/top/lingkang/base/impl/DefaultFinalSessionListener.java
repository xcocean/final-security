package top.lingkang.base.impl;

import org.springframework.lang.Nullable;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public class DefaultFinalSessionListener implements FinalSessionListener {
    @Override
    public void create(FinalSession session, @Nullable HttpServletRequest request, @Nullable HttpServletResponse response) {

    }

    @Override
    public void logout(FinalSession session, HttpServletRequest request, HttpServletResponse response) {

    }
}
