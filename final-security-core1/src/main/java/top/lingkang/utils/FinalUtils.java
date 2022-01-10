package top.lingkang.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public class FinalUtils {
    public static void existsDuplicateBean(Class<?> clazz) {
        Log log = LogFactory.getLog(clazz);
        StringBuilder builder = new StringBuilder("Duplicate configuration exists, 存在重复配置\n");
        builder.append(" \n");
        builder.append("Cannot configure Bean, 不能在配置bean：" + clazz.getSimpleName());
        builder.append("after, 后\n");
        builder.append("Again configuration FinalSecurityConfiguration, 又在 FinalSecurityConfiguration 中配置 ");
        builder.append(clazz.getName());
        builder.append(" \n");
        builder.append(" \n");
        builder.append("Solution, 解决方案：");
        builder.append(" \n");
        builder.append("Delete one of the configurations and keep only one configuration, ");
        builder.append("删除其中一处配置，只保留一处配置");
        builder.append(" \n");
        builder.append(" \n");
        log.error(builder.toString());
        System.exit(0);
    }
}
