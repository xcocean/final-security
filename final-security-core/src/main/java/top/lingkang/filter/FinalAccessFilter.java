package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.lingkang.FinalManager;
import top.lingkang.base.FinalAuth;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public class FinalAccessFilter implements FinalFilterChain {
    private FinalManager manager;

    public FinalAccessFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        for (Map.Entry<String, FinalAuth> entry : manager.getHttpSecurity().getCheckAuths().entrySet()) {
            if (AuthUtils.matcher(entry.getKey(), request.getServletPath())) {
                entry.getValue().check(manager);
            }
        }
    }
}
