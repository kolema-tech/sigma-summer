package com.sigma.sigmacore.web;

import lombok.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/8/31-15:49
 * desc:
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SigmaResponseHeader {
    /**
     * 響應碼
     */
    private String code = "0";
    /**
     * 消息
     */
    private String message;
}
