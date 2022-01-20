package top.lingkang.oauth.server.entity;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class RefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private  String refreshToken;
    private  long createTime;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
