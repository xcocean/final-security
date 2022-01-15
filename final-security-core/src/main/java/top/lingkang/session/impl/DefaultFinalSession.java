package top.lingkang.session.impl;

import top.lingkang.session.FinalSession;

import java.io.Serializable;

/**
 * @author lingkang
 * @date 2021/8/13 10:24
 * @description
 */
public class DefaultFinalSession implements FinalSession, Serializable {
    private static final long serialVersionUID = 1L;
    private String id, token;
    private Object user;
    private volatile long creationTime, lastAccessTime;

    public DefaultFinalSession(String id, Object user, String token) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.creationTime = this.lastAccessTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getUser() {
        return user;
    }

    @Override
    public void setUser(Object user) {
        this.user = user;
    }

    public void updateLastAccessTime() {
        lastAccessTime = System.currentTimeMillis();
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public long getCreateTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return "DefaultFinalSession{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", creationTime=" + creationTime +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}
