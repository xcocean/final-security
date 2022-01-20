package top.lingkang.oauth.server.base.impl;

import top.lingkang.oauth.server.base.OauthRefreshTokenGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public class FinalOauthRefreshTokenGenerate implements OauthRefreshTokenGenerate {
    @Override
    public String refreshTokenGenerate() {
        return UUID.randomUUID().toString();
    }
}
