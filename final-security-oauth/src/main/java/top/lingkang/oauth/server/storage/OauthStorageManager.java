package top.lingkang.oauth.server.storage;

import top.lingkang.oauth.server.pojo.OauthToken;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface OauthStorageManager {

    boolean addCode(String code);

    boolean removeCode(String code);

    void addToken(String token, OauthToken oauthToken);

    void addRefreshToken(String refreshToken, OauthToken token);

    // 根据token获取角色
    String[] getRoles(String token);

    String[] getPermission(String token);

    // 根据token添加角色
    void addRoles(String token, String... role);

    String[] deleteRoles(String token);

    void addPermission(String token, String... permission);

    String[] deletePermission(String token);

    String getTokenById(String id);

    OauthToken getToken(String token);

    OauthToken getRefreshToken(String refreshToken);

    OauthToken removeToken(String token);

    OauthToken removeRefreshToken(String refreshToken);

    void cleanExpires();
}
