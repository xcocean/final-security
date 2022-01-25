package top.lingkang.filter;

import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class CheckTokenValidFilter implements FinalFilterChain{
    private FinalManager manager;

    public CheckTokenValidFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) {
        String token = manager.getToken();
        if (token==null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);

        // 检查令牌预留时长情况
        if (manager.getProperties().getPrepareCheck()) {
            long last = manager.getSessionManager().getLastAccessTime(token);
            if (AuthUtils.checkReserveTime(
                    manager.getProperties().getPrepareTime(),
                    manager.getProperties().getMaxValid(),
                    last)) {
                // 不满足预留时间，注销。
                manager.getSessionManager().removeSession(token);
                throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
            }
        } else {
            // 检查令牌时效
            if (AuthUtils.checkTokenValid(
                    manager.getSessionManager().getLastAccessTime(token),
                    manager.getProperties().getMaxValid())) {
                manager.getSessionManager().removeSession(token);
                throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
            }
        }
    }
}
