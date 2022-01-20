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
    private String tokenHeader="Authorization";
    private String tokenHeaderPrefix="Basic ";
    private String tokenRequest="access_token";

    private long tokenMaxTime = 1800000L;// 30分钟
    private long refreshTokenMaxTime=1296000000L;// 15 天
    private boolean autoRefreshToken=false;

    private long codeMaxValid=300000L;// 5 分钟

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getTokenHeaderPrefix() {
        return tokenHeaderPrefix;
    }

    public void setTokenHeaderPrefix(String tokenHeaderPrefix) {
        this.tokenHeaderPrefix = tokenHeaderPrefix;
    }

    public String getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(String tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    public long getCodeMaxValid() {
        return codeMaxValid;
    }

    public void setCodeMaxValid(long codeMaxValid) {
        this.codeMaxValid = codeMaxValid;
    }

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
