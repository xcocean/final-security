package top.lingkang.security;

import top.lingkang.error.NotLoginException;

/**
 * @author lingkang
 * @date 2021/8/19 17:10
 * @description 鉴权配置
 */
public interface FinalAuthConfig {
    // 检查登录
    void checkLogin() throws NotLoginException;

    // 有角色即可通过
    void hasRoles(String... roles);

    // 有所有角色即可通过
    void hasAllRoles(String... roles);
}
