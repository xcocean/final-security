package top.lingkang.base.impl;

import top.lingkang.base.FinalSessionListener;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public class DefaultFinalSessionListener implements FinalSessionListener {
    @Override
    public void create(String token, String id, HttpServletRequest request) {

    }
}
