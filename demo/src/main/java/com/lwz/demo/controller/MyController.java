package com.lwz.demo.controller;

import com.lwz.demo.pojo.Msg;
import com.lwz.demo.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MyController {

    @RequestMapping("/add")
    public String add(){
        return "add";
    }
    @RequestMapping("/get")
    @ResponseBody
    public void getMsg(Model model){
        MsgService service = new MsgService();

    }
    @RequestMapping("/post")
    @ModelAttribute
    public ModelAndView addMsg(Integer id,
                       String name,
                       Integer age,
                       String gender,
                       Integer birth,
                       Model model){
        MsgService service = new MsgService();
        Msg msg = new Msg();
        System.out.println(id);
        msg.setId(id);
        System.out.println(msg.getId());
        msg.setName(name);
        msg.setAge(age);
        msg.setGender(gender);
        msg.setBirth(birth);
        System.out.println(msg);
        service.insert(msg);
        model.addAttribute("msg",msg);
        List<Msg> msgs = service.selectAll();
        for(Msg msg1: msgs){
            System.out.println(msg1);
        }
        model.addAttribute("msg2",msgs);
        ModelAndView modelAndView = new ModelAndView("result","msg3",model);
        return  modelAndView;
    }

}


