package it.korea.app_boot.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    @GetMapping("/login")
    public ModelAndView  loginView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/user/loginForm");

        return view;
    }

    @GetMapping("/login/error")
    public ModelAndView  loginErrorView(@RequestParam("msg")String msg) {
      
      ModelAndView view = new ModelAndView();
      view.addObject("msg", msg);
      view.setViewName("views/user/loginError");

        return view;
    }



}
