package top.lingkang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author lingkang
 * @date 2021/8/10 15:17
 * @description
 */
@Primary
@Component
@ConfigurationProperties(prefix = "final.security")
public class FinalSecurityProperties {
    private String tokenName = "fs-token";

    private String[] excludePath = {};

    // 默认session最大存活时间 默认 30分钟
    private long sessionMaxValid = 1800000L;

    // 每次访问是否更新会话时间，默认 true 即每次访问都会更新token的失效时间为最大
    private boolean accessUpdateSessionTime = true;

    public boolean isAccessUpdateSessionTime() {
        return accessUpdateSessionTime;
    }

    public void setAccessUpdateSessionTime(boolean accessUpdateSessionTime) {
        this.accessUpdateSessionTime = accessUpdateSessionTime;
    }

    public long getSessionMaxValid() {
        return sessionMaxValid;
    }

    public void setSessionMaxValid(long sessionMaxValid) {
        this.sessionMaxValid = sessionMaxValid;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }

    @Override
    public String toString() {
        return "FinalSecurityProperties{" +
                "tokenName='" + tokenName + '\'' +
                ", excludePath=" + Arrays.toString(excludePath) +
                ", sessionMaxValid=" + sessionMaxValid +
                ", accessUpdateSessionTime=" + accessUpdateSessionTime +
                '}';
    }
}
