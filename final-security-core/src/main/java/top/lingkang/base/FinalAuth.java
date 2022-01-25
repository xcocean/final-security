package top.lingkang.base;

import top.lingkang.FinalManager;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;

/**
 * @author lingkang
 * @date 2021/8/20 16:35
 * @description
 */
public class FinalAuth {
    private String[] role, andRole, permission, andPermission;

    public void check(FinalManager manager) {
        String token = manager.getToken();
        if (token==null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);

        if (role != null || andRole != null) {
            String[] has = manager.getSessionManager().getRoles(token);
            AuthUtils.checkRole(role, has);
            AuthUtils.checkAndRole(andRole, has);
        }

        if (permission != null || andPermission != null) {
            String[] has = manager.getSessionManager().getPermission(token);
            AuthUtils.checkPermission(permission, has);
            AuthUtils.checkAndPermission(andPermission, has);
        }
    }

    public FinalAuth hasRoles(String... roles) {
        role = roles;
        return this;
    }

    public FinalAuth hasAllRoles(String... roles) {
        andRole = roles;
        return this;
    }

    public FinalAuth hasPermission(String... permission) {
        this.permission = permission;
        return this;
    }

    public FinalAuth hasAllPermission(String... permission) {
        andPermission = permission;
        return this;
    }
}
