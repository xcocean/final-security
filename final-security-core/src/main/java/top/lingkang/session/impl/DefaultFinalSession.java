package top.lingkang.session.impl;

import top.lingkang.session.FinalSession;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lingkang
 * @date 2021/8/13 10:24
 * @description
 */
public class DefaultFinalSession implements FinalSession, Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> data = new HashMap<>();
    private String token;
    private String refreshToken;
    private String id;
    private volatile long creationTime, lastAccessTime;

    public DefaultFinalSession(String id, String token, String refreshToken) {
        this.id = id;
        this.token = token;
        this.refreshToken = refreshToken;
        this.creationTime = this.lastAccessTime = System.currentTimeMillis();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Object getAttribute(String var1) {
        return data.get(var1);
    }

    public void setAttribute(String var1, Object var2) {
        data.put(var1, var2);
    }

    public void removeAttribute(String var1) {
        data.remove(var1);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isValidInternal(long time) {
        return System.currentTimeMillis() - lastAccessTime < time;
    }

    public void updateLastAccessTime() {
        lastAccessTime = System.currentTimeMillis();
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public long getCreateTime() {
        return creationTime;
    }
}
