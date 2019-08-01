package com.sigma.yx163vod;

import lombok.Getter;

/**
 * https://dev.yunxin.163.com/docs/product/%E9%80%9A%E7%94%A8/%E7%82%B9%E6%92%AD%E9%80%9A%E7%94%A8/%E5%9B%9E%E6%BA%90%E9%89%B4%E6%9D%83%E5%8A%9F%E8%83%BD%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E-%E5%87%AD%E8%AF%81%E7%AE%A1%E7%90%86%E6%A8%A1%E5%BC%8F
 */
public enum StyleType {
    Zero(0),
    One(1),
    Tow(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Sixteen(16),
    Seventeen(17);

    @Getter
    private int code;

    private StyleType(int code) {
        this.code = code;
    }
}
