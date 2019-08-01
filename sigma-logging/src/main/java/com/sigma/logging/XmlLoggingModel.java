package com.sigma.logging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.var;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/8/6-13:19
 * desc:
 **/
@Deprecated
@Getter
@Setter
@NoArgsConstructor
public class XmlLoggingModel {

    /**
     * The request xml.
     */
    private String rq;

    /**
     * The response xml.
     */
    private String rs;

    /**
     * The class name.
     */
    private String className;

    /**
     * The method name.
     */
    private String methodName;

    /**
     * 消息
     */
    private String message = "";

    /**
     * 詳細
     */
    private String detail;

    /**
     * @param detail
     * @param rq
     * @param rs
     */
    public XmlLoggingModel(String detail, String rq, String rs) {

        this.detail = detail;

        var ste = new Exception().getStackTrace();
        this.className = ste[1].getClassName();
        this.methodName = ste[1].getMethodName();

        this.rq = rq;
        this.rs = rs;
    }
}
