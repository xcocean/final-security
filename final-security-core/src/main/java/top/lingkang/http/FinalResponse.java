package top.lingkang.http;

/**
 * @author lingkang
 * @date 2021/8/13 17:14
 * @description
 */
public interface FinalResponse {
    /**
     * expiry == -1 表示关闭浏览器会话结束
     * @param name
     * @param value
     * @param expiry
     */
    void addToken(String name, String value, int expiry);
}
