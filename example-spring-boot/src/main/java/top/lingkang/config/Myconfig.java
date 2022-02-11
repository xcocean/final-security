package top.lingkang.config;

import org.springframework.context.annotation.Configuration;
import top.lingkang.annotation.EnableFinalSecurity;
import top.lingkang.annotation.EnableFinalSecurityAnnotation;
import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.base.FinalHttpSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@EnableFinalSecurity // 开启 FinalSecurity
@EnableFinalSecurityAnnotation // 开启注解
@Configuration
public class Myconfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        HashMap<String, FinalAuth> map = new HashMap<>();
        map.put("/user", new FinalAuth().hasRoles("user"));// 必须拥有user角色
        map.put("/about", new FinalAuth().hasPermission("get"));// 必须拥有get权限
        map.put("/updatePassword", new FinalAuth().hasRoles("user").hasPermission("update"));// 需要拥有user角色和update权限
        map.put("/index", new FinalAuth().hasRoles("admin", "system").hasPermission("get"));// 至少有一个角色并拥有get权限
        map.put("/vip/**", new FinalAuth().hasAllRoles("user","vip"));// 需要同时拥有角色
        properties.setCheckAuths(map);

        properties.setExcludePath(new String[]{"/login", "/logout"});
        /*// 自定义异常处理
        properties.setExceptionHandler(new FinalExceptionHandler() {
            @Override
            public void permissionException(Exception e, HttpServletRequest request, HttpServletResponse response) {
                // 异常处理
            }

            @Override
            public void notLoginException(Exception e, HttpServletRequest request, HttpServletResponse response) {
                // 异常处理
            }

            @Override
            public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
                // 异常处理
            }
        });*/
    }
}