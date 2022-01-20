package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.oauth.constants.OauthConstants;
import top.lingkang.oauth.error.OauthTokenException;
import top.lingkang.oauth.server.base.OauthCodeGenerate;
import top.lingkang.oauth.server.base.OauthExceptionHandler;
import top.lingkang.oauth.server.base.OauthRefreshTokenGenerate;
import top.lingkang.oauth.server.config.FinalOauthServerProperties;
import top.lingkang.oauth.server.storage.OauthStorageManager;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class OauthServerManager {
    @Autowired
    private OauthExceptionHandler oauthExceptionHandler;
    @Autowired
    private OauthRefreshTokenGenerate refreshTokenGenerate;
    @Autowired
    private OauthCodeGenerate oauthCodeGenerate;
    @Autowired
    private OauthStorageManager storageManager;
    @Autowired
    private FinalOauthServerProperties oauthServerProperties;

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

    public OauthRefreshTokenGenerate getRefreshTokenGenerate() {
        return refreshTokenGenerate;
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
