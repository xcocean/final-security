package top.lingkang.oauth.server.base.impl;


import top.lingkang.oauth.server.base.OauthTokenGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class DefaultOauthTokenGenerate implements OauthTokenGenerate {
    @Override
    public String refreshTokenGenerate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String tokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
