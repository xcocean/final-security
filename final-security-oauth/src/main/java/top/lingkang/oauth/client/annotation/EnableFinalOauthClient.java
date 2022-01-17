package top.lingkang.oauth.client.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FinalOauthClientConfiguration.class})
@Documented
public @interface EnableFinalOauthClient {
}
