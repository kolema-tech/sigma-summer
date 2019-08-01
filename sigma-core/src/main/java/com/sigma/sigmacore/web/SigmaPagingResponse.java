package com.sigma.sigmacore.web;


import lombok.*;

/**
 * @author huston.peng
 * @version 1.0.4
 * date-time: 2019/4/10-13:59
 * desc: 分页响应
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SigmaPagingResponse<T extends PagingResponseParam> {

    /**
     * 头部
     */
    private SigmaResponseHeader header;

    /**
     * 響應體
     */
    private T data;
}
