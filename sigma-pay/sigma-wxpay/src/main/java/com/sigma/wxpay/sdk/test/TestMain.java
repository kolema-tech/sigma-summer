package com.sigma.wxpay.sdk.test;

import com.sigma.wxpay.sdk.DefaultWxPayConfig;
import com.sigma.wxpay.sdk.WXPay;
import com.sigma.wxpay.sdk.WXPayConstants;
import com.sigma.wxpay.sdk.WXPayUtil;
import com.sigma.wxpay.sdk.request.QueryOrderRequest;
import com.sigma.wxpay.sdk.request.UnifiedOrderRequest;
import lombok.experimental.var;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class TestMain {

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static void main(String[] args) throws Exception {

        queryOrder();

        DefaultWxPayConfig defaultWxPayConfig = new DefaultWxPayConfig();

        WXPay wxPay = new WXPay(defaultWxPayConfig, false);

        var map = UnifiedOrderRequest.builder()
                .body("视频购买")
                .notify_url("http://kolematech.com/")
                .out_trade_no("asdf2323423")
                .spbill_create_ip("127.0.0.1")
                .total_fee(1)
                .trade_type("APP").build().toMap();
        var result = wxPay.unifiedOrder(map);
        System.out.println(result);
    }

    public static void queryOrder() throws Exception {
        DefaultWxPayConfig defaultWxPayConfig = new DefaultWxPayConfig();

        WXPay wxPay = new WXPay(defaultWxPayConfig);
        var result = wxPay.orderQuery(QueryOrderRequest.builder().out_trade_no("1101415943569715200").build().toMap());
        System.out.println(result);
    }

    public static String GetSignKey() throws Exception {

        DefaultWxPayConfig defaultWxPayConfig = new DefaultWxPayConfig();

        String nonce_str = WXPayUtil.generateNonceStr();//生成随机字符
        Map<String, String> param = new HashMap<String, String>();
        param.put("mch_id", defaultWxPayConfig.getMchID());//需要真实商户号
        param.put("nonce_str", nonce_str);//随机字符
        param.put("total_fee", "100");
        String sign = WXPayUtil.generateSignature(param, defaultWxPayConfig.getKey(), WXPayConstants.SignType.MD5);//通过SDK生成签名其中API_KEY为商户对应的真实密钥
        param.put("sign", sign);
        String xml = WXPayUtil.mapToXml(param);//将map转换为xml格式
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";//沙箱密钥获取api
        String SignKey = post(url, xml);//
        System.out.println("signkey+" + SignKey);
        Map<String, String> param1 = new HashMap<String, String>();
        param1 = WXPayUtil.xmlToMap(SignKey);
        String key = param1.get("sandbox_signkey");
        return key;
    }

    public static String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
