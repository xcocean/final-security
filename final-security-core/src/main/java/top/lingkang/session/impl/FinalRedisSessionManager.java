package top.lingkang.session.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

import java.util.concurrent.TimeUnit;

/**
 * @author lingkang
 * @date 2022/1/16
 */
public class FinalRedisSessionManager implements SessionManager {
    @Autowired
    private FinalManager manager;
    private RedisTemplate<String, Object> redisTemplate;
    private static final String prefixId = "id.";
    private static final String prefixRole = "role.";
    private static final String prefixPermission = "permission.";

    public FinalRedisSessionManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addFinalSession(String token, FinalSession finalSession) {
        redisTemplate.opsForValue().set(token, finalSession, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public FinalSession getSession(String token) {
        Object o = redisTemplate.opsForValue().get(token);
        if (o == null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        return (FinalSession) o;
    }

    @Override
    public FinalSession getSessionById(String id) {
        Object o = redisTemplate.opsForValue().get(prefixId + id);
        if (o == null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        return (FinalSession) o;
    }

    @Override
    public String[] getRoles(String token) {
        Object o = redisTemplate.opsForValue().get(prefixRole + token);
        if (o == null)
            return null;
        return (String[]) o;
    }

    @Override
    public String[] getPermission(String token) {
        Object o = redisTemplate.opsForValue().get(prefixPermission + token);
        if (o == null)
            return null;
        return (String[]) o;
    }

    @Override
    public void addRoles(String token, String... roles) {
        redisTemplate.opsForValue().set(prefixRole + token, roles, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void deleteRoles(String token) {
        redisTemplate.delete(prefixRole + token);
    }

    @Override
    public void addPermission(String token, String... permission) {
        redisTemplate.opsForValue().set(prefixPermission + token, permission, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void deletePermission(String token) {
        redisTemplate.delete(prefixPermission + token);
    }

    @Override
    public FinalSession removeSession(String token) {
        Object o = redisTemplate.opsForValue().get(token);
        redisTemplate.delete(prefixRole + token);
        redisTemplate.delete(prefixPermission + token);
        if (o != null) {
            redisTemplate.delete(token);
            FinalSession session = (FinalSession) o;
            redisTemplate.delete(prefixId + session.getId());
            return session;
        }
        return null;
    }

    @Override
    public boolean existsToken(String token) {
        return redisTemplate.hasKey(token);
    }

    @Override
    public boolean existsId(String id) {
        return redisTemplate.hasKey(prefixId + id);
    }

    @Override
    public void updateLastAccessTime(String token) {
        FinalSession session = getSession(token);
        session.updateLastAccessTime();
        addFinalSession(token, session);
        redisTemplate.expire(prefixId + token, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
        redisTemplate.expire(prefixRole + token, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
        redisTemplate.expire(prefixPermission + token, manager.getProperties().getMaxValid(), TimeUnit.MILLISECONDS);
    }

    @Override
    public long getLastAccessTime(String token) {
        FinalSession session = getSession(token);
        return session.getLastAccessTime();
    }
}
