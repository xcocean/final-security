package top.lingkang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.annotation.FinalCheckLogin;
import top.lingkang.holder.FinalHolder;
import top.lingkang.service.UserService;
import top.lingkang.session.FinalSession;

import java.util.ArrayList;

/**
 * @author lingkang
 * @date 2021/8/10 15:35
 * @description
 */
@FinalCheckLogin
@RestController
public class FinalSecurityController {
    @Autowired
    private FinalHolder finalHolder;
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public Object login() {
        finalHolder.login("lk", null, new String[]{"user"}, null);// 登陆
//        finalHolder.addRoles("user", "admin", "system");// 添加角色
//        finalHolder.addPermission("get", "update", "delete");// 添加权限
        FinalSession finalSession = finalHolder.getSession();
        System.out.println(finalSession.getToken());
        //return new ModelAndView("redirect:/");
        return "ok";
    }


    @GetMapping("logout")
    public Object logout() {
        finalHolder.logout();
        return "ok";
    }

    @FinalCheck(orRole = "admin",andRole = {"admin,system"},orPermission = "get")
    @GetMapping("/")
    public Object index() {
        return "index";
    }

    @GetMapping("/nickname")
    public Object getNickname(){
        return userService.getNickname();
    }

    @GetMapping("/username")
    public Object getUsername(){
        return userService.getUsername();
    }

    @GetMapping("index")
    public Object indexHtml() {
        return new ModelAndView("index");
    }

    @GetMapping("/test")
    public Object test() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("66");
        return "test";
    }
}
