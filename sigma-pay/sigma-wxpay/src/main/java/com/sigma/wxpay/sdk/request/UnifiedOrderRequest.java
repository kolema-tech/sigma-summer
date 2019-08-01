package com.sigma.wxpay.sdk.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一下单请求
 */
@Getter
@Setter
@Builder
public class UnifiedOrderRequest {

    private String body;

    private String out_trade_no;

    private Integer total_fee;

    private String spbill_create_ip;

    private String notify_url;

    private String trade_type;


    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();

        result.put("body", body);
        result.put("out_trade_no", out_trade_no);
        result.put("total_fee", total_fee.toString());
        result.put("spbill_create_ip", spbill_create_ip);
        result.put("notify_url", notify_url);
        result.put("trade_type", trade_type);

        return result;
    }

}
