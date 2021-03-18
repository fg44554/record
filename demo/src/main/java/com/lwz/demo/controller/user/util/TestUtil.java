package com.lwz.demo.controller.user.util;

import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.service.MsgService;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class TestUtil {
    UpMsg upMsg;
    MsgService msgService = new MsgService();
    public TestUtil(UpMsg upMsg){
        this.upMsg=upMsg;
    }
    public Boolean testExist(){
        String name = upMsg.getName();
        return msgService.selectUidByName(name) != null && msgService.selectUidByName(name).contains(upMsg.getId());
    }
    public ModelAndView toCharFromDb(Model model){
        if(testExist()){
            System.out.println("进入run方法");
            List<Text> texts = msgService.selectAllTextByName(upMsg.getName());
            model.addAttribute("texts", texts);
            return new ModelAndView("responseText1", "text", model);
        }else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return toCharFromDb(model);
    }

}
