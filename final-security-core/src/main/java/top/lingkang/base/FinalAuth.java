package top.lingkang.base;

import top.lingkang.constants.FinalConstants;
import top.lingkang.constants.FinalSessionKey;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.utils.AuthUtils;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author lingkang
 * date 2021/8/20 16:35
 * 执行授权检查
 */
public class FinalAuth implements Serializable {
    private String[] role = new String[0], andRole = new String[0];

    public void check(HttpSession session) {
        // 检查登录
        if (session.getAttribute(FinalSessionKey.IS_LOGIN) == null) {
            throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_MSG);
        }
        if (role.length != 0 || andRole.length != 0) {
            Object finalRole = session.getAttribute(FinalSessionKey.HAS_ROLE);
            if (finalRole == null)
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);

            String[] has = (String[]) finalRole;
            if (has.length==0)
                throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);

            // 检查
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
