package top.lingkang.session.impl;

import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;
import top.lingkang.security.impl.DefaultFinalRoles;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description
 */
public class DefaultFinalSessionManager implements SessionManager {
    private static ConcurrentMap<String, FinalSession> session = new ConcurrentHashMap<String, FinalSession>();
    private static ConcurrentMap<String, FinalRoles> roles = new ConcurrentHashMap<String, FinalRoles>();
    private static ConcurrentMap<String, FinalPermission> permission = new ConcurrentHashMap<String, FinalPermission>();


    @Override
    public void putFinalSession(String token, FinalSession finalSession) {
        session.put(token, finalSession);
    }

    @Override
    public FinalSession getFinalSession(String token) {
        return session.get(token);
    }

    @Override
    public FinalSession getFinalSessionById(String id) {
        for (Map.Entry<String, FinalSession> map : session.entrySet()) {
            if (map.getValue().getId().equals(id)) {
                return map.getValue();
            }
        }
        return null;
    }

    @Override
    public FinalRoles getFinalRoles(String token) {
        return roles.get(token);
    }

    @Override
    public void addFinalRoles(String token, List<String> roles) {
        FinalRoles finalRoles = this.roles.get(token);
        if (finalRoles==null){
            finalRoles=new DefaultFinalRoles();
        }
        finalRoles.addRoles(roles);
        this.roles.put(token, finalRoles);
    }

    @Override
    public FinalPermission getFinalPermission(String token) {
        return permission.get(token);
    }

    @Override
    public void removeSession(String token) {
        session.remove(token);
        roles.remove(token);
        permission.remove(token);
    }

    public boolean hasToken(String token) {
        return session.containsKey(token);
    }

    @Override
    public void updateLastAccessTime(String token) {
        FinalSession finalSession = session.get(token);
        finalSession.updateLastAccessTime();
        session.put(token, finalSession);
    }
}
