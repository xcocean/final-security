package top.lingkang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lingkang
 * @date 2021/8/10 15:17
 * @description
 */
@ConfigurationProperties(prefix = "final.security")
public class FinalSecurityProperties {
    private String tokenName = "fs-token";

    private String[] excludePath = {"/login"};

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
}
