//package com.sigma.sdk.interfaces;
//
//import com.sun.deploy.net.HttpResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import sun.net.www.http.HttpClient;
//
//import static com.google.common.net.HttpHeaders.USER_AGENT;
//
///**
// * API 模板
// */
//public class ApiTemplate<TResponse, TRequest> {
//
//    @Autowired
//    RestTemplateBuilder restTemplateBuilder;
//
//    /**
//     * @param apiRequest
//     * @param <TResponse>
//     * @param <TRequest>
//     * @return
//     * @throws ApiException
//     */
//    public <TResponse, TRequest> ApiResponse<TResponse> make(ApiRequest<TRequest> apiRequest) throws ApiException {
//
//        restTemplateBuilder.rootUri("");
//
//        HttpClient httpClient = HttpClientBuilder.create()
//                .setConnectionManager(connManager)
//                .build();
//
//        String url = "https://" + domain + urlSuffix;
//        HttpPost httpPost = new HttpPost(url);
//
//        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
//        httpPost.setConfig(requestConfig);
//
//        StringEntity postEntity = new StringEntity(data, "UTF-8");
//        httpPost.addHeader("Content-Type", "text/xml");
//        httpPost.addHeader("User-Agent", USER_AGENT + " " + config.getMchID());
//        httpPost.setEntity(postEntity);
//
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity httpEntity = httpResponse.getEntity();
//        return EntityUtils.toString(httpEntity, "UTF-8");
//
//        return new ApiResponse();
//    }
//}