package top.lingkang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lingkang.helper.FinalHolder;
import top.lingkang.session.FinalSession;

import java.util.ArrayList;

/**
 * @author lingkang
 * @date 2021/8/10 15:35
 * @description
 */
@RestController
public class FinalSecurityController {

    @GetMapping("login")
    public Object login() {
        FinalHolder.login("lk", true);
        FinalHolder.addRoles("user", "admin", "system");
        FinalHolder.addPermission("get", "update", "delete");
        FinalSession finalSession = FinalHolder.getSession();
        System.out.println(finalSession.getToken());
        finalSession.setAttribute("a", "login添加的参数a：" + finalSession.getToken());
        //return new ModelAndView("redirect:/");
        return "ok";
    }

    @GetMapping("logout")
    public Object logout() {
        FinalHolder.logout();
        return "ok";
    }

    @GetMapping("/")
    public Object index() {
        FinalSession session = FinalHolder.getSession();
        System.out.println(session.getData());
        return "123";
    }

    @GetMapping("/test")
    public Object test() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("66");
        return "test";
    }
}
