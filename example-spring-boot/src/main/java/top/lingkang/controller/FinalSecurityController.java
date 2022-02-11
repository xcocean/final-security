package top.lingkang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.http.FinalSecurityHolder;
import top.lingkang.service.UserService;

/**
 * @author lingkang
 * date 2021/8/10 15:35
 */
@RestController
public class FinalSecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    private FinalSecurityHolder securityHolder;

    @GetMapping("login")
    public Object login() {
        securityHolder.login("asd", new String[]{"user"}, null);
        return "ok";
    }


    @GetMapping("logout")
    public Object logout() {
        securityHolder.logout();
        return "ok";
    }

    @FinalCheck(orRole = "admin", andRole = {"admin,system"}, orPermission = "get")
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
    public Object indexHtml() {
        return new ModelAndView("index");
    }

    @GetMapping("/test")
    public Object test() {
        return "test";
    }

}
