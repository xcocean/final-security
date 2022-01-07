package top.lingkang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Primary
@Component
@ConfigurationProperties(prefix = "final.security")
@Data
public class FinalSecurityProperties {
    // 令牌名称
    private String tokenName = "fs-token";
    private String tokenNameRequest = "access_token";
    private String tokenNameHeader = "Authorization";

    // 失效时间
    private Integer maxValid = 1800000;// 30分钟
    private Integer maxValidRefresh = 1296000000;// 15天

    // 排除路径
    private String[] excludePath = {};

    // 登录相关
    private Boolean useCookie=true;// 使用cookie存储 token
    private Boolean onlyOne=false;// 只允许登录唯一用户，第二个登录会kill掉第一个的token
    private Boolean shareToken=false;// 多个同时登录时，生成token值是否一致
}
