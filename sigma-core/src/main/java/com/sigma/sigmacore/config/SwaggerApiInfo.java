package com.sigma.sigmacore.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/4-12:23
 * desc: Swagger 配置
 **/
@Getter
@Builder
@ToString(callSuper = true)
public class SwaggerApiInfo {
    /**
     * 標題
     */
    private String title;

    /**
     * 版本
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 服務Url
     */
    private String termsOfServiceUrl;

    /**
     * 聯繫人 姓名
     */
    private String contactName;

    /**
     * 聯繫人地址
     */
    private String contactUrl;

    /**
     * 聯繫人郵箱
     */
    private String contactEmail;
}
