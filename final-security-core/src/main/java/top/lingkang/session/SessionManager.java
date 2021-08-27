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

    void putFinalSession(String token, FinalSession finalSession);

    FinalSession getFinalSession(String token);

    FinalSession getFinalSessionById(String id);

    FinalRoles getFinalRoles(String token);

    void addFinalRoles(String token, List<String> roles);

    FinalPermission getFinalPermission(String token);

    void removeSession(String token);

    boolean hasToken(String token);

}
