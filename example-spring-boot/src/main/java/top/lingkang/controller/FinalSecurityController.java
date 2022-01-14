package top.lingkang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.annotation.FinalCheckLogin;
import top.lingkang.holder.FinalHolder;
import top.lingkang.session.FinalSession;

import java.util.ArrayList;

/**
 * @author lingkang
 * @date 2021/8/10 15:35
 * @description
 */
@RestController
public class FinalSecurityController {
    @Autowired
    private FinalHolder finalHolder;

    @GetMapping("login")
    public Object login() {
        finalHolder.login("lk");// 登陆
        finalHolder.addRoles("user", "admin", "system");// 添加角色
        finalHolder.addPermission("get", "update", "delete");// 添加权限
        FinalSession finalSession = finalHolder.getSession();
        System.out.println(finalSession.getToken());
        finalSession.setAttribute("a", "login添加的参数a：" + finalSession.getToken());
        //return new ModelAndView("redirect:/");
        return "ok";
    }


    @GetMapping("logout")
    public Object logout() {
        finalHolder.logout();
        return "ok";
    }

    @FinalCheckLogin
    @GetMapping("/")
    public Object index() {
        FinalSession session = finalHolder.getSession();
        System.out.println(session.getData());
        return "123";
    }

    @GetMapping("index")
    public Object indexHtml(){
        return new ModelAndView("index");
    }

    @GetMapping("/test")
    public Object test() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("66");
        return "test";
    }
}
