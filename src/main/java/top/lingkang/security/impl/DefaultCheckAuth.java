package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.MessageConstants;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.PermissionException;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalAuthConfig;

/**
 * @author lingkang
 * @date 2021/8/20 16:37
 * @description
 */
public class DefaultCheckAuth implements CheckAuth {
    private boolean checkLogin;
    private String[] hasRoles;

    public boolean checkAll() {
        FinalAuthConfig authConfig = new DefaultFinalAuthConfig(FinalManager.getSessionEntity());
        if (checkLogin)
            authConfig.checkLogin();
        if (hasRoles != null)
            try {
                authConfig.hasRoles(hasRoles);
            } catch (Exception e) {
                throw new PermissionException(MessageConstants.unauthorizedMsg);
            }
        return true;
    }

    public CheckAuth checkLogin() throws NotLoginException {
        checkLogin = true;
        return this;
    }

    public CheckAuth hasRoles(String... roles) {
        hasRoles = roles;
        checkLogin = true;
        return this;
    }
}
