package top.lingkang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ConfigurationProperties(prefix = "final.security")
public class FinalSecurityProperties implements Serializable {
    // 令牌名称
    private String tokenName = "fs-token";
    private String tokenNameRequest = "access_token";
    private String tokenNameHeader = "Authorization";

    // 失效时间
    private Integer maxValid = 1800000;// 30分钟
    private Integer maxValidRefresh = 1296000000;// 15天
    private Boolean prepareCheck = true;
    private Long prepareTime = 180000L;// 预留3分钟执行后续操操作

    // 排除路径
    private String[] excludePath = {"/login", "/logout"};

    // 登录相关
    private Boolean useCookie = true;// 使用cookie存储 token
    private Boolean onlyOne = false;// 只允许登录唯一用户，第二个登录会kill掉第一个的token


    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenNameRequest() {
        return tokenNameRequest;
    }

    public void setTokenNameRequest(String tokenNameRequest) {
        this.tokenNameRequest = tokenNameRequest;
    }

    public String getTokenNameHeader() {
        return tokenNameHeader;
    }

    public void setTokenNameHeader(String tokenNameHeader) {
        this.tokenNameHeader = tokenNameHeader;
    }

    public Integer getMaxValid() {
        return maxValid;
    }

    public void setMaxValid(Integer maxValid) {
        this.maxValid = maxValid;
    }

    public Integer getMaxValidRefresh() {
        return maxValidRefresh;
    }

    public void setMaxValidRefresh(Integer maxValidRefresh) {
        this.maxValidRefresh = maxValidRefresh;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }

    public Boolean getUseCookie() {
        return useCookie;
    }

    public void setUseCookie(Boolean useCookie) {
        this.useCookie = useCookie;
    }

    public Boolean getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(Boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public Boolean getPrepareCheck() {
        return prepareCheck;
    }

    public void setPrepareCheck(Boolean prepareCheck) {
        this.prepareCheck = prepareCheck;
    }

    public Long getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(Long prepareTime) {
        this.prepareTime = prepareTime;
    }
}
