package com.sigma.sigmacore.web;

import lombok.*;

import javax.validation.Valid;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-10:55
 * desc: 請求，默認只支持JSON格式的.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SigmaRequest<T> {

    /**
     * 請求頭
     */
    private SigmaRequestHeader header;

    /**
     * 請求數據體
     */
    @Valid
    private T data;

    /**
     * 是否為模擬數據
     */
    private Boolean mockData;
}
