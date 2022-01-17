package top.lingkang.oauth.server.client.impl;

import top.lingkang.oauth.server.client.ClientDetails;
import top.lingkang.oauth.server.client.ClientDetailsManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class DefaultClientDetailsManager implements ClientDetailsManager {
    private static final ConcurrentMap<String, ClientDetails> map = new ConcurrentHashMap<>();

    @Override
    public ClientDetails getClientDetails(String clientId) {
        return map.get(clientId);
    }

    @Override
    public void addClientDetails(ClientDetails... clientDetails) {
        for (ClientDetails details : clientDetails) {
            map.put(details.getClientId(), details);
        }
    }
}
