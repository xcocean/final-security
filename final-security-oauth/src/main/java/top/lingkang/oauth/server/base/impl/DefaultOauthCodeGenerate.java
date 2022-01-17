package top.lingkang.oauth.server.base.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import top.lingkang.oauth.server.base.OauthCodeGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
@ConditionalOnMissingBean(OauthCodeGenerate.class)
public class DefaultOauthCodeGenerate implements OauthCodeGenerate {
    @Override
    public String codeGenerate() {
        return UUID.randomUUID().toString();
    }
}
