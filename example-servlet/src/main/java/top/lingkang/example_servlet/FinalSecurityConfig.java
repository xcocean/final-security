package top.lingkang.example_servlet;

import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.config.FinalSecurityConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/2/11
 */
public class FinalSecurityConfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        properties.checkAuthorize()
                .pathMatchers("/user").hasAnyRole("user", "vip1") // 有其中任意角色就能访问
                .pathMatchers("/vip/**").hasAllRole("user", "vip1");// 必须有所有角色才能访问


        properties.setExcludePath("/login", "/logout", "/hello-servlet");

        properties.setExceptionHandler(new DefaultFinalExceptionHandler() {
            @Override
            public void notLoginException(Exception e, HttpServletRequest request, HttpServletResponse response) {
                System.out.println(request.getServletPath());
                super.notLoginException(e, request, response);
            }
        });
    }
}
