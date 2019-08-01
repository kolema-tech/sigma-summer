package com.sigma.sdk.interfaces;

import lombok.Getter;
import lombok.Setter;

/**
 * API
 *
 * @param <TRequest>
 */
@Getter
@Setter
public class ApiRequest<TRequest> {

    /**
     * 路径
     */
    private UrlSuffix urlSuffix;

    /**
     * Http请求方法
     */
    private String method;

    /**
     * 请求头
     */
    private ApiHeaders apiHeaders;

    /**
     * 参数
     */
    private ApiParameters apiParameters;

    /**
     *
     */
    private ApiRequestBody apiRequestBody;

    /**
     * 授权
     */
    private ApiAuthorization apiAuthorization;

    private TRequest data;
}
