package com.lwz.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.pojo.UserMsg;
import com.lwz.demo.service.LoginService;
import com.lwz.demo.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class Echarts {

    @Autowired
    LoginService loginService;
    @RequestMapping(value = "/echarts",method = RequestMethod.GET)
    @ResponseBody
    public String eCharts(HttpSession session, @RequestParam("name")String name,@RequestParam("user_id")Integer user_id){
        System.out.println(name+user_id+"进入echarts方法");
        String username = (session.getAttribute("username")).toString().trim();
        String password = (session.getAttribute("password")).toString().trim();
        Integer admin_id = Integer.parseInt((session.getAttribute("admin_id")).toString());
        UserMsg userMsg = loginService.selectUserMsgByUserNameAndPasswordAndAdminID(username, password, admin_id);
        if(userMsg!=null) {
            System.out.println("hello我是controller方法");
            MsgService msgService = new MsgService();
            UpMsg upMsg = new UpMsg();
            System.out.println("这是user_id"+user_id);
            System.out.println("这是文件名"+name);
                upMsg = msgService.selectByNameAndUserId(name, user_id);
            System.out.println(upMsg);
                List<Text> texts = msgService.selectAllTextByID(upMsg.getId());
                List<Integer> btimes = new ArrayList<>();
                List<Integer> moods = new ArrayList<>();
                for (Text text : texts) {
                    int i = text.getbTime();
                    int mood = text.getMood();
                    btimes.add(i);
                    moods.add(mood);
                    System.out.println("这里是取出的mood值"+mood);
                    System.out.println("这里是取出的btimes值"+btimes);
                }
                Map<String, Integer[]> m1 = new HashMap<>();
                Object[] btimes1 = btimes.toArray();
                Object[] moods1 = moods.toArray();
                Integer[] bt = new Integer[btimes1.length];
                for (int k = 0; k < btimes1.length; k++) {
                    bt[k] = Integer.parseInt(btimes1[k].toString());
                    System.out.println("这里是x轴的值"+bt[k]);
                }
                Integer[] md = new Integer[moods1.length];
                for (int k = 0; k < moods1.length; k++) {
                    md[k] = Integer.parseInt(moods1[k].toString());
                    System.out.println("这里是Y轴的值"+md[k]);
                }
//                m1.put("btimes", bt);
//                m1.put("moods", md);
//                for (Object o : btimes1) {
//                    System.out.println(o);
//                }
//                Set<String> strings = m1.keySet();
                String a = "";
                for (int i = 0; i < bt.length; i++) {
                    a += "\"" + bt[i] + "\",";
                }
            System.out.println("这是字符串a的值"+a);
                a = a.substring(0, a.length() - 1);
                String b = "";
                int sum = 0;
                for (int i = 0; i < md.length; i++) {
                    sum += md[i];
                    b += sum + ",";
                    System.out.println(b);
                }
            System.out.println("这是字符串b的值"+b);
                b = b.substring(0, b.length() - 1);
                return "<html>\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf8\">\n" +
                        "    <title>吃藕</title>\n" +
                        "    <!-- 引入 echarts.js -->\n" +
                        "    <script src=\"https://cdn.bootcdn.net/ajax/libs/echarts/4.8.0/echarts.min.js\"></script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div id=\"main33\" style=\"width:1800px;height:800px;\"></div><br />\n" +
                        "   \n" +
                        "   \n" +
                        "   <script type=\"text/javascript\">\n" +
                        "     \n" +
                        "        var myChart = echarts.init(document.getElementById('main33'));\n" +
                        "\n" +
                        "       \n" +
                        "\n" +
                        "option = {\n" +
                        "    xAxis: {\n" +
                        "        type: 'category',\n" +
                        "        data: " + "[" + a + "]" + "\n" +
                        "    },\n" +
                        "    yAxis: {\n" +
                        "        type: 'value'\n" +
                        "    },\n" +
                        "    series: [{\n" +
                        "        data: " + "[" + b + "]" + ",\n" +
                        "        type: 'line'\n" +
                        "    }]\n" +
                        "};\n" +
                        "\n" +
                        "        // 使用刚指定的配置项和数据显示图表。\n" +
                        "        myChart.setOption(option);\n" +
                        "    </script>\n" +
                        "</body>\n" +
                        "</html>";

        }
        return "error";
    }
//    @RequestMapping(value = "/echarts",method = RequestMethod.GET)
    @RequestMapping("/chart11")
    public ModelAndView jump(){
        return new ModelAndView("/echarts");
    }
}

