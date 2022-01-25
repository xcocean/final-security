package top.lingkang.session;

import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:11
 * @description
 */
public interface SessionManager {
    // 添加会话
    void addFinalSession(String token, FinalSession finalSession);

    // 根据token获取会话
    FinalSession getSession(String token);

    // 根据登录者id获取会话
    FinalSession getSessionById(String id);

    // 根据token获取角色
    String[] getRoles(String token);

    String[] getPermission(String token);

    // 根据token添加角色
    void addRoles(String token, String... roles);

    void deleteRoles(String token);

    void addPermission(String token, String... permission);

    void deletePermission(String token);

    // 根据token移除会话
    FinalSession removeSession(String token);

    // 是否存在 token
    boolean existsToken(String token);

    boolean existsId(String id);

    // 更新会话最后访问时间
    void updateLastAccessTime(String token);

    // 获取 会话 最后访问时间
    long getLastAccessTime(String token);

    void cleanExpires();
}
