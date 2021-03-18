package com.lwz.demo.controller.admin;

import com.lwz.demo.controller.MoodRun;
import com.lwz.demo.controller.UploadAliController;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import com.lwz.demo.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Controller
public class AdminUploadController {
    @Autowired
    LoginService loginService;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    MsgService msgService;
    @RequestMapping("/index1")
    public ModelAndView toIndex(HttpSession session,Model model){
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null){
            System.out.println(userMsg);

            MsgService msgService = new MsgService();
            List<UpMsg> upMsgs= new ArrayList<>();
            System.out.println(userMsg.getAdmin()+"这里可以查看用户权限");
            if(userMsg.getAdmin().equals("Y")){
                upMsgs = msgService.selectAllAdminUpByAdminID(userMsg.getAdmin_id());
            }else if(userMsg.getAdmin().equals("N")){
                System.out.println("获取当前用户的id"+userMsg.getId());
                upMsgs= msgService.selectAllAdminUpByID(userMsg.getId());
            }
            model.addAttribute("upmsg3",upMsgs);
            ModelAndView modelAndView = new ModelAndView("succ1","upmsg2",model);
            MoodRun moodRun = new MoodRun();
            UploadAliController uploadAliController = new UploadAliController();
            Thread thread = new Thread(uploadAliController);
            Thread thread1 = new Thread(moodRun);
            thread1.start();
            thread.start();
            return modelAndView;
        }else{
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }
    }
    @RequestMapping("/admin")
    public ModelAndView upload(HttpSession session,MultipartFile fileUpload, Model model){
        System.out.println("你好，我是admin上传方法");
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            System.out.println("user信息验证成功");
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
            System.out.println(userMsg.getId());
            if (!(msgService.selectFileNameByUserID(userMsg.getId()).contains(fileName))) {
                System.out.println("库中不包含同名文件");
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
                    SimpleMailMessage message = new SimpleMailMessage();
                    // 发件人
                    message.setFrom("1520553040@qq.com");
                    String email2=loginService.selectEmailByAdminAndAdminId(admin_id);
                    System.out.println("发送给"+email2);
                    // 收件人
                    message.setTo(email2);
                    // 邮件标题
                    message.setSubject("用户上传文件信息");
                    // 邮件内容
                    message.setText("上传用户:"
                            +username+"文件名"
                            +upMsg.getName()+"文件上传时间"
                            +upMsg.getTime()+"文件类型"
                            +upMsg.getType()+"文件大小"
                            +upMsg.getSize());
                    // 抄送人
                    message.setCc("1520553040@qq.com");
                    mailSender.send(message);
                    System.out.println("邮件已发送---------------------------------------------------------------------------");
                    int i = msgService.insertUpMsg(upMsg);
                    //不可以在这里运行requestController类方法否则会耦合
                    //事件flag变成false了
                    List<UpMsg> upMsgs = new ArrayList<>();
                    if (userMsg.getAdmin().equals("Y")) {
                        upMsgs = msgService.selectAllAdminUpByAdminID(userMsg.getAdmin_id());
                    } else if (userMsg.getAdmin().equals("N")) {
                        upMsgs = msgService.selectAllAdminUpByID(userMsg.getId());
                        System.out.println();
                    }
                    model.addAttribute("upmsg3", upMsgs);
                    return new ModelAndView("succ1", "upmsg2", model);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ModelAndView("ERROR");
                }
            } else {
                System.out.println("库中包含同名文件");
                return new ModelAndView("error1");
            }
        }else{
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }
    }
    //不要忘了换url
    @RequestMapping("/getText")
    public ModelAndView getFileLink(HttpSession session, @RequestParam(value = "name") String name, @RequestParam(value = "user_id") Integer user_id, Model model){
        System.out.println(name+user_id+"进入getFileLink方法");
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            System.out.println("hello我是controller方法");
            System.out.println(userMsg);
            MsgService msgService = new MsgService();
            UpMsg upMsg = new UpMsg();
            if (((loginService.selectUserMsgByUserID(user_id)).getAdmin()).equals("Y")) {
                //这句有问题
                upMsg = msgService.selectByNameAndAdminId(name, (loginService.selectUserMsgByUserID(user_id)).getAdmin_id());
                System.out.println(upMsg);
                List<Text> texts = msgService.selectAllTextByID(upMsg.getId());
                if (!(texts.size() == 0)) {
                    model.addAttribute("texts", texts);
                    return new ModelAndView("responseText", "text", model);
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return getFileLink(session,name, user_id, model);
                }
            } else if (((loginService.selectUserMsgByUserID(user_id)).getAdmin()).equals("N")) {
                upMsg = msgService.selectByNameAndUserId(name, user_id);
                System.out.println(upMsg);
                List<Text> texts = msgService.selectAllTextByID(upMsg.getId());
                if (!(texts.size() == 0)) {
                    model.addAttribute("texts", texts);
                    return new ModelAndView("responseText", "text", model);
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return getFileLink(session,name, user_id, model);
        }else{
            System.out.println("您存在非法操作导致账号密码不正确，请重新登录");
            return  new ModelAndView("login");
        }
    }

}
