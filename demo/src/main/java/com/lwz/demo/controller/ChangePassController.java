package com.lwz.demo.controller;

import com.lwz.demo.controller.user.util.MD5Util;
import com.lwz.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ChangePassController {
    @Autowired
    MailSender mailSender;
    @Autowired
    LoginService loginService;
    @RequestMapping("/update")
    public void changePass(HttpSession session, @RequestParam("password")String password){
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        String email = session.getAttribute("email").toString().trim();
        String username = session.getAttribute("username").toString().trim();
        if(loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username,password,admin_id)!=null) {
            StringBuilder s=new StringBuilder();
            s.append(password);
            String s1 = s.toString();
            String s2 = MD5Util.md5Hex(s1 + username + 123);
            loginService.updatePassByNameAndAdminId(s2, username, admin_id);
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人
            message.setFrom("1520553040@qq.com");
            // 收件人
            message.setTo(email);
            // 邮件标题
            message.setSubject("密码更改通知");
            // 邮件内容
            message.setText("您的新密码是:" + password);
            // 抄送人
            message.setCc("1520553040@qq.com");
            mailSender.send(message);
        }
    }
}
