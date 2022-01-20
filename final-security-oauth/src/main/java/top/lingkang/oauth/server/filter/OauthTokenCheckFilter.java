package top.lingkang.oauth.server.filter;

import top.lingkang.filter.FinalFilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/20
 */
public class OauthTokenCheckFilter implements FinalFilterChain {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {

    }
}
