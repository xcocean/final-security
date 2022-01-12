package top.lingkang.session;

import java.util.Arrays;

/**
 * @author lingkang
 * Created by 2022/1/12
 */
public class FinalSessionData {
    private String id;
    private String token;
    private String[] role;
    private String[] permission;

    public FinalSessionData() {
    }

    public FinalSessionData(String id, String token, String[] role, String[] permission) {
        this.id = id;
        this.token = token;
        this.role = role;
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "FinalSessionData{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", role=" + Arrays.toString(role) +
                ", permission=" + Arrays.toString(permission) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }
}
