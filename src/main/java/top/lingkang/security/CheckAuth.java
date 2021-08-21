package top.lingkang.security;

/**
 * @author lingkang
 * @date 2021/8/20 16:35
 * @description
 */
public interface CheckAuth {
    boolean checkAll();

    CheckAuth checkLogin();

    CheckAuth hasRoles(String... roles);

    CheckAuth hasAllRoles(String... roles);

    CheckAuth hasPermission(String... permission);

    CheckAuth hasAllPermission(String... permission);
}
