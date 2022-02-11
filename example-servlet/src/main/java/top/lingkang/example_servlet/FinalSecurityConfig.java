package top.lingkang.example_servlet;

import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.config.FinalSecurityConfiguration;

import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/2/11
 */
public class FinalSecurityConfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        HashMap<String, FinalAuth> checkAuths=new HashMap<>();
        checkAuths.put("/*",new FinalAuth().hasRoles("user"));

        properties.setCheckAuths(checkAuths);
        properties.setExcludePath(new String[]{"/hello-servlet"});
    }
}
