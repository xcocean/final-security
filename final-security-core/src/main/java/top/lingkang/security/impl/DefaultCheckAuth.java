package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalAuthConfig;

/**
 * @author lingkang
 * @date 2021/8/20 16:37
 * @description
 */
public class DefaultCheckAuth implements CheckAuth {
    private boolean checkLogin;
    private String[] hasRoles, hasAllRoles, permission, hasAllpermission;

    public boolean checkAll() {
        String token=FinalManager.getToken();
        FinalAuthConfig authConfig = new DefaultFinalAuthConfig(token);
        if (checkLogin)
            authConfig.checkLogin();

        if (hasRoles != null) {
            authConfig.hasRoles(hasRoles);
        }
        if (hasAllRoles != null) {
            authConfig.hasAllRoles(hasAllRoles);
        }
        if (permission != null) {
            authConfig.hasPermission(permission);
        }
        if (hasAllpermission != null) {
            authConfig.hasAllPermission(hasAllpermission);
        }
        return true;
    }

    public CheckAuth checkLogin() throws FinalNotLoginException {
        checkLogin = true;
        return this;
    }

    public CheckAuth hasRoles(String... roles) {
        hasRoles = roles;
        checkLogin = true;
        return this;
    }

    @Override
    public CheckAuth hasAllRoles(String... roles) {
        checkLogin = true;
        hasAllRoles = roles;
        return this;
    }

    @Override
    public CheckAuth hasPermission(String... permission) {
        checkLogin = true;
        this.permission = permission;
        return this;
    }

    @Override
    public CheckAuth hasAllPermission(String... permission) {
        checkLogin = true;
        this.hasAllpermission = permission;
        return null;
    }
}
