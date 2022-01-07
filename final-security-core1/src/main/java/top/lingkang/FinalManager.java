package top.lingkang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityConfig;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.http.impl.FinalRequestSpringMVC;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ComponentScan("top.lingkang")
@Configuration
@EnableConfigurationProperties(FinalSecurityProperties.class)
public class FinalManager {
    @Autowired
    private FinalSecurityProperties properties;
    private FinalSecurityConfig config;

    public void login(String id){
        String token=getToken();
        if (properties.getOnlyOne()){

        }
        if (properties.getShareToken()){

        }




    }

    /**
     * 获取token
     */
    public String getToken() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        FinalRequestSpringMVC requestSpringMVC = new FinalRequestSpringMVC(servletRequestAttributes.getRequest());

        // cookie中获取
        String token = requestSpringMVC.getCookieValue(properties.getTokenName());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // 请求域中获取
        token = requestSpringMVC.getParam(properties.getTokenNameRequest());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // 请求头中获取
        token = requestSpringMVC.getHeader(properties.getTokenNameHeader());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // ThreadLocal 中获取
        Object requestToken = servletRequestAttributes.getAttribute(properties.getTokenName(), RequestAttributes.SCOPE_REQUEST);
        if (requestToken != null) {
            return (String) requestToken;
        }
        throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }


    public FinalSecurityConfig getConfig() {
        if (config==null){
            return new FinalSecurityConfig();
        }
        return config;
    }

    public FinalManager setConfig(FinalSecurityConfig config) {
        this.config = config;
        return this;
    }
}
