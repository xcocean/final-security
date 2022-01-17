package top.lingkang.oauth.server.client;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface ClientDetailsManager {
    ClientDetails getClientDetails(String clientId);

    void addClientDetails(ClientDetails ... clientDetails);
}
