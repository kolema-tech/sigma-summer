package com.sigma.aliyunvod;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/18-14:20
 * desc:
 **/
@Slf4j
public class SendSmsCodeUtil {

    @Value(value = "${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value(value = "${aliyun.secret}")
    private String secret;

    @Value(value = "${aliyun.signName}")
    private String signName;

    @Value(value = "${aliyun.templateCode}")
    private String templateCode;

    @Async
    public void sendVerifyCode(String phoneNumber, String verifyCode) {

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");
        CommonResponse response = null;
        try {
            log.info("发送短信验证码:" + phoneNumber + ":" + verifyCode);
            response = client.getCommonResponse(request);
            log.debug("发送短信验证码响应:" + response);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
