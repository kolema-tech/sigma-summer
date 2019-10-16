package com.sigma.wxpay.sdk.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.8
 * date-time: 2019-10-
 * desc: 根据订单号查询
 **/
@Getter
@Setter
@Builder
public class QueryOrderRequest {

    /**
     * 根据订单号查询
     */
    private String orderId;

    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>(16);

        result.put("out_trade_no", orderId);

        return result;
    }
}
