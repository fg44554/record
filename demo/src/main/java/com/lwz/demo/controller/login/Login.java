package com.lwz.demo.controller.login;

import com.lwz.demo.controller.user.util.MD5Util;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class Login {
    @Autowired
    UserMsg userMsg;
    @Autowired
    LoginService loginService;
    @RequestMapping("/login")
    public String login(HttpSession session,String username, String password, Integer admin_id){
        //管理员登陆
        if(username.equals("admin")&&password.equals("admin")&&admin_id==999){
            session.setAttribute("username","admin");
            session.setAttribute("password","admin");
            session.setAttribute("admin_id",999);
            System.out.println("欢迎最高管理员");
            return "redirect:/index";
        }
        //验证是否有此管理员
        if(!(loginService.selectAllAdminId().contains(admin_id))){
            System.out.println("未查询到管理员ID，请输入正确的管理员ID");
            return "ERROR";
        }
        UserMsg userMsg = loginService.selectUserMsgByUsernameAndAdminID(username, admin_id);
        //验证管理员下是否有此用户
        if(userMsg==null){
            System.out.println("此管理员下无此用户");
            return "ERROR";
        }
        //验证此用户密码是否正确
        if(userMsg.getPassword().equals(MD5Util.md5Hex(password+username+123))){
            //验证此用户是否为管理员
            if(userMsg.getAdmin().equals("Y")){
                System.out.println("欢迎管理员"+userMsg.getUsername());
                session.setAttribute("username",userMsg.getUsername());
                session.setAttribute("password",userMsg.getPassword());
                session.setAttribute("admin_id",userMsg.getAdmin_id());
                session.setAttribute("email",userMsg.getEmail());
                session.setAttribute("state",userMsg.getAdmin());

                return "redirect:/index1";
            }else if(userMsg.getAdmin().equals("N")){
                System.out.println("欢迎用户"+userMsg.getUsername());
                session.setAttribute("username",userMsg.getUsername());
                session.setAttribute("password",userMsg.getPassword());
                session.setAttribute("admin_id",userMsg.getAdmin_id());
                session.setAttribute("email",userMsg.getEmail());
                session.setAttribute("state",userMsg.getAdmin());

                return "redirect:/index1";
            }
            return "ERROR";
        }else{
        System.out.println("输入的密码不正确");
        return "ERROR";
        }

    }
}
