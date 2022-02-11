package top.lingkang.base;

import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * date 2021/8/20 16:35
 */
public class FinalAuth {
    private String[] role, andRole, permission, andPermission;

    public void check(HttpSession session) {
        if (role != null || andRole != null) {
            Object finalRole = session.getAttribute("finalRole");
            if (finalRole==null)
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);

            String[] has = (String[]) finalRole;
            AuthUtils.checkRole(role, has);
            AuthUtils.checkAndRole(andRole, has);
        }

        if (permission != null || andPermission != null) {
            Object finalPermission = session.getAttribute("finalPermission");
            if (finalPermission==null)
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);

            String[] has = (String[]) finalPermission;
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
