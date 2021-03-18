package com.lwz.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

@Controller
public class ValidateController {
    @RequestMapping("/validate")
    public String validate(HttpSession session){
        session.invalidate();
        return "login";
    }
}
