package top.lingkang.oauth.server.client.impl;

import top.lingkang.oauth.server.client.ClientDetails;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class DefaultClientDetails implements ClientDetails {
    private String clientId;
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public ClientDetails setClient(String clientId) {
        return this;
    }

    @Override
    public ClientDetails secret(String... secret) {
        return this;
    }

    @Override
    public ClientDetails authorizedGrantTypes(String... type) {
        return this;
    }

    @Override
    public ClientDetails scopes(String... scopes) {
        return this;
    }

    @Override
    public ClientDetails redirectUris(String url) {
        return this;
    }
}
