package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author lingkang
 * @date 2022/1/11
 */
public class UpdateLastAccessTimeFilter implements FinalFilterChain {

    private FinalManager manager;

    public UpdateLastAccessTimeFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        // 是否续时，，默认关闭提升性能
        if (manager.getProperties().getTokenAccessContinue()) {
            manager.getSessionManager().updateLastAccessTime(manager.getToken());
        }
    }
}
