package top.lingkang.config;

import org.springframework.context.annotation.Configuration;
import top.lingkang.annotation.EnableFinalSecurity;
import top.lingkang.annotation.EnableFinalSecurityAnnotation;
import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author lingkang
 * date 2022/1/8
 */
@EnableFinalSecurity // 开启 FinalSecurity
@EnableFinalSecurityAnnotation // 开启注解
@Configuration
public class Myconfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        properties.checkAuthorize()
                .pathMatchers("/user").hasAnyRole("user", "vip1") // 有其中任意角色就能访问
                .pathMatchers("/vip/**").hasAllRole("user", "vip1");// 必须有所有角色才能访问


        properties.setExcludePath("/login", "/logout", "/user/login/app");

        properties.setExceptionHandler(new DefaultFinalExceptionHandler() {
            @Override
            public void notLoginException(Exception e, HttpServletRequest request, HttpServletResponse response) {
                System.out.println(request.getServletPath());
                super.notLoginException(e, request, response);
            }
        });
    }
}
