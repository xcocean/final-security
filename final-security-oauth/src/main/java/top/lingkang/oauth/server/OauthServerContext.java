package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.FinalManager;
import top.lingkang.oauth.server.entity.OauthToken;
import top.lingkang.oauth.server.entity.RefreshToken;
import top.lingkang.session.FinalSession;
import top.lingkang.session.impl.DefaultFinalSession;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthServerContext {
    @Autowired
    private OauthServerManager serverManager;


    public OauthToken oauthLogin(String id, Object user, String[] role, String[] permission) {

        return null;
    }


}
