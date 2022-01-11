package top.lingkang.annotation;

import org.springframework.context.annotation.Import;
import top.lingkang.annotation.impl.FinalCheckAnnotation;
import top.lingkang.annotation.impl.FinalCheckLoginAnnotation;

import java.lang.annotation.*;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalCheckLoginAnnotation.class, FinalCheckAnnotation.class})
@Documented
public @interface EnableFinalAnnotation {
}
