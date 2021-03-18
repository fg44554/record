package com.lwz.demo.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class Action {
    @RequestMapping("/backLogin")
    public String backLogin(){
        System.out.println("跳转到login页面");
        return "login";
    }
    @RequestMapping("/toAdminRegister")
    public String toAdminRegister(){
        System.out.println("跳转到管理员注册页面");
        return "adminRegister";
    }
    @RequestMapping("/toRegister")
    public String toRegister(){
        System.out.println("跳转到用户注册页面");
        return "register";
    }
    @RequestMapping("/toSend")
    public String toSend(){
        return "Email";
    }


    @RequestMapping("/toChange")
    public String toChange(){
        return "update";
    }

    @RequestMapping({"/toPersonal"})
    public String toPersonal()
    {
        return "personal";
    }
}
