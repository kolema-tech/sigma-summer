package com.sigma.aliyunstarter;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/18-14:20
 * desc:
 **/
@Slf4j
public class AliSmsService {

    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    @Getter
    @Setter
    private AliyunSmsProperties smsProperties;

    public AliSmsService() {

    }

    public AliSmsService(AliyunSmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    public static String generateRandomCode(int num) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            int randomNum = (int) (Math.random() * 10);
            stringBuilder.append(randomNum);
        }

        return stringBuilder.toString();
    }

    /**
     * 发送验证码
     *
     * @param phoneNumber   手机号
     * @param templateCode  模板代码
     * @param templateParam 模板参数：EG："{\"code\":\"" + verifyCode + "\",\"product\":\"预约\"}"
     * @return SendSmsResponse
     * @throws ClientException 客户端异常
     */
    public SendSmsResponse sendVerifyCode(String phoneNumber, String templateCode, String templateParam) throws ClientException {

        DefaultProfile profile = DefaultProfile.getProfile("default", smsProperties.getAccessKeyId(), smsProperties.getSecret());
        DefaultProfile.addEndpoint("default", "default", PRODUCT, DOMAIN);

        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(smsProperties.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        return acsClient.getAcsResponse(request);
    }
}
