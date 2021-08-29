package top.lingkang.session.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import top.lingkang.FinalManager;
import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;
import top.lingkang.security.impl.DefaultFinalRoles;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FinalRedisSessionManager implements SessionManager {

    private static final String prefix_permission = "permission:";
    private static final String prefix_session = "session:";
    private static final String prefix_roles = "roles:";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void putFinalSession(String token, FinalSession finalSession) {
        redisTemplate.opsForValue().set(prefix_session + token, finalSession, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public FinalSession getFinalSession(String token) {
        return (FinalSession) redisTemplate.opsForValue().get(prefix_session + token);
    }

    @Override
    public FinalSession getFinalSessionById(String id) {
        Set<String> keys = redisTemplate.keys(prefix_session + "*");
        if (keys != null) {
            for (String key : keys) {
                FinalSession session = (FinalSession) redisTemplate.opsForValue().get(prefix_session + key);
                if (session.getId().equals(id)) {
                    return session;
                }
            }
        }
        return null;
    }

    @Override
    public FinalRoles getFinalRoles(String token) {
        return (FinalRoles) redisTemplate.opsForValue().get(prefix_roles + token);
    }

    @Override
    public void addFinalRoles(String token, List<String> roles) {
        FinalRoles finalRoles = getFinalRoles(token);
        if (finalRoles == null) {
            finalRoles = new DefaultFinalRoles();
        }
        finalRoles.addRoles(roles);
        redisTemplate.opsForValue().set(prefix_roles + token, finalRoles, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void updateFinalRoles(String token, FinalRoles finalRoles) {

    }

    @Override
    public FinalPermission getFinalPermission(String token) {
        return null;
    }

    @Override
    public void addFinalPermission(String token, List<String> roles) {

    }

    @Override
    public void updateFinalPermission(String token, FinalPermission permission) {

    }

    @Override
    public void removeSession(String token) {
        redisTemplate.delete(prefix_session + token);
        redisTemplate.delete(prefix_roles + token);
        redisTemplate.delete(prefix_permission + token);
    }

    @Override
    public boolean hasToken(String token) {
        return redisTemplate.hasKey(prefix_session + token);
    }

    @Override
    public void updateLastAccessTime(String token) {
        // 会话
        FinalSession finalSession = getFinalSession(token);
        finalSession.updateLastAccessTime();
        this.putFinalSession(token, finalSession);

        // 角色
        redisTemplate.expire(prefix_roles + token, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
        // 权限
        redisTemplate.expire(prefix_permission + token, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
    }
}
