package top.lingkang.entity;

import top.lingkang.security.FinalPermission;
import top.lingkang.security.FinalRoles;
import top.lingkang.session.FinalSession;

/**
 * @author lingkang
 * @date 2021/8/13 15:13
 * @description
 */
public class SessionEntity {

    private FinalSession finalSession;

    private FinalRoles finalRoles;

    private FinalPermission finalPermission;

    public FinalSession getFinalSession() {
        return finalSession;
    }

    public void setFinalSession(FinalSession finalSession) {
        this.finalSession = finalSession;
    }

    public FinalRoles getFinalRoles() {
        return finalRoles;
    }

    public void setFinalRoles(FinalRoles finalRoles) {
        this.finalRoles = finalRoles;
    }

    public FinalPermission getFinalPermission() {
        return finalPermission;
    }

    public void setFinalPermission(FinalPermission finalPermission) {
        this.finalPermission = finalPermission;
    }
}
