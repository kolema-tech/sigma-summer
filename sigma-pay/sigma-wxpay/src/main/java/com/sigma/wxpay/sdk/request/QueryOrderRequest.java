package com.sigma.wxpay.sdk.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class QueryOrderRequest {

    /**
     * 根据订单号查询
     */
    private String out_trade_no;

    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();

        result.put("out_trade_no", out_trade_no);

        return result;
    }
}
