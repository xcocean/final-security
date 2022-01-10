package top.lingkang.base.impl;

import top.lingkang.base.FinalTokenGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class DefaultFinalTokenGenerate implements FinalTokenGenerate {
    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String generateRefreshToken() {
        return generateToken();
    }
}
