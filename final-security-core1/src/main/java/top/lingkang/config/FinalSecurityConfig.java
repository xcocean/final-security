package top.lingkang.config;

import lombok.Data;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.utils.SpringBeanUtils;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Data
public class FinalSecurityConfig {
    private FinalExceptionHandler exceptionHandler;
    private FinalTokenGenerate tokenGenerate;

    public FinalSecurityConfig() {
        if (!SpringBeanUtils.isExistsBean(FinalExceptionHandler.class)) {
            setExceptionHandler(new DefaultFinalExceptionHandler());
        }
        if (!SpringBeanUtils.isExistsBean(FinalTokenGenerate.class)) {
            setTokenGenerate(new DefaultFinalTokenGenerate());
        }
    }
}
