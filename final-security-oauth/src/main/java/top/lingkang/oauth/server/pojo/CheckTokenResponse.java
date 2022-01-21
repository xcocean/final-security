package top.lingkang.oauth.server.pojo;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/21
 */
public class CheckTokenResponse implements Serializable {
    private int code;
    private String id;
    private Object user;
    private String[] role;
    private String[] permission;
    private String client_id;
    private String[] scope;
    private String[] rid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    public String[] getRid() {
        return rid;
    }

    public void setRid(String[] rid) {
        this.rid = rid;
    }
}
