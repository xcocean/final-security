package top.lingkang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.config.FinalContext;
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
        FinalContext.login("lk");
        FinalSession finalSession = FinalContext.getFinalSession();
        System.out.println(finalSession.getToken());
        System.out.println(finalSession.getAttribute("a"));
        finalSession.setAttribute("a", finalSession.getToken());
        System.out.println(finalSession.getToken());
        List<String> role = new ArrayList<>();
        role.add("user");
        FinalContext.addRoles(role);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/")
    public Object index() {
        return "123";
    }

    @GetMapping("/test")
    public Object test() {
        return "test";
    }

    @GetMapping("logout")
    public Object logout() {
        FinalContext.logout();
        String id = FinalContext.getId();
        return id;
    }
}
