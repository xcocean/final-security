package top.lingkang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.annotation.FinalCheckLogin;
import top.lingkang.http.FinalSecurityHolder;
import top.lingkang.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author lingkang
 * date 2021/8/10 15:35
 */
@CrossOrigin
@RestController
public class FinalSecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    private FinalSecurityHolder securityHolder;

    @GetMapping("/login")
    public Object login() {
        securityHolder.login("asd", new String[]{});
        securityHolder.getUsername();// 获取会话用户名 string
        securityHolder.getRole(); // 获取会话中的角色 array
        securityHolder.isLogin(); // 判断当前会话是否登录 boolean
        return "ok";
    }

    @GetMapping("user")
    public Object user(){
        return "user";
    }


    @GetMapping("logout")
    public Object logout() {
        securityHolder.logout();
        return "ok";
    }

    @FinalCheck(anyRole = "admin", andRole = {"admin,system"})
    @GetMapping("/")
    public Object index() {
        return "index";
    }

    @GetMapping("/nickname")
    public Object getNickname() {
        return userService.getNickname();
    }

    @GetMapping("/username")
    public Object getUsername() {
        return userService.getUsername();
    }

    @GetMapping("index")
    public Object indexHtml(HttpServletRequest request) {
        return new ModelAndView("index");
    }

    @GetMapping("/test")
    public Object test() {
        return "test";
    }

    @GetMapping("vip")
    public Object vip() {
        return "vip1";
    }

}
