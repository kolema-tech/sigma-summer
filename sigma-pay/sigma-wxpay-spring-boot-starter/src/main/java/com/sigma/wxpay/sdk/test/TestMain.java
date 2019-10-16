package com.sigma.wxpay.sdk.test;

import com.sigma.wxpay.sdk.DefaultPayConfig;
import com.sigma.wxpay.sdk.WXPay;
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

        DefaultPayConfig defaultPayConfig = new DefaultPayConfig("", "", "");

        WXPay wxPay = new WXPay(defaultPayConfig, false);

        var map = UnifiedOrderRequest.builder()
                .body("视频购买")
                .notifyUrl("http://kolematech.com/")
                .orderId("asdf2323423")
                .spbillCreateIp("127.0.0.1")
                .totalFee(1)
                .tradeType("APP").build().toMap();
        var result = wxPay.unifiedOrder(map);
        System.out.println(result);
    }

    public static void queryOrder() throws Exception {
        DefaultPayConfig defaultPayConfig = new DefaultPayConfig("", "", "");

        WXPay wxPay = new WXPay(defaultPayConfig);
        var result = wxPay.orderQuery(QueryOrderRequest.builder().orderId("1101415943569715200").build().toMap());
        System.out.println(result);
    }

    public static String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in;
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
            StringBuilder sb = new StringBuilder("");
            String line;
            String lineSeparator = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(lineSeparator);
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
