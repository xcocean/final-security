package top.lingkang.config;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalConfigProperties {
    // 令牌名称
    private String tokenName;
    private String tokenNameRequest;
    private String tokenNameHeader;
    private String tokenNameHeaderPrefix;// 默认 "Basic "
    private Boolean generateNewToken; // 重复登陆是否生成新的令牌 默认 false 即源令牌未过期则不会生成新令牌

    // 失效时间
    private Long maxValid;// 30分钟
    private Boolean prepareCheck;
    private Long prepareTime;// 预留3分钟执行后续操操作
    private Boolean tokenAccessContinue;// 每次访问都更新令牌失效时间,,即访问续时，默认false

    // 排除路径
    private String[] excludePath;

    // 登录相关
    private Boolean useCookie;// 使用cookie存储 token
    private Boolean onlyOne;// 只允许登录唯一用户，第二个登录会kill掉第一个的token

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
