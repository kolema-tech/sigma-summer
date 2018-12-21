package com.sigma.sigmacore.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-13:00
 * desc:
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicAuthPair {
    /**
     * 用戶名
     */
    private String userName;

    /**
     * 密碼
     */
    private String password;
}
