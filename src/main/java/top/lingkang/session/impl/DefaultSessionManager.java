package top.lingkang.session.impl;

import top.lingkang.entity.SessionEntity;
import top.lingkang.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public SessionEntity getById(String id) {
        for (Map.Entry<String, SessionEntity> map : concurrentMap.entrySet()) {
            if (map.getValue().getFinalSession().getId().equals(id)) {
                return map.getValue();
            }
        }
        return null;
    }

    public List<SessionEntity> getAllSessionEntity() {
        List<SessionEntity> list = new ArrayList<SessionEntity>();
        for (Map.Entry<String, SessionEntity> map : concurrentMap.entrySet()) {
            list.add(map.getValue());
        }
        return list;
    }

    public int getAllSessionEntityCount() {
        if (concurrentMap != null)
            return concurrentMap.size();
        return 0;
    }
}
