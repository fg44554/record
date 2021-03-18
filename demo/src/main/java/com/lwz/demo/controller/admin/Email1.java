package com.lwz.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Email1 {
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/send1")
    private void send(@RequestParam("send")String send,
                      @RequestParam("received")String received,
                      @RequestParam("title")String title,
                      @RequestParam("content")String content){
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人
        message.setFrom(send);
        // 收件人
        message.setTo(received);
        // 邮件标题
        message.setSubject(title);
        // 邮件内容
        message.setText(content);

        // 抄送人
        message.setCc("1520553040@qq.com");
        mailSender.send(message);
    }
}
