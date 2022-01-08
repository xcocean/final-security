package top.lingkang.session;

import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalSession {
    String getId();

    String getToken();

    String getRefreshToken();

    Object getAttribute(String var1);

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    Map<String, Object> getData();

    void setData(Map<String, Object> data);

    boolean isValidInternal(long time);

    void updateLastAccessTime();

    long getLastAccessTime();

    long getCreateTime();
}
