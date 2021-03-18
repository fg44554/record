package com.lwz.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lwz.demo.controller.user.util.TestUtil;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.service.MsgService;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadAliController implements Runnable{
    @Override
    public synchronized void run() {
        MsgService msgService = new MsgService();
        while (true) {
            synchronized (this) {
                if ((msgService.selectByStat()).size() != 0) {
                    System.out.println("发现状态为N的音频");
                    List<UpMsg> upMsgs = msgService.selectByStat();
                    for (UpMsg upMsg : upMsgs) {
                        System.out.println(upMsg.getName());
                        //进不去
//                        if ((msgService.selectUidByName(upMsg.getName())).size() == 0) {
                            System.out.println("发现未处理文字，开始处理");
                            TestUtil testUtil = new TestUtil(upMsg);
                            String fileLink = "http://8.131.64.240:8080/GetFile/" + upMsg.getName();
                            // 第一步：提交录音文件识别请求，获取任务ID用于后续的识别结果轮询。
                        System.out.println(fileLink+"filelink");
                            RequestController demo = new RequestController();
                            demo.RequestController1("XXXXXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                            String taskId = demo.firstRequest("XXXXXXXXXXXXXXXXXXXXXXXXXXXX", fileLink);
                            if (taskId != null) {
                                System.out.println("录音文件识别请求成功，task_id: " + taskId);
                            } else {
                                System.out.println("录音文件识别请求失败！");
//            return new ModelAndView("erro");
                            }
                            // 第二步：根据任务ID轮询识别结果。
                            String str = demo.getFileTransResult(taskId);
                            if (str != null) {
                                //获取"result"中的json字符串
                                JSONObject parse = (JSONObject) JSON.parse(str);
                                //获取字符数组
                                String str1 = parse.getString("Sentences");
                                JSONArray parse1 = (JSONArray) JSONArray.parse(str1);
                                String resultText = null;
                                Text text = new Text();
                                //遍历数组中的Text项
                                for (int i1 = 0; i1 < parse1.size(); i1++) {

                                        JSONObject jsonObject = parse1.getJSONObject(i1);
                                        resultText = jsonObject.getString("Text");
                                        Integer endTime = jsonObject.getInteger("EndTime");
                                        Integer beginTime = jsonObject.getInteger("BeginTime");
                                        Integer Time = endTime - beginTime;
                                        System.out.println(resultText);
                                        text.setbTime(beginTime);
                                        text.seteTime(endTime);
                                        text.setTime(Time);
                                        text.setText(resultText);
                                        text.setTitle(upMsg.getName());
                                        text.setUid(upMsg.getId());
                                        text.setUser_id(upMsg.getUser_id());
                                        text.setAdmin_id(upMsg.getAdmin_id());
                                        text.setMood_stat("n");
                                        String regEx = "[^0-9]";
                                        Pattern p = Pattern.compile(regEx);
                                        Matcher matcher = p.matcher(resultText);
                                        String num = matcher.replaceAll("").trim();
                                        if (!num.equals("") && num.length() == 11) {
                                            text.setPhone(num);
                                        } else {
                                            text.setPhone("null");
                                        }
                                        //解决了数据重复插入的问题
                                        List<String> s=msgService.selectTextByUID(text.getUid());
                                        if(!(s.contains((text.getText().trim())))){
                                        msgService.insertT(text);}
                                    }
                                //修复了统计电话号码次数时，按电话号在数据库中出现的总数统计次数的问题，新方法采用uid方式辨别具体音频
                                msgService.updateTimes(text.getUid());
                                msgService.updateStatById(upMsg.getId());

                            } else {
                                System.out.println("录音文件识别结果查询失败！");
//            return new ModelAndView("erro");
                            }
                        }
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("转文字轮询开始");
//                }
            }
        }
    }
}
