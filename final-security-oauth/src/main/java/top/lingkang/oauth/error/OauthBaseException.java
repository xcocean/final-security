package top.lingkang.oauth.error;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthBaseException extends RuntimeException {
    public OauthBaseException(String message) {
        super(message);
    }
}
