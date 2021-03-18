package com.lwz.demo.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.imagerecog.model.v20190930.TaggingImageRequest;
import com.aliyuncs.imagerecog.model.v20190930.TaggingImageResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class ImgController {
    @RequestMapping("/img/{name}")
    public void img(@PathVariable(value = "name")String name){
        System.out.println("hello");
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "XXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXX");
        IAcsClient client = new DefaultAcsClient(profile);
        TaggingImageRequest request = new TaggingImageRequest();
        request.setRegionId("cn-shanghai");
        request.setImageURL("http://explorer-image.oss-cn-shanghai.aliyuncs.com/3G42OTdf_cIb8ogsS3jUFH-V/7.png?OSSAccessKeyId=LTAI4Fk9FstqSEYnqKJ5Dpeo&Expires=1606818618&Signature=WNksHrrsFsKjJ7q1uY3cne%2FNQCw%3D"+name);
        try {
            TaggingImageResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }

}
