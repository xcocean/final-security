package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.MessageConstants;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.security.FinalAuthConfig;
import top.lingkang.utils.AuthUtils;

import java.util.List;

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
        List<String> rol = FinalManager.getSessionManager().getRoles(token);
        if (rol.isEmpty()) {
            throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String role : roles) {
            for (String has : rol) {
                if (AuthUtils.patternMatch(has, role)) {
                    return;
                }
            }
        }
        throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
    }

    public void hasAllRoles(String... roles) {
        List<String> rol = FinalManager.getSessionManager().getRoles(token);
        if (rol.isEmpty()) {
            throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String role : roles) {
            boolean exist = false;
            for (String has : rol) {
                if (AuthUtils.patternMatch(has, role)) {
                    exist = true;
                    break;
                }
            }
            if (!exist)
                throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
    }

    @Override
    public void hasPermission(String... permission) {
        List<String> pers = FinalManager.getSessionManager().getPermission(token);
        if (pers.isEmpty()) {
            throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String per : permission) {
            for (String has : pers) {
                if (AuthUtils.patternMatch(has, per)) {
                    return;
                }
            }
        }
        throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
    }

    @Override
    public void hasAllPermission(String... permission) {
        List<String> finalPermission = FinalManager.getSessionManager().getPermission(token);
        if (finalPermission == null) {
            throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
        for (String per : permission) {
            boolean ok = false;
            for (String has : finalPermission) {
                if (AuthUtils.patternMatch(has, per)) {
                    ok = true;
                    break;
                }
            }
            if (!ok)
                throw new FinalPermissionException(MessageConstants.UNAUTHORIZED_MSG);
        }
    }
}
