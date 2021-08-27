package top.lingkang.session;

import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/20 10:11
 * @description
 */
public interface SessionManager {

    // 添加会话
    void putFinalSession(String token, FinalSession finalSession);

    // 根据token获取会话
    FinalSession getFinalSession(String token);

    // 根据登录者id获取会话
    FinalSession getFinalSessionById(String id);

    // 根据token获取角色
    FinalRoles getFinalRoles(String token);

    // 根据token添加角色
    void addFinalRoles(String token, List<String> roles);

    // 根据token获取权限
    FinalPermission getFinalPermission(String token);

    // 根据token移除会话
    void removeSession(String token);

    // 是否存在 token
    boolean hasToken(String token);

    // 更新最后访问时间
    void updateLastAccessTime(String token);
}
