package top.lingkang.oauth.server.storage;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface OauthStorageManager {

    boolean addCode(String code);

    boolean removeCode(String code);

}
