package com.lwz.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
// 这里以词性标注为例，其它算法的API名称和参数请参考文档
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomRequest;
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.lwz.demo.pojo.Text;
import com.lwz.demo.pojo.UpMsg;
import com.lwz.demo.service.MsgService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Controller
public class Mood {

    @RequestMapping("/mood1")
    @ResponseBody
    public String mood() throws ClientException {
        String accessKeyId = "XXXXXXXXXXXXXXXXXXXXXX";
        String accessKeySecret = "XXXXXXXXXXXXXXXXXXXXXX";
        CommonResponse response = null;
        UpMsg upMsg = new UpMsg();
        MsgService msgService = new MsgService();
        List<Text> sentences = this.sentence(upMsg);
            for (Text sentence:sentences) {
                try {
                    DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Nlp", "nlp.cn-shanghai.aliyuncs.com");
                    IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKeyId, accessKeySecret);
                    IAcsClient client = new DefaultAcsClient(profile);
                    String postBody;
                postBody = "{\n"
                        + "  \"text\": \""+ sentence.getText() +"\",\n"
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
                    String str1 = parse.getString("data");
                    JSONObject parse1 = (JSONObject) JSON.parse(str1);
                    Integer integer = parse1.getInteger("text_polarity");
                    msgService.updateMoodById(sentence.getId(),integer);
                    msgService.updateMoodStatById(sentence.getId());
                }
                catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        assert response != null;
        return response.getData();
    }


    public List<Text> sentence(UpMsg upMsg){
        MsgService msgService = new MsgService();
        List<Text> texts = msgService.selectAllTextByName(upMsg.getName());
        return texts;
    }
}
