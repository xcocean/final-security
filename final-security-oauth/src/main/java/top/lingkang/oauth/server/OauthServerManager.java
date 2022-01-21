package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import top.lingkang.FinalManager;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.error.OauthTokenException;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.base.OauthExceptionHandler;
import top.lingkang.oauth.server.base.OauthTokenGenerate;
import top.lingkang.oauth.server.config.FinalOauthServerProperties;
import top.lingkang.oauth.server.config.OauthServerConfig;
import top.lingkang.oauth.server.storage.OauthStorageManager;
import top.lingkang.utils.BeanUtils;

import java.util.Arrays;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class OauthServerManager implements ApplicationRunner {
    @Autowired(required = false)
    private OauthExceptionHandler oauthExceptionHandler;
    @Autowired
    private OauthTokenGenerate tokenGenerate;
    @Autowired
    private OauthCodeGenerate oauthCodeGenerate;
    @Autowired
    private OauthStorageManager storageManager;
    @Autowired
    private FinalOauthServerProperties oauthServerProperties;
    @Autowired
    private OauthServerConfig configProperties;
    @Autowired
    private FinalManager manager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BeanUtils.copyProperty(configProperties, oauthServerProperties, true);

        // 更新排除路径
        String[] excludePath = manager.getProperties().getExcludePath();
        String[] newEx = Arrays.copyOf(excludePath, excludePath.length + 1);
        newEx[newEx.length-1]="/oauth/**";
        manager.updateExcludePath(newEx);
    }

    public String getToken() {
        FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
        if (requestContext == null) {
            throw new OauthTokenException(OauthConstants.NOT_EXIST_TOKEN);
        }

        String token = requestContext.getRequest().getHeader(oauthServerProperties.getTokenHeader());
        if (token != null) {
            return token.substring(oauthServerProperties.getTokenHeaderPrefix().length());
        }

        token = requestContext.getRequest().getParameter(oauthServerProperties.getTokenRequest());
        if (token != null)
            return token;


        throw new OauthTokenException(OauthConstants.NOT_EXIST_TOKEN);
    }

    public OauthExceptionHandler getOauthExceptionHandler() {
        return oauthExceptionHandler;
    }

    public OauthTokenGenerate getTokenGenerate() {
        return tokenGenerate;
    }

    public OauthCodeGenerate getOauthCodeGenerate() {
        return oauthCodeGenerate;
    }

    public OauthStorageManager getStorageManager() {
        return storageManager;
    }

    public FinalOauthServerProperties getOauthServerProperties() {
        return oauthServerProperties;
    }
}
