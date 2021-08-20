package top.lingkang.session.impl;

import top.lingkang.entity.SessionEntity;
import top.lingkang.session.SessionManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description
 */
public class DefaultSessionManager implements SessionManager {
    private static ConcurrentMap<String, SessionEntity> concurrentMap = new ConcurrentHashMap<String, SessionEntity>();

    public SessionEntity get(String token) {
        return concurrentMap.get(token);
    }

    public void put(String token, SessionEntity sessionEntity) {
        concurrentMap.put(token, sessionEntity);
    }

    public void remove(String token) {
        concurrentMap.remove(token);
    }

    public boolean containsKey(String token) {
        return concurrentMap.containsKey(token);
    }
}
