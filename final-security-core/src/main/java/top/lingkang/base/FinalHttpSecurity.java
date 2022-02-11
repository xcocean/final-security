package top.lingkang.base;

import top.lingkang.base.FinalAuth;

import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/2/11
 */
public class FinalHttpSecurity {

    // 排除路径
    private String[] excludePath;
    // 权限校验
    private HashMap<String, FinalAuth> checkAuths = new HashMap<>();
}
