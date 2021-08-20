package top.lingkang.security;

import top.lingkang.error.NotLoginException;

/**
 * @author lingkang
 * @date 2021/8/20 16:35
 * @description
 */
public interface CheckAuth {
    boolean checkAll();
    CheckAuth checkLogin();
    CheckAuth hasRoles(String ... roles);
}
