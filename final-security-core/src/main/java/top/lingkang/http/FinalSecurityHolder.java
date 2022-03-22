package top.lingkang.http;

import top.lingkang.constants.FinalSessionKey;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalSecurityHolder {

    public void login(String username, String[] role) {
        HttpSession session = FinalContextHolder.getRequest().getSession();
        session.setAttribute(FinalSessionKey.LOGIN_USERNAME, username);
        session.setAttribute(FinalSessionKey.HAS_ROLE, role);
        session.setAttribute(FinalSessionKey.IS_LOGIN, true);
    }

    public void logout() {
        HttpSession session = FinalContextHolder.getRequest().getSession();
        session.removeAttribute(FinalSessionKey.IS_LOGIN);
        session.removeAttribute(FinalSessionKey.HAS_ROLE);
        session.removeAttribute(FinalSessionKey.LOGIN_USERNAME);
    }

    public String[] getRole() {
        Object finalRole = FinalContextHolder.getRequest().getSession().getAttribute(FinalSessionKey.HAS_ROLE);
        if (finalRole != null) {
            return (String[]) finalRole;
        }
        return null;
    }

    public String getUsername() {
        Object username = FinalContextHolder.getRequest().getSession().getAttribute(FinalSessionKey.LOGIN_USERNAME);
        if (username != null) {
            return (String) username;
        }
        return null;
    }

    public boolean isLogin() {
        Object login = FinalContextHolder.getRequest().getSession().getAttribute(FinalSessionKey.IS_LOGIN);
        if (login != null)
            return (boolean) login;
        return false;
    }
}
