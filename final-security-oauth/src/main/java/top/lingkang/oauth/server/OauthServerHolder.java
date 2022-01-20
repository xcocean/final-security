package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.oauth.server.entity.OauthToken;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthServerHolder {
    @Autowired
    private OauthServerManager serverManager;


    public OauthToken oauthLogin(String id, Object user, String[] role, String[] permission) {

        return null;
    }


}
