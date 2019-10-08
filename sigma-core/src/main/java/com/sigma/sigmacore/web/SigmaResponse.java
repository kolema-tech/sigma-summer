package com.sigma.sigmacore.web;

import lombok.*;
import lombok.experimental.var;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-11:11
 * desc: 響應
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SigmaResponse<T> {

    /**
     * 头部
     */
    private SigmaResponseHeader header;

    /**
     * 響應體
     */
    private T data;

    /**
     * 構造器
     *
     * @param data 數據
     */
    public SigmaResponse(T data) {
        this.data = data;
    }

    /**
     * 創建
     *
     * @param data 數據
     * @param <T>  泛型
     * @return 響應
     */
    public static <T> SigmaResponse<T> ok(T data) {
        return new SigmaResponse(new SigmaResponseHeader(), data);
    }

    /**
     * 创建
     *
     * @param code    代码
     * @param message 消息
     * @param <T>     类型
     * @return 响应
     */
    public static <T> SigmaResponse<T> create(String code, String message) {
        return create(code, message, null);
    }

    /**
     * 创建
     *
     * @param code    代码
     * @param message 消息
     * @param data    数据
     * @param <T>     类型
     * @return 创建的对象
     */
    public static <T> SigmaResponse<T> create(String code, String message, T data) {
        var response = new SigmaResponse<T>();

        response.setHeader(new SigmaResponseHeader(code, message));
        response.setData(data);

        return response;
    }

    public static SigmaResponseBuilder builder() {
        var sigmaResponseBuilder = new SigmaResponseBuilder(new SigmaResponse());
        return sigmaResponseBuilder;
    }
}
