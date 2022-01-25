package top.lingkang.oauth.server.base;

import top.lingkang.base.FinalTokenGenerate;

/**
 * @author lingkang
 * Created by 2022/1/20
 */
public interface OauthTokenGenerate extends FinalTokenGenerate {
    String refreshTokenGenerate();
}
