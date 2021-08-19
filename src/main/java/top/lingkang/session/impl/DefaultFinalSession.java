package top.lingkang.session.impl;

import top.lingkang.session.FinalSession;
import top.lingkang.FinalManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingkang
 * @date 2021/8/13 10:24
 * @description
 */
public class DefaultFinalSession implements FinalSession {
    private final Map<String, Object> data = new ConcurrentHashMap<String, Object>();
    private String token;
    private String id;
    private volatile long creationTime;

    public DefaultFinalSession(String id,String token) {
        this.id=id;
        this.token = token;
        this.creationTime = System.currentTimeMillis();
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

    public Object getAttribute(String var1) {
        return data.get(var1);
    }

    public void setAttribute(String var1, Object var2) {
        data.put(var1, var2);
    }

    public void removeAttribute(String var1) {
        data.remove(var1);
    }

}
