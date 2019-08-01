package com.sigma.sigmacore.web;

import lombok.*;

import javax.validation.Valid;

/**
 * @author huston.peng
 * @version 1.0.4
 * date-time: 2019/4/10-13:49
 * desc: 分页请求
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SigmaPagingRequest<T extends PagingRequestParam> {

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
