package top.lingkang.oauth.server.pojo;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class OauthToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String token;
    private String refreshToken;
    private long expire;
    private long refreshExpire;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getRefreshExpire() {
        return refreshExpire;
    }

    public void setRefreshExpire(long refreshExpire) {
        this.refreshExpire = refreshExpire;
    }
}
