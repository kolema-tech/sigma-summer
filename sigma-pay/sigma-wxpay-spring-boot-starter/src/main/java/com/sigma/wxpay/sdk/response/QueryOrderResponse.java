package com.sigma.wxpay.sdk.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QueryOrderResponse {

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String code_url;
    private String trade_type;
    private String trade_state;
    private String err_code;
    private String err_code_des;

    private String openid;
    private String is_subscribe;
    private String bank_type;
    private Integer total_fee;
    private Integer cash_fee;

    private String transaction_id;
    private String out_trade_no;
    private String time_end;
    private String trade_state_desc;

    public QueryOrderResponse(Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
        org.apache.commons.beanutils.BeanUtils.populate(this, map);
    }

}
