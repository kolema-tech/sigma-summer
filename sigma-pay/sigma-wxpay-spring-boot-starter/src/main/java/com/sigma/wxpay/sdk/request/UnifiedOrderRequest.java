package com.sigma.wxpay.sdk.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


/**
 * @author huston.peng
 * @version 1.0
 * date-time: 2019-10-
 * desc: 统一下单请求
 **/
@Getter
@Setter
@Builder
public class UnifiedOrderRequest {

    private String body;

    private String orderId;

    private Integer totalFee;

    private String spbillCreateIp;

    private String notifyUrl;

    private String tradeType;


    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>(16);

        result.put("body", body);
        result.put("out_trade_no", orderId);
        result.put("total_fee", totalFee.toString());
        result.put("spbill_create_ip", spbillCreateIp);
        result.put("notify_url", notifyUrl);
        result.put("trade_type", tradeType);

        return result;
    }

}
