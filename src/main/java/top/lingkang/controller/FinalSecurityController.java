package top.lingkang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.lingkang.config.FinalContext;
import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2021/8/10 15:35
 * @description
 */
@RestController
public class FinalSecurityController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("login")
    public Object login() {
        FinalContext.login("lk");
        FinalSession finalSession = FinalContext.getFinalSession();
        System.out.println(finalSession.getToken());
        System.out.println(finalSession.getAttribute("a"));
        finalSession.setAttribute("a",finalSession.getToken());
        System.out.println(finalSession.getToken());
        return new ModelAndView("redirect:/");
    }

    @GetMapping("")
    public Object index() {
        FinalSession finalSession = FinalContext.getFinalSession();
        System.out.println(finalSession.getToken());
        System.out.println(finalSession.getAttribute("a"));
        System.out.println(FinalContext.getFinalSession().getToken());
        return "123";
    }

    @GetMapping("logout")
    public Object logout(){
        FinalContext.logout();
        String id = FinalContext.getId();
        return id;
    }
}
