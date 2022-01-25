package top.lingkang.session.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.SpringBeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description 会话管理，存储于内存中
 */
public class FinalMemorySessionManager implements SessionManager {
    private ConcurrentMap<String, String> idAndToken = new ConcurrentHashMap<>();
    private ConcurrentMap<String, FinalSession> session = new ConcurrentHashMap<String, FinalSession>();
    private ConcurrentMap<String, String[]> roles = new ConcurrentHashMap<String, String[]>();
    private ConcurrentMap<String, String[]> permission = new ConcurrentHashMap<String, String[]>();

    @Override
    public void addFinalSession(String token, FinalSession finalSession) {
        session.put(token, finalSession);
        idAndToken.put(finalSession.getId(), token);
    }

    @Override
    public FinalSession getSession(String token) {
        return session.get(token);
    }

    @Override
    public FinalSession getSessionById(String id) {
        String s = idAndToken.get(id);
        if (s == null)
            return null;
        return session.get(s);
    }

    @Override
    public String[] getRoles(String token) {
        return roles.get(token);
    }

    @Override
    public void addRoles(String token, String... role) {
        if (role == null)
            return;
        this.roles.put(token, AuthUtils.removeRepeat(role));
    }

    @Override
    public String[] getPermission(String token) {
        return permission.get(token);
    }

    @Override
    public void deleteRoles(String token) {
        roles.remove(token);
    }

    @Override
    public void addPermission(String token, String... permission) {
        if (permission == null)
            return;
        this.permission.put(token, AuthUtils.removeRepeat(permission));
    }

    @Override
    public void deletePermission(String token) {
        permission.remove(token);
    }

    @Override
    public FinalSession removeSession(String token) {
        FinalSession remove = session.remove(token);
        if (remove != null) {
            idAndToken.remove(remove.getId());
        }
        roles.remove(token);
        permission.remove(token);
        return remove;
    }

    public boolean existsToken(String token) {
        return session.containsKey(token);
    }

    @Override
    public boolean existsId(String id) {
        return idAndToken.containsKey(id);
    }

    @Override
    public void updateLastAccessTime(String token) {
        FinalSession finalSession = session.get(token);
        if (finalSession == null) {
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }
        finalSession.updateLastAccessTime();
    }

    @Override
    public long getLastAccessTime(String token) {
        return session.get(token).getLastAccessTime();
    }

    public void cleanExpires() {
        FinalManager manager = SpringBeanUtils.getBean(FinalManager.class);
        Long maxValid = manager.getProperties().getMaxValid();
        if (maxValid < 1800000L) {// 30分钟
            maxValid = 1800000L;
        }
        if (session.size() == 0)
            return;
        long current = System.currentTimeMillis() - maxValid + 300000L;
        for (Map.Entry<String, FinalSession> entry : session.entrySet()) {
            if (entry.getValue().getType() == null)
                if (entry.getValue().getLastAccessTime() < current) {
                    removeSession(entry.getValue().getToken());
                }
        }
    }
}
