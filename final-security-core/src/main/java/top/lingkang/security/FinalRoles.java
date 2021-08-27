package top.lingkang.security;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/13 15:16
 * @description
 */
public interface FinalRoles {
    void addRole(String role);

    void addRoles(List<String> roles);

    void deleteRole(String role);

    List<String> getRoles();
}
