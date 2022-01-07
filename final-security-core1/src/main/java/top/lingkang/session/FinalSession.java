package top.lingkang.session;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalSession {
    String getId();

    String getToken();

    Object getAttribute(String var1);

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    boolean isValidInternal(long time);

    void updateLastAccessTime();
}
