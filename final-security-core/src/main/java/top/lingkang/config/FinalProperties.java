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
    private String rememberName = "rn";// 记住我的名称
    private String rememberTokenPrefix="remember-";// 记住我的令牌前缀

    // 失效时间
    private Long maxValid = 1800000L;// 30分钟
    private Long maxValidRemember = 2592000000L;// 30天
    private Boolean prepareCheck = true;
    private Long prepareTime = 180000L;// 预留3分钟执行后续操操作
    private Boolean tokenAccessContinue = false;// 每次访问都更新令牌失效时间

    // 排除路径
    private String[] excludePath = {"/login", "/logout"};

    // 登录相关
    private Boolean useCookie = true;// 使用cookie存储 token
    private Boolean onlyOne = false;// 只允许登录唯一用户，第二个登录会kill掉第一个的token

    // 会话相关
    private Boolean useViewSession=false;// 是否使用视图会话，例如在 jsp中，通过 ${sessionScope.finalSecurity.role }获得角色数组

    public Boolean getUseViewSession() {
        return useViewSession;
    }

    public void setUseViewSession(Boolean useViewSession) {
        this.useViewSession = useViewSession;
    }

    public String getRememberTokenPrefix() {
        return rememberTokenPrefix;
    }

    public void setRememberTokenPrefix(String rememberTokenPrefix) {
        this.rememberTokenPrefix = rememberTokenPrefix;
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

    public Long getMaxValidRemember() {
        return maxValidRemember;
    }

    public void setMaxValidRemember(Long maxValidRemember) {
        this.maxValidRemember = maxValidRemember;
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

    public String getRememberName() {
        return rememberName;
    }

    public void setRememberName(String rememberName) {
        this.rememberName = rememberName;
    }
}
