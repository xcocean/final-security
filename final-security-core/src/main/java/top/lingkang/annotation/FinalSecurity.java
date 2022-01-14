package top.lingkang.annotation;

import org.springframework.context.annotation.Import;
import top.lingkang.FinalManager;

import java.lang.annotation.*;

/**
 * 开启 FinalSecurity
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalManager.class})
@Documented
public @interface FinalSecurity {
}
