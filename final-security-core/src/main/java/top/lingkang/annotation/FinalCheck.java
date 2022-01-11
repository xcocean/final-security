package top.lingkang.annotation;

import java.lang.annotation.*;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FinalCheck {
    // 检查角色
    String[] orRole() default {};
    String[] andRole() default {};
    String[] orPermission() default {};
    String[] andPermission() default {};
}
