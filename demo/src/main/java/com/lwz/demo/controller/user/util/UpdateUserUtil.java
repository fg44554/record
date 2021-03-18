package com.lwz.demo.controller.user.util;

import com.lwz.demo.mapper.LoginMapperImp;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UpdateUserUtil {
    @Autowired
    LoginMapperImp loginMapperImp;

    @RequestMapping("/updateUser")
    public void updateUser(){
        List<UserMsg> userMsgs = loginMapperImp.selectAll();
        for (UserMsg u:userMsgs) {
            String password = u.getPassword();
            String username = u.getUsername();
            String s = MD5Util.md5Hex(password + username + 123);
            int id = u.getId();
            loginMapperImp.updateUserPassToMD5(s,id);
        }

    }
}
