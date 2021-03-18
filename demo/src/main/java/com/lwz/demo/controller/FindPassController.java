package com.lwz.demo.controller;

import com.lwz.demo.controller.user.util.MD5Util;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class FindPassController {
    @Autowired
    LoginService loginService;
    @Autowired
    private JavaMailSender mailSender;
    @RequestMapping("/find")
    public void findPass(HttpSession session, @RequestParam("username")String username,@RequestParam("adminId")Integer adminId){
        UserMsg userMsg = loginService.selectUserMsgByUsernameAndAdminID(username, adminId);

        if(userMsg!=null){
            System.out.println("用户信息获取成功"+userMsg);
            String str1 = "abcdefghijklmnopqrstuvwxyz";
            String str2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String str3 = "1234567890";
            StringBuilder s = new StringBuilder();
            for (int o = 0; o <3 ; o++) {
                int i = (int) (Math.random() * 26);
                int k = (int) (Math.random() * 26);
                int j = (int) (Math.random() * 10);
                String i1 = String.valueOf(str1.charAt(i)) + String.valueOf(str2.charAt(k)) + String.valueOf(str3.charAt(j));
                s.append(i1);
            }
            String s1 = s.toString();
            String s2 = MD5Util.md5Hex(s1 + username + 123);
            loginService.updatePassByNameAndAdminId(s2,username,adminId);
            System.out.println("密码已更改为:"+(loginService.selectUserMsgByUsernameAndAdminID(username, adminId)).getPassword());
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人
            message.setFrom("1520553040@qq.com");
            // 收件人
            message.setTo(userMsg.getEmail());
            // 邮件标题
            message.setSubject("密码找回通知");
            // 邮件内容
            message.setText("您的新密码是:"+s);
            // 抄送人
            message.setCc("1520553040@qq.com");
            mailSender.send(message);

        }else{System.out.println("用户信息获取失败"+userMsg);}
    }
}
