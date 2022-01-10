package top.lingkang.base;

import top.lingkang.helper.FinalHolder;
import top.lingkang.utils.AuthUtils;

import java.util.List;

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
            List<String> has = FinalHolder.getRole(token);
            AuthUtils.checkRole(role, has);
            AuthUtils.checkAndRole(andRole, has);
        }

        if (permission != null || andPermission != null) {
            List<String> has = FinalHolder.getPermission(token);
            AuthUtils.checkPermission(permission, has);
            AuthUtils.checkAndPermission(andPermission, has);
        }
    }

    public FinalAuth existsRoles(String... roles) {
        role = roles;
        return this;
    }

    public FinalAuth existsAllRoles(String... roles) {
        andRole = roles;
        return this;
    }

    public FinalAuth existsPermission(String... permission) {
        this.permission = permission;
        return this;
    }

    public FinalAuth existsAllPermission(String... permission) {
        andPermission = permission;
        return this;
    }
}
