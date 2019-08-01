package com.sigma.soa;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/12/21-10:57
 * desc:
 **/
@Getter
@Setter
public class SoapRequestConfig {
    private String url;
    private String userName;
    private String password;
    private String soapAction;
}
