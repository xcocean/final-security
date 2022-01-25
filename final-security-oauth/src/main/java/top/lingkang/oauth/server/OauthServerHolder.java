package top.lingkang.oauth.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import top.lingkang.oauth.server.pojo.OauthToken;
import top.lingkang.session.FinalSession;
import top.lingkang.session.impl.DefaultFinalSession;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthServerHolder {

    @Autowired
    private OauthServerManager serverManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Object oauthLogin(String id, Object user, String[] role, String[] permission) throws Exception {
        Assert.notNull(id, "入参id不能为空！");
        // token
        FinalSession session = serverManager.getStorageManager().getSessionById(id);
        if (session != null) {
            session.setUser(user);
            session.updateLastAccessTime();
        } else {
            session = new DefaultFinalSession(id, user, serverManager.getTokenGenerate().generateToken());
        }
        session.setType("token");
        serverManager.getStorageManager().addFinalSession(session.getToken(), session);

        // refresh token
        FinalSession refreshSession = serverManager.getStorageManager().getRefreshSessionById(id);
        if (refreshSession == null) {
            refreshSession = new DefaultFinalSession(id, user, serverManager.getTokenGenerate().refreshTokenGenerate());
            refreshSession.setType("refresh");
            serverManager.getStorageManager().addRefreshSession(refreshSession.getToken(), refreshSession);
        }

        serverManager.getStorageManager().addPermission(session.getToken(), permission);
        serverManager.getStorageManager().addRoles(session.getToken(), role);
        serverManager.getStorageManager().updateRefreshPermission(refreshSession.getToken(), permission);
        serverManager.getStorageManager().updateRefreshRole(refreshSession.getToken(), role);

        OauthToken oauthToken = new OauthToken();
        oauthToken.setToken(session.getToken());
        oauthToken.setRefreshToken(refreshSession.getToken());
        oauthToken.setId(id);
        long current = System.currentTimeMillis();
        oauthToken.setExpire(
                serverManager.getOauthServerProperties().getTokenMaxTime() + session.getLastAccessTime() - current
        );
        oauthToken.setRefreshExpire(
                serverManager.getOauthServerProperties().getRefreshTokenMaxTime() + refreshSession.getLastAccessTime() - current
        );

        // to json
        return objectMapper.writeValueAsString(oauthToken);
    }


}
