package com.sigma.wxpay.sdk.wrapper;

import com.sigma.wxpay.sdk.WXPay;
import com.sigma.wxpay.sdk.WXPayConfig;
import com.sigma.wxpay.sdk.request.QueryOrderRequest;
import com.sigma.wxpay.sdk.request.UnifiedOrderRequest;
import com.sigma.wxpay.sdk.response.QueryOrderResponse;
import com.sigma.wxpay.sdk.response.UnifiedOrderResponse;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@Component
@Slf4j
public class WxPayWrapper {

    @Autowired
    WXPayConfig wxPayConfig;

    public UnifiedOrderResponse unifiedOrderRequest(UnifiedOrderRequest request) throws Exception {
        var wxPay = new WXPay(wxPayConfig, false);
        var result = wxPay.unifiedOrder(request.toMap());
        return new UnifiedOrderResponse(result);
    }

    public QueryOrderResponse queryOrderRequest(QueryOrderRequest request) throws Exception {
        var wxPay = new WXPay(wxPayConfig, false);
        var result = wxPay.orderQuery(request.toMap());
        return new QueryOrderResponse(result);
    }
}
