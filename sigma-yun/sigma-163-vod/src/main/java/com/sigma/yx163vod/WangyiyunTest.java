package com.sigma.yx163vod;

import lombok.experimental.var;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class WangyiyunTest {

    /*以下为调用示例*/
    public static void main(String[] argv) throws UnsupportedEncodingException, MalformedURLException {

        String appKey = "";
        String secret = "";
        String url = "http://jdvodcwjw2btk.vod.126.net/jdvodcwjw2btk/310ee578-befd-47f0-bb21-48142a7a04a7.mp4";
        URL url1 = new URL(url);

        var authBody = "/jdvodcwjw2btk/310ee578-befd-47f0-bb21-48142a7a04a7.mp4";
        var resId = appKey + "_0_0";
        System.out.println(resId);
        resId = URLEncoder.encode(resId, "UTF-8");
        System.out.println(resId);

        long authTime = System.currentTimeMillis() / 1000 + 2 * 60;
        var authSign = AuthSignBuilder.getAuthSign(secret, authBody, String.valueOf(authTime));
        System.out.println(authSign);
        authSign = URLEncoder.encode(authSign, "UTF-8");
        System.out.println(authSign);

        System.out.println(url + "?resId=" + resId + "&authTime=" + authTime + "&authSign=" + authSign);
    }
}
