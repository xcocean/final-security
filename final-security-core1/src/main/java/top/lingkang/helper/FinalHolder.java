package top.lingkang.helper;

import top.lingkang.FinalManager;
import top.lingkang.utils.SpringBeanUtils;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public abstract class FinalHolder {
    public static FinalManager manager= SpringBeanUtils.getBean(FinalManager.class);

    public static void login(String id){
        manager.login(id);
    }
}
