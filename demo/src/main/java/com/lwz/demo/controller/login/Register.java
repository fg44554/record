package com.lwz.demo.controller.login;

import com.lwz.demo.controller.user.util.MD5Util;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
//普通用户注册
public class Register {
    @Autowired
    LoginService loginService;
    @Autowired
    JavaMailSender mailSender;
    @RequestMapping("/register")
    public void register(String username,String password,Integer admin_id,String email) throws SQLException {
        if(admin_id == null || username == null || password == null){
            System.out.println("请填写全部信息");
            return;
        }
        //同管理员ID内用户名不可重复
        if(loginService.selectAllAdminId().contains(admin_id)){
            System.out.println("查询到管理员ID，下面进行重复名检测");
            //改，通过管理员ID查是否用同名
            if(loginService.selectUsername(username,admin_id)!=1){
                //密码复杂性判断，后续可做迭代
                if(password.length()>6 && password.matches("^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+$")){
                    UserMsg userMsg = new UserMsg();
                    userMsg.setUsername(username);
                    String s = MD5Util.md5Hex(password + username + 123);
                    userMsg.setPassword(s);
                    userMsg.setAdmin("N");
                    userMsg.setRoot("N");
                    userMsg.setAdmin_id(admin_id);
                    userMsg.setEmail(email);
                    loginService.insertUserMsg(userMsg);
                    SimpleMailMessage message = new SimpleMailMessage();
                    // 发件人
                    message.setFrom("1520553040@qq.com");
                    // 收件人
                    message.setTo(email);
                    // 邮件标题
                    message.setSubject("用户注册信息");
                    // 邮件内容
                    message.setText("用户注册成功，用户名为:"+username+"密码为:"+password+"管理员ID为:"+admin_id);
                    // 抄送人
                    message.setCc("1520553040@qq.com");
                    mailSender.send(message);
                    System.out.println("邮件已发送---------------------------------------------------------------------------");
                }else {
                    System.out.println("请输入大于六位的密码，密码至少包含数字和英文");
                }
            }else{
                System.out.println("用户名重复，请更换用户名");
            }
        }else{
            System.out.println("管理员ID不正确，没有此管理员");
        }
    }
}
