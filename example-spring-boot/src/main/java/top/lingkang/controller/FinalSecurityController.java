package top.lingkang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.helper.FinalHolder;
import top.lingkang.session.FinalSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/10 15:35
 * @description
 */
@RestController
public class FinalSecurityController {

    @GetMapping("login")
    public Object login() {
        FinalHolder.login("lk");
        FinalSession finalSession = FinalHolder.getSession();
        System.out.println(finalSession.getToken());
        finalSession.setAttribute("a", "login添加的参数a：" + finalSession.getToken());
        List<String> role = new ArrayList<>();
        role.add("user");
        return new ModelAndView("redirect:/");
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

    @GetMapping("logout")
    public Object logout() {
        return "id";
    }
}
