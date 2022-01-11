package top.lingkang.oauth.server.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalOauthServerConfiguration.class})
@Documented
public @interface EnableFinalOauthServer {
}
