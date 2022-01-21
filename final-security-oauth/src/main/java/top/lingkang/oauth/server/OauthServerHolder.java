package top.lingkang.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.oauth.server.pojo.OauthToken;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthServerHolder {
    @Autowired
    private OauthServerManager serverManager;

    public OauthToken oauthLogin(String id, Object user, String[] role, String[] permission) {
        String tokenById = serverManager.getStorageManager().getTokenById(id);
        OauthToken oauthToken = null;
        if (tokenById != null) {
            oauthToken = serverManager.getStorageManager().getToken(tokenById);
        }
        if (oauthToken != null) {
            oauthToken = copyAttributes(oauthToken);
            OauthToken refreshToken = serverManager.getStorageManager().getRefreshToken(oauthToken.getRefreshToken());
            if (refreshToken == null) {
                oauthToken.setRefreshToken(serverManager.getTokenGenerate().refreshTokenGenerate());
            }
        } else {
            oauthToken = new OauthToken();
            oauthToken.setToken(serverManager.getTokenGenerate().tokenGenerate());
            oauthToken.setRefreshToken(serverManager.getTokenGenerate().refreshTokenGenerate());
        }
        oauthToken.setId(id);
        oauthToken.setUser(user);
        oauthToken.setCreateTime(System.currentTimeMillis());


        // 添加token信息
        serverManager.getStorageManager().addToken(oauthToken.getToken(), oauthToken);
        serverManager.getStorageManager().addToken(oauthToken.getRefreshToken(), oauthToken);
        serverManager.getStorageManager().addRoles(oauthToken.getToken(), role);
        serverManager.getStorageManager().addPermission(oauthToken.getToken(), permission);

        return oauthToken;
    }

    private OauthToken copyAttributes(OauthToken token) {
        OauthToken oauthToken = new OauthToken();
        oauthToken.setToken(token.getToken());
        oauthToken.setRefreshToken(token.getRefreshToken());
        oauthToken.setClientId(token.getClientId());
        oauthToken.setClientSecret(token.getClientSecret());
        oauthToken.setId(token.getId());
        oauthToken.setUser(token.getUser());
        oauthToken.setCreateTime(token.getCreateTime());
        return oauthToken;
    }


}
