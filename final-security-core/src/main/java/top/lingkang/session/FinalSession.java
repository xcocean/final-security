package top.lingkang.session;

import java.io.Serializable;

/**
 * @author lingkang
 * @date 2021/8/11 15:16
 * @description
 */
public interface FinalSession {
    long getCreationTime();

    String getId();

    String getToken();

    Object getAttribute(String var1);

    void setAttribute(String var1, Object var2);

    void removeAttribute(String var1);

    boolean isValidInternal(long time);

    void updateLastAccessTime();

}
