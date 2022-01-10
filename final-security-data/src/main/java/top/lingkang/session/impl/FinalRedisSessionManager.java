///*
//package top.lingkang.session.impl;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import top.lingkang.FinalManager;
//import top.lingkang.constants.MessageConstants;
//import top.lingkang.error.FinalTokenException;
//import top.lingkang.session.FinalSession;
//import top.lingkang.session.SessionManager;
//
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//public class FinalRedisSessionManager implements SessionManager {
//
//    private static final String prefix_permission = "permission:";
//    private static final String prefix_session = "session:";
//    private static final String prefix_roles = "roles:";
//    private final RedisTemplate redisTemplate;
//
//    public FinalRedisSessionManager(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public void putFinalSession(String token, FinalSession finalSession) {
//        redisTemplate.opsForValue().set(prefix_session + token, finalSession, FinalManager.getp(), TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public FinalSession getFinalSession(String token) {
//        Object o = redisTemplate.opsForValue().get(prefix_session + token);
//        if (o != null) {
//            return (FinalSession) o;
//        }
//        return null;
//    }
//
//    @Override
//    public FinalSession getFinalSessionById(String id) {
//        Set<String> keys = redisTemplate.keys(prefix_session + "*");
//        if (keys != null) {
//            for (String key : keys) {
//                FinalSession session = (FinalSession) redisTemplate.opsForValue().get(key);
//                if (session.getId().equals(id)) {
//                    return session;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<String> getRoles(String token) {
//        Object o = redisTemplate.opsForValue().get(prefix_roles + token);
//        if (o != null) {
//            return (List<String>) o;
//        }
//        return null;
//    }
//
//    @Override
//    public void addRoles(String token, List<String> roles) {
//        redisTemplate.opsForValue().set(prefix_roles + token, roles, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public void updateRoles(String token, List<String> roles) {
//        Long expire = redisTemplate.getExpire(prefix_roles + token);
//        if (expire < -1) {
//            // 不存在，创建
//            addRoles(token, roles);
//        } else {
//            redisTemplate.opsForValue().set(prefix_roles + token, roles, expire, TimeUnit.MILLISECONDS);
//        }
//    }
//
//    @Override
//    public void deleteRoles(String token) {
//        redisTemplate.delete(prefix_roles + token);
//    }
//
//    @Override
//    public List<String> getPermission(String token) {
//        Object o = redisTemplate.opsForValue().get(prefix_permission + token);
//        if (o != null) {
//            return (List<String>) o;
//        }
//        return null;
//    }
//
//    @Override
//    public void addPermission(String token, List<String> permission) {
//        redisTemplate.opsForValue().set(prefix_permission + token, permission, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public void updatePermission(String token, List<String> permission) {
//        Long expire = redisTemplate.getExpire(prefix_permission + token);
//        if (expire < -1) {
//            // 不存在，创建
//            addPermission(token, permission);
//        } else {
//            redisTemplate.opsForValue().set(prefix_permission + token, permission, expire, TimeUnit.MILLISECONDS);
//        }
//    }
//
//    @Override
//    public void deletePermission(String token) {
//        redisTemplate.delete(prefix_permission + token);
//    }
//
//    @Override
//    public void removeSession(String token) {
//        redisTemplate.delete(prefix_session + token);
//        redisTemplate.delete(prefix_roles + token);
//        redisTemplate.delete(prefix_permission + token);
//    }
//
//    @Override
//    public boolean hasToken(String token) {
//        return redisTemplate.hasKey(prefix_session + token);
//    }
//
//    @Override
//    public void updateLastAccessTime(String token) {
//        // 会话
//        FinalSession finalSession = getFinalSession(token);
//        if (finalSession == null) {
//            throw new FinalTokenException(MessageConstants.TOKEN_INVALID);
//        }
//        finalSession.updateLastAccessTime();
//        this.putFinalSession(token, finalSession);
//
//        // 角色
//        redisTemplate.expire(prefix_roles + token, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
//        // 权限
//        redisTemplate.expire(prefix_permission + token, FinalManager.getSessionMaxValid(), TimeUnit.MILLISECONDS);
//    }
//
//    @Override
//    public long getExpire(String token) {
//        Long expire = redisTemplate.getExpire(prefix_session + token);
//        return expire == null ? -1L : expire;
//    }
//}
//*/
