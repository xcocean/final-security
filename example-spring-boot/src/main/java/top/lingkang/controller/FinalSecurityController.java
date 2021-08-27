package top.lingkang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.config.FinalContextHolder;
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
        FinalContextHolder.login("lk");
        FinalSession finalSession = FinalContextHolder.getFinalSession();
        System.out.println(finalSession.getToken());
        System.out.println(finalSession.getAttribute("a"));
        finalSession.setAttribute("a", finalSession.getToken());
        System.out.println(finalSession.getToken());
        List<String> role = new ArrayList<>();
        role.add("user");
        FinalContextHolder.addRoles(role);
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
        FinalContextHolder.logout();
        String id = FinalContextHolder.getId();
        return id;
    }
}
