package top.lingkang.base;

import top.lingkang.error.FinalTokenException;

public interface FinalFilterCheck {
    /**
     * 检查token是否存在
     * @return token
     * @throws FinalTokenException
     */
    String checkTokenExists() throws FinalTokenException;

    /**
     * 检查token预留时间，预留时间不足抛出token无效，并移除token
     * @throws FinalTokenException
     */
    void checkTokenPrepare(String token) throws FinalTokenException;
}
