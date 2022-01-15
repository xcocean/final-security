package top.lingkang.filter;

import top.lingkang.FinalManager;
import top.lingkang.base.FinalAuth;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public class FinalAuthenticationFilter implements FinalFilterChain {
    private FinalManager manager;
    private HashMap<String, List<FinalAuth>> authCache = new HashMap<>();

    public FinalAuthenticationFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        List<FinalAuth> finalAuths = authCache.get(request.getServletPath());
        if (finalAuths == null) {
            finalAuths = new ArrayList<>();
            for (Map.Entry<String, FinalAuth> entry : manager.getHttpSecurity().getCheckAuths().entrySet()) {
                if (AuthUtils.matcher(entry.getKey(), request.getServletPath())) {
                    finalAuths.add(entry.getValue());
                }
            }
            authCache.put(request.getServletPath(), finalAuths);
        }
        for (FinalAuth auth : finalAuths) {
            auth.check(manager);
        }
    }
}
