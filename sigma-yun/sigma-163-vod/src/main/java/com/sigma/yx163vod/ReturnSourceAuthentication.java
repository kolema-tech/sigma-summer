package com.sigma.yx163vod;

import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class ReturnSourceAuthentication {

    @Autowired
    AuthConfig authConfig;

    /**
     * 生成播放链接
     * 当设置vid=0且style=0表示生成对所有视频都有效的播放凭证
     *
     * @param url     原视频地址
     * @param seconds 有效期，秒数
     * @return
     * @throws UnsupportedEncodingException
     */
    public String signUrl(URL url, long seconds) throws UnsupportedEncodingException {

        var authBody = url.getPath();

        var resId = authConfig.getAppKey() + "_0_0";
        resId = URLEncoder.encode(resId, "UTF-8");

        long authTime = System.currentTimeMillis() / 1000 + seconds;
        var authSign = AuthSignBuilder.getAuthSign(authConfig.getAppSecret(), authBody, String.valueOf(authTime));
        authSign = URLEncoder.encode(authSign, "UTF-8");

        return url.toString() + "?resId=" + resId + "&authTime=" + authTime + "&authSign=" + authSign;
    }
}
