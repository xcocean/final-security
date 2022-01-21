package top.lingkang.oauth.server.base;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public interface OauthTokenGenerate {
    String refreshTokenGenerate();
    String tokenGenerate();
}
