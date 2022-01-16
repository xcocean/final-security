package top.lingkang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Component
@ConfigurationProperties(prefix = "final.security")
public class FinalProperties implements Serializable {
    // 令牌名称
    private String tokenName = "fs-token";
    private String tokenNameRequest = "access_token";
    private String tokenNameHeader = "Authorization";
    private String tokenNameHeaderPrefix="Basic ";
    private Boolean generateNewToken = false; // 重复登陆是否生成新的令牌 默认 false 即源令牌未过期则不会生成新令牌

    // 失效时间
    private Long maxValid = 1800000L;// 30分钟
    private Boolean prepareCheck = true;
    private Long prepareTime = 180000L;// 预留3分钟执行后续操操作
    private Boolean tokenAccessContinue = false;// 每次访问都更新令牌失效时间

    // 排除路径
    private String[] excludePath = {"/login", "/logout"};

    // 登录相关
    private Boolean useCookie = true;// 使用cookie存储 token
    private Boolean onlyOne = false;// 只允许登录唯一用户，第二个登录会kill掉第一个的token

    public String getTokenNameHeaderPrefix() {
        return tokenNameHeaderPrefix;
    }

    public void setTokenNameHeaderPrefix(String tokenNameHeaderPrefix) {
        this.tokenNameHeaderPrefix = tokenNameHeaderPrefix;
    }

    public Boolean getGenerateNewToken() {
        return generateNewToken;
    }

    public void setGenerateNewToken(Boolean generateNewToken) {
        this.generateNewToken = generateNewToken;
    }

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

    public Long getMaxValid() {
        return maxValid;
    }

    public void setMaxValid(Long maxValid) {
        this.maxValid = maxValid;
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

    public Boolean getTokenAccessContinue() {
        return tokenAccessContinue;
    }

    public void setTokenAccessContinue(Boolean tokenAccessContinue) {
        this.tokenAccessContinue = tokenAccessContinue;
    }
}
