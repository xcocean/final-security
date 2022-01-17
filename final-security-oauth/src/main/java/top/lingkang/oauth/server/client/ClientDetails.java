package top.lingkang.oauth.server.client;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface ClientDetails {
    ClientDetails withClient(String clientId);

    ClientDetails secret(String... secret);

    ClientDetails authorizedGrantTypes(String... type);

    ClientDetails scopes(String... scopes);

    ClientDetails redirectUris(String url);
}
