package com.sigma.sigmacore.web;

import com.sigma.sigmacore.constant.SigmaResponseCodes;
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
    private String code = SigmaResponseCodes.SUCCESS;
    /**
     * 消息
     */
    private String message;
}
