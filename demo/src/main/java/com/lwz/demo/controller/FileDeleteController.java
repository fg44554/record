package com.lwz.demo.controller;


import com.lwz.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;

@Controller
public class FileDeleteController {
    /**
     * 删除服务上的文件
     * @author LWZ
     * @param fileName 路径
     * @return
     */
    @Autowired
    LoginService loginService;
    @RequestMapping("/delete/{name}")
    public String deleteServerFile(@PathVariable("name") String fileName){
        File file = new File("/home/wenzhe/upload/"+fileName);
        if (file.exists() && file.isFile() && file.delete()){
            loginService.deleteAllFileByName(fileName);
            System.out.println("该文件已删除:"+fileName);
        }
        return "redirect:/index";
    }
    @RequestMapping("/delete1")
    public String deleteServerFile1(HttpSession session,
                                    @RequestParam("name") String fileName,
                                    @RequestParam("user_id")Integer userId,
                                    @RequestParam("admin_id")Integer adminId){
        String state = session.getAttribute("state").toString();
        System.out.println("userId"+userId);
        System.out.println("adminId"+adminId);
        if(userId != null || adminId != null){
//            Integer userId = Integer.parseInt(userId1.trim());
//            Integer adminId = Integer.parseInt(adminId1.trim());
            if(state.equals("N")){
                loginService.deleteFileByNameAndUserID(fileName,userId);
                System.out.println("该文件已删除:"+fileName);
            }else if(state.equals("Y")){
                loginService.deleteFileByNameAndAdminId(fileName,adminId);
                System.out.println("该文件已删除:"+fileName);
            }
        }


        return "redirect:/index1";
    }
}
