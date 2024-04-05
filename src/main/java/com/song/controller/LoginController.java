package com.song.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    //采用tymeleaf模板引擎
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/main")
    public ModelAndView main() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //返回当前登录用户 tymeleaf模板引擎会自动渲染
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", principal.getUsername());
        modelAndView.setViewName("main");
        return modelAndView;
    }
    @GetMapping("/loginFail")
    public ModelAndView loginFail(ModelAndView modelAndView) {
        modelAndView.addObject("msg", "登录失败");
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
