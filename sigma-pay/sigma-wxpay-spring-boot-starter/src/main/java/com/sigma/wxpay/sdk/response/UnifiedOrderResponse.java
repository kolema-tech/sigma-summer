package com.sigma.wxpay.sdk.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UnifiedOrderResponse {

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String code_url;
    private String trade_type;
    private String prepay_id;
    private String err_code;
    private String err_code_des;

    public UnifiedOrderResponse(Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
        org.apache.commons.beanutils.BeanUtils.populate(this, map);
    }

}
