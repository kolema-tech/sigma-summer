package com.sigma.rule;

import lombok.Getter;
import lombok.Setter;

/**
 * @author pzdn
 * 错误枚举
 */
public enum ErrorCodes {

    /**
     * 事實類型非法
     */
    FACT_DATA_TYPE_NOT_MATCH("Fact001", "事實:{0} 類型非法：{1}!"),

    /**
     * 事實鍵不存在
     */
    FACT_KEY_NOT_FOUND("Fact002", "事實:{0}鍵不存在!"),

    /**
     * 事實為null
     */
    FACT_VALUE_NULL("Fact003", "事實:{0} 值為null!"),

    /**
     * 事實 運算符不支持
     */
    FACT_OP_NOT_SUPPORT("Fact004", "不支持的運算符: {0}！");

    @Getter
    private String code;

    @Getter
    @Setter
    private String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
