package top.lingkang.annotation;

import org.springframework.context.annotation.Import;
import top.lingkang.http.FinalSecurityHolder;

import java.lang.annotation.*;

/**
 * @author lingkang
 * Created by 2022/2/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalSecurityHolder.class})
@Documented
public @interface EnableFinalSecurity {
}
