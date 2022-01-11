package top.lingkang.base;

import top.lingkang.helper.FinalHolder;
import top.lingkang.utils.AuthUtils;

/**
 * @author lingkang
 * @date 2021/8/20 16:35
 * @description
 */
public class FinalAuth {
    private String[] role, andRole, permission, andPermission;

    public void check() {
        String token = FinalHolder.getToken();
        if (role != null || andRole != null) {
            String[] has = FinalHolder.getRole(token);
            AuthUtils.checkRole(role, has);
            AuthUtils.checkAndRole(andRole, has);
        }

        if (permission != null || andPermission != null) {
            String[] has = FinalHolder.getPermission(token);
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
