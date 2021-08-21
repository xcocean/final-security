package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.MessageConstants;
import top.lingkang.entity.SessionEntity;
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
    private SessionEntity sessionEntity;

    public DefaultFinalAuthConfig(SessionEntity sessionEntity) {
        this.sessionEntity = sessionEntity;
    }

    public void checkLogin() {
        FinalManager.isLogin();
    }

    public void hasRoles(String... roles) {
        FinalRoles finalRoles = sessionEntity.getFinalRoles();
        if (finalRoles == null) {
            throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
        for (String role : roles) {
            if (finalRoles.getRoles().contains(role))
                return;
        }
        throw new PermissionException(MessageConstants.unauthorizedMsg);
    }

    public void hasAllRoles(String... roles) {
        FinalRoles finalRoles = sessionEntity.getFinalRoles();
        if (finalRoles == null) {
            throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
        for (String role : roles) {
            if (!finalRoles.getRoles().contains(role))
                throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
    }

    @Override
    public void hasPermission(String... permission) {
        FinalPermission finalPermission = sessionEntity.getFinalPermission();
        if (finalPermission == null) {
            throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
        for (String per : permission) {
            for (String has : finalPermission.getPermission()) {
                if (AuthUtils.patternMatch(has, per)) {
                    return;
                }
            }
        }
        throw new PermissionException(MessageConstants.unauthorizedMsg);
    }

    @Override
    public void hasAllPermission(String... permission) {
        FinalPermission finalPermission = sessionEntity.getFinalPermission();
        if (finalPermission == null) {
            throw new PermissionException(MessageConstants.unauthorizedMsg);
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
                throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
    }
}
