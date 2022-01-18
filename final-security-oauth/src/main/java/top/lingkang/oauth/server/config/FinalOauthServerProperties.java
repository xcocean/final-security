package top.lingkang.oauth.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lingkang
 * @date 2022/1/10
 */
@Component(value = "finalOauthServerProperties")
@ConfigurationProperties(prefix = "final.security.oauth.server")
public class FinalOauthServerProperties {
    private long tokenMaxTime = 1800000L;// 30分钟
    private long refreshTokenMaxTime=1296000000L;// 15 天
    private boolean autoRefreshToken=false;



    public long getTokenMaxTime() {
        return tokenMaxTime;
    }

    public void setTokenMaxTime(long tokenMaxTime) {
        this.tokenMaxTime = tokenMaxTime;
    }

    public long getRefreshTokenMaxTime() {
        return refreshTokenMaxTime;
    }

    public void setRefreshTokenMaxTime(long refreshTokenMaxTime) {
        this.refreshTokenMaxTime = refreshTokenMaxTime;
    }

    public boolean isAutoRefreshToken() {
        return autoRefreshToken;
    }

    public void setAutoRefreshToken(boolean autoRefreshToken) {
        this.autoRefreshToken = autoRefreshToken;
    }
}
