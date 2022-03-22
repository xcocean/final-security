package top.lingkang.base;

import top.lingkang.constants.FinalConstants;
import top.lingkang.constants.FinalSessionKey;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * date 2021/8/20 16:35
 */
public class FinalAuth {
    private String[] role=new String[0], andRole=new String[0];

    public void check(HttpSession session) {
        if (role != null || andRole != null) {
            Object finalRole = session.getAttribute(FinalSessionKey.HAS_ROLE);
            if (finalRole==null)
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);

            String[] has = (String[]) finalRole;
            AuthUtils.checkRole(role, has);
            AuthUtils.checkAndRole(andRole, has);
        }
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String[] getAndRole() {
        return andRole;
    }

    public void setAndRole(String[] andRole) {
        this.andRole = andRole;
    }
}
