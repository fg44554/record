package com.lwz.demo.controller;

import com.lwz.demo.controller.user.util.TestUtil;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import com.lwz.demo.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class UploadController {

    @Autowired
    LoginService loginService;
    @RequestMapping("/index")
    public ModelAndView toIndex(HttpSession session,Model model){
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            MsgService msgService = new MsgService();
            List<UpMsg> upMsgs = msgService.selectAllUp();
            model.addAttribute("upmsg", upMsgs);
            ModelAndView modelAndView = new ModelAndView("succ", "upmsg1", model);
            return modelAndView;
        }else {
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }

    }
    @RequestMapping("/upload")
    public String picUpload(){
        return "upload";
    }
    @RequestMapping("/upload1")
    public String picUpload1(){
        return "upload1";
    }
    @RequestMapping("/uploading")
    public ModelAndView upload(HttpSession session,MultipartFile fileUpload, Model model){
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            //获取文件名
            String fileName = fileUpload.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        //重新生成文件名
//        fileName = UUID.randomUUID()+suffixName;
            //指定本地文件夹存储图片
            String filePath = "./upload/";
//        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy年MM月dd日 " );
//        String str = sdf.format(new Date());
            MsgService msgService = new MsgService();
            UpMsg upMsg = new UpMsg();
            upMsg.setName(fileName);
            upMsg.setSize(fileUpload.getSize() + "");
//            upMsg.setTime(str.trim());
            upMsg.setType(suffixName);
            upMsg.setStat("N");
            upMsg.setAdmin_id(userMsg.getAdmin_id());
            upMsg.setUser_id(userMsg.getId());
            System.out.println(upMsg);
//        RequestController requestController = new RequestController();
            try {
//            //将上传服务器模块和上传阿里云模块耦合
//            requestController.getFileLink(fileName,model);
                //将图片保存到static文件夹里
                fileUpload.transferTo(new File(filePath + fileName));
                int i = msgService.insertUpMsg(upMsg);
                //不可以在这里运行requestController类方法否则会耦合
                //事件flag变成false了
                List<UpMsg> upMsgs = msgService.selectAllUp();
                MoodRun moodRun = new MoodRun();
                UploadAliController uploadAliController = new UploadAliController();
                Thread thread = new Thread(uploadAliController);
                Thread thread1 = new Thread(moodRun);
                thread.start();
                thread1.start();
                for (UpMsg u:upMsgs) {
                    System.out.println("这是每一个文件的userid"+u.getUser_id());
                }
                System.out.println();
                model.addAttribute("upmsg", upMsgs);
                return new ModelAndView("succ", "upmsg1", model);
            } catch (Exception e) {
                e.printStackTrace();
                return new ModelAndView("ERROR");
            }
        }else {
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }
    }
    @RequestMapping("/put/{name}")
    public ModelAndView getFileLink(HttpSession session,@PathVariable(value = "name") String name, Model model){
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            System.out.println("hello我是controller方法");
            MsgService msgService=new MsgService();
            UpMsg upMsg = msgService.selectByName(name);
            return new TestUtil(upMsg).toCharFromDb(model);
        }else {
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }
    }
}

