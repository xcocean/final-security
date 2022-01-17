package top.lingkang.oauth.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.lingkang.FinalManager;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class OauthServerManager implements ApplicationContextAware {
    @Autowired
    private FinalManager manager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }




}
