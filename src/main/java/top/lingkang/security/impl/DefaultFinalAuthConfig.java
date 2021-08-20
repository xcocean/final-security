package top.lingkang.security.impl;

import top.lingkang.FinalManager;
import top.lingkang.constants.MessageConstants;
import top.lingkang.entity.SessionEntity;
import top.lingkang.error.PermissionException;
import top.lingkang.security.FinalAuthConfig;

import java.util.List;

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
        List<String> r = sessionEntity.getFinalRoles().getRoles();
        for (String role : roles) {
            if (r.contains(role))
                return;
        }
        throw new PermissionException(MessageConstants.unauthorizedMsg);
    }

    public void hasAllRoles(String... roles) {
        List<String> r = sessionEntity.getFinalRoles().getRoles();
        for (String role : roles) {
            if (!r.contains(role))
                throw new PermissionException(MessageConstants.unauthorizedMsg);
        }
    }
}
