package com.sigma.wxpay.sdk.wrapper;

import com.sigma.wxpay.sdk.WXPay;
import com.sigma.wxpay.sdk.WXPayConfig;
import com.sigma.wxpay.sdk.request.QueryOrderRequest;
import com.sigma.wxpay.sdk.request.UnifiedOrderRequest;
import com.sigma.wxpay.sdk.response.QueryOrderResponse;
import com.sigma.wxpay.sdk.response.UnifiedOrderResponse;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc: 微信支付包装服务
 **/
@Slf4j
public class PayWrapperService {

    WXPayConfig wxPayConfig;

    public PayWrapperService(WXPayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    /**
     * 统一下单接口
     *
     * @param request 下单请求
     * @return 下单响应
     * @throws Exception 异常
     */
    public UnifiedOrderResponse unifiedOrderRequest(UnifiedOrderRequest request) throws Exception {
        var wxPay = new WXPay(wxPayConfig, false);
        var result = wxPay.unifiedOrder(request.toMap());
        return new UnifiedOrderResponse(result);
    }

    /**
     * 下单查询
     *
     * @param request 查询请求
     * @return 查询响应
     * @throws Exception 异常
     */
    public QueryOrderResponse queryOrderRequest(QueryOrderRequest request) throws Exception {
        var wxPay = new WXPay(wxPayConfig, false);
        var result = wxPay.orderQuery(request.toMap());
        return new QueryOrderResponse(result);
    }
}
