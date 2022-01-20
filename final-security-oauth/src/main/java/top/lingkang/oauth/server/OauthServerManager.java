package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
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
