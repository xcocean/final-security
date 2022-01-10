package top.lingkang.filter;

import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public class FinalPrepareFilter implements FinalFilterChain {
    private FinalManager manager;

    public FinalPrepareFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) {
        // 检查令牌时长情况
        String token = manager.getToken();
        long last = manager.getSessionManager().getLastAccessTime(token);
        if (AuthUtils.checkReserveTime(manager.getProperties().getPrepareTime(), manager.getProperties().getMaxValid(), last)) {// 180s 预留时间
            // 不满足预留时间，注销。
            manager.getSessionManager().removeSession(token);
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }
    }
}
