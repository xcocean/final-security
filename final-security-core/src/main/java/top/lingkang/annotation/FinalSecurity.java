package top.lingkang.annotation;

import org.springframework.context.annotation.Import;
import top.lingkang.FinalManager;
import top.lingkang.config.FinalSecurityInitConfiguration;

import java.lang.annotation.*;

/**
 * 开启 FinalSecurity
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalSecurityInitConfiguration.class})
@Documented
public @interface FinalSecurity {
}
