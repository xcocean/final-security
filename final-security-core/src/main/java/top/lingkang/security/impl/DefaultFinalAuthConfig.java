package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.MessageConstants;
import top.lingkang.error.PermissionException;
import top.lingkang.security.FinalAuthConfig;
import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;
import top.lingkang.utils.AuthUtils;

/**
 * @author lingkang
 * @date 2021/8/19 17:17
 * @description
 */
public class DefaultFinalAuthConfig implements FinalAuthConfig {
    private String token;

    public DefaultFinalAuthConfig(String token) {
        this.token = token;
    }

    public void checkLogin() {
        FinalManager.isLogin(token);
    }

    public void hasRoles(String... roles) {
        FinalRoles finalRoles = FinalManager.getSessionManager().getFinalRoles(token);
        if (finalRoles == null) {
            throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String role : roles) {
            for (String has : finalRoles.getRoles()) {
                if (AuthUtils.patternMatch(has, role)) {
                    return;
                }
            }
        }
        throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
    }

    public void hasAllRoles(String... roles) {
        FinalRoles finalRoles = FinalManager.getSessionManager().getFinalRoles(token);
        if (finalRoles == null) {
            throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String role : roles) {
            boolean exist = false;
            for (String has : finalRoles.getRoles()) {
                if (AuthUtils.patternMatch(has, role)) {
                    exist = true;
                    break;
                }
            }
            if (!exist)
                throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
    }

    @Override
    public void hasPermission(String... permission) {
        FinalPermission finalPermission = FinalManager.getSessionManager().getFinalPermission(token);
        if (finalPermission == null) {
            throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String per : permission) {
            for (String has : finalPermission.getPermission()) {
                if (AuthUtils.patternMatch(has, per)) {
                    return;
                }
            }
        }
        throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
    }

    @Override
    public void hasAllPermission(String... permission) {
        FinalPermission finalPermission = FinalManager.getSessionManager().getFinalPermission(token);
        if (finalPermission == null) {
            throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String per : permission) {
            boolean ok = false;
            for (String has : finalPermission.getPermission()) {
                if (AuthUtils.patternMatch(has, per)) {
                    ok = true;
                    break;
                }
            }
            if (!ok)
                throw new PermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
    }
}
