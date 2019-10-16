package com.sigma.samples;

import com.aliyuncs.exceptions.ClientException;
import com.sigma.aliyunstarter.AliOssService;
import com.sigma.aliyunstarter.AliSmsService;
import com.sigma.wxpay.sdk.request.QueryOrderRequest;
import com.sigma.wxpay.sdk.request.UnifiedOrderRequest;
import com.sigma.wxpay.sdk.wrapper.PayWrapperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/6/14-15:38
 * desc:
 **/
@SpringBootApplication(scanBasePackages = "com.sigma")
@Api(value = "App")
@RestController
public class App {

    @Autowired
    AliSmsService aliSmsService;
    @Autowired
    AliOssService aliOssService;

    @Autowired
    PayWrapperService payWrapperService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @ApiOperation(value = "统一下单")
    @GetMapping("/api/pay/createOrder")
    public Object createOrder(String orderId) throws Exception {

        return payWrapperService.unifiedOrderRequest(
                UnifiedOrderRequest.builder()
                        .body("印花")
                        .notifyUrl("http://kolematech.com/")
                        .orderId(orderId)
                        .spbillCreateIp("127.0.0.1")
                        .totalFee(1)
                        .tradeType("NATIVE").build());
    }

    @ApiOperation(value = "查询订单")
    @GetMapping("/api/pay/queryOrder")
    public Object queryOrder(String orderId) throws Exception {

        return payWrapperService.queryOrderRequest(
                QueryOrderRequest.builder().orderId(orderId).build());
    }


    @ApiOperation(value = "get method")
    @GetMapping("/api/getFileUrl")
    public URL getUser(String key, Integer seconds) {
        return aliOssService.getOssUrl(key, seconds);
    }

    @ApiOperation(value = "发送短信")
    @GetMapping("/api/sendVerifyCode")
    public Object sendVerifyCode(String mobile) throws ClientException {
        return aliSmsService.sendVerifyCode(mobile, "SMS_50690014", "{\"code\":\"2468\",\"product\":\"预约\"}");
    }

    @ApiOperation(value = "上传会员头像")
    @PutMapping(value = "/avatar")
    public Object uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String extName = "";
        assert fileName != null;
        if (".".contains(fileName)) {
            extName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }

        String randomName = "avatar2/" + UUID.randomUUID() + extName;

        aliOssService.upload(randomName, file.getInputStream());

        return aliOssService.getOssUrl(randomName);
    }

}