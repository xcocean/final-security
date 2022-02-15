package top.lingkang.http;

import javax.servlet.http.HttpSession;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalSecurityHolder {
    
    public void login(String username, String[] role, String[] permission) {
        HttpSession session = FinalContextHolder.getRequest().getSession();
        session.setAttribute("finalUsername", username);
        session.setAttribute("finalRole", role);
        session.setAttribute("finalPermission", permission);
        session.setAttribute("finalLogin", true);
    }

    public void logout() {
        HttpSession session = FinalContextHolder.getRequest().getSession();
        session.removeAttribute("finalUsername");
        session.removeAttribute("finalRole");
        session.removeAttribute("finalPermission");
        session.removeAttribute("finalLogin");
    }
    
    public String[] getRole() {
        Object finalRole = FinalContextHolder.getRequest().getSession().getAttribute("finalRole");
        if (finalRole != null) {
            return (String[]) finalRole;
        }
        return null;
    }

    public String[] getPermission() {
        Object permission = FinalContextHolder.getRequest().getSession().getAttribute("finalPermission");
        if (permission != null) {
            return (String[]) permission;
        }
        return null;
    }

    public String getUsername() {
        Object username = FinalContextHolder.getRequest().getSession().getAttribute("finalUsername");
        if (username != null) {
            return (String) username;
        }
        return null;
    }

    public boolean isLogin(){
        Object login = FinalContextHolder.getRequest().getSession().getAttribute("finalLogin");
        if (login!=null)
            return (boolean) login;
        return false;
    }
}
