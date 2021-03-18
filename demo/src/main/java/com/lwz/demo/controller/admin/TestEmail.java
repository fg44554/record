package com.lwz.demo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestEmail {
    @RequestMapping("/email1")
    @ResponseBody
    public void test(){
        Email email = new Email();
        email.send("1520553040@qq.com","测试","这是一条测试邮件","123");
    }
}
