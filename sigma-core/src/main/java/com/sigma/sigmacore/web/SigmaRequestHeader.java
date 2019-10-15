package com.sigma.sigmacore.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.var;
import org.springframework.util.StringUtils;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/6/5-10:50
 * desc: 請求頭
 **/
@Getter
@Setter
@ToString
public class SigmaRequestHeader {

    /**
     * 客戶端id
     */
    private String clientId;

    /**
     * 客戶端版本
     */
    private String clientVer;

    /**
     * 语言（包括站点）
     */
    private String lang;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 系统代码
     */
    private String systemCode;

    /**
     * 项目代码
     */
    private String projectCode;

    /**
     * 会员token
     */
    private String token;


    @JsonIgnore
    public String getSite() {
        try {
            if (StringUtils.hasLength(lang)) {
                var arr = lang.split("_");
                if (arr.length == 2) {
                    return arr[1];
                }
            }

        } catch (Exception ex) {
        }

        return "";
    }
}
