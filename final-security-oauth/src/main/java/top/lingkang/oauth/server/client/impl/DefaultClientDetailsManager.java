package top.lingkang.oauth.server.client.impl;

import top.lingkang.oauth.server.client.ClientDetails;
import top.lingkang.oauth.server.client.ClientDetailsManager;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class DefaultClientDetailsManager implements ClientDetailsManager {
    @Override
    public ClientDetails getClientDetails(String clientId) {
        return null;
    }

    @Override
    public void addClientDetails(ClientDetails... clientDetails) {

    }
}
