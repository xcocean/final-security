package top.lingkang.session.impl;

import top.lingkang.session.FinalTokenGenerate;

import java.util.UUID;

/**
 * @author lingkang
 * @date 2021/8/13 17:24
 * @description
 */
public class DefaultFinalTokenGenerate implements FinalTokenGenerate {

    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
