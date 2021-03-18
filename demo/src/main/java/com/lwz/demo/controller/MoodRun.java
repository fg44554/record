package com.lwz.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.service.MsgService;

import java.util.List;
import java.util.UUID;

public class MoodRun implements Runnable{
    @Override
    public synchronized void run() {
        MsgService msgService = new MsgService();
        while (true) {
            if ((msgService.selectIdByMoodStat()).size() != 0) {
                System.out.println("情绪分析开始，存在未处理句子");
                List<Integer> ids = msgService.selectIdByMoodStat();
                for (Integer i : ids) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Text text = msgService.selectTextById(i);
                    String accessKeyId = "XXXXXXXXXXXXXXXXXXXXXX";
                    String accessKeySecret = "XXXXXXXXXXXXXXXXXXXXXXXXXXX";
                    CommonResponse response = null;
                    try {
                        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Nlp", "nlp.cn-shanghai.aliyuncs.com");
                        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);
                        IAcsClient client = new DefaultAcsClient(profile);
                        String postBody;
                        postBody = "{\n"
                                + "  \"text\": \"" + text.getText() + "\",\n"
                                + "  \"lang\": \"ZH\"\n" + "}";
                        CommonRequest request = new CommonRequest();
                        request.setDomain("nlp.cn-shanghai.aliyuncs.com"); // 必须设置domain
                        request.setUriPattern("/nlp/api/sentiment/ecommerce"); // 设置所要请求的API路径
                        request.setMethod(MethodType.POST); // 设置请求方式，目前只支持POST
                        request.setHttpContent(postBody.getBytes(), "utf-8", FormatType.JSON); // 设置请求内容以及格式
                        request.putHeadParameter("x-acs-signature-method", "HMAC-SHA1");
                        request.putHeadParameter("x-acs-signature-nonce", UUID.randomUUID().toString()); // 设置请求唯一码，防止网络重放攻击
                        request.setVersion("2018-04-08");
                        response = client.getCommonResponse(request); // 请求并获取结果
                        String data = response.getData();
                        System.out.println(response.getData());
                        JSONObject parse = (JSONObject) JSON.parse(data);
                        JSONObject str1 = parse.getJSONObject("data");
                        System.out.println(str1.getString("text_polarity"));
                        Integer integer = Integer.parseInt(str1.getString("text_polarity"));
                        msgService.updateMoodById(text.getId(), integer);
                        msgService.updateMoodStatById(text.getId());
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("情感分析线程正在轮询");
            }
        }
    }
}
