package top.lingkang.base.impl;

import top.lingkang.FinalManager;
import top.lingkang.base.FinalFilterCheck;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;

/**
 * @author lingkang
 * @date 2022/1/9
 */
public class DefaultFinalFilterCheck implements FinalFilterCheck {

    private FinalManager manager;

    public DefaultFinalFilterCheck(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public String checkTokenExists() throws FinalTokenException {
        return manager.getToken();
    }

    @Override
    public void checkTokenPrepare(String token) throws FinalTokenException {
        long last = manager.getSession(token).getLastAccessTime();
        if (AuthUtils.checkReserveTime(manager.getProperties().getPrepareTime(), manager.getProperties().getMaxValid(), last)) {// 180s 预留时间
            // 不满足预留时间，注销。
            manager.getSessionManager().removeSession(token);
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }
    }
}
