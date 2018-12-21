package com.sigma.soa;

import com.sigma.web.BasicRestTemplate;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * @author SteveGlory Zeng.
 * @version 1.0
 * date-time: 2018/7/26-17:10
 * desc:
 **/
@Component
public class SoapRequestTemplate {

    @Autowired
    BasicRestTemplate basicRestTemplate;

    private static HttpHeaders getHeader(String userName, String passWord, String soapAction) {
        String auth = userName + ":" + passWord;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.add("SOAPAction", soapAction);
        headers.add("Authorization", authHeader);
        headers.add("Content-Type", String.valueOf(MediaType.TEXT_XML));
        return headers;
    }

    public String send(String request, SoapRequestConfig soapRequestConfig) {
        return send(request, soapRequestConfig, String.class);
    }

    public <T> T send(String request, SoapRequestConfig soapRequestConfig, Class<T> responseType) {
        HttpHeaders headers = getHeader(soapRequestConfig.getUserName(), soapRequestConfig.getPassword(), soapRequestConfig.getSoapAction());
        RestTemplate restTemplate = basicRestTemplate.build();
        T returnObj = restTemplate.postForObject(soapRequestConfig.getUrl(), new HttpEntity(request, headers), responseType);
        return returnObj;
    }
}
