package top.lingkang.oauth.server.storage;

import top.lingkang.oauth.server.pojo.OauthToken;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface OauthStorageManager extends SessionManager {
    void addRefreshSession(String refreshToken, FinalSession finalSession);

    FinalSession getRefreshSession(String refreshToken);

    FinalSession removeRefreshSession(String refreshToken);

    String[] getRefreshRole(String refreshToken);

    String[] updateRefreshRole(String refreshToken, String[] role);

    String[] deleteRefreshRole(String refreshToken);

    String[] getRefreshPermission(String refreshToken);

    String[] updateRefreshPermission(String refreshToken, String[] permission);

    String[] deleteRefreshPermission(String refreshToken);
}
