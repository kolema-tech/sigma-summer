package com.sigma.sigmacore.validation;

import lombok.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:49
 * desc: 驗證結果
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 錯誤代碼
     */
    private String code;

    /**
     * 消息
     */
    private String message;
}
