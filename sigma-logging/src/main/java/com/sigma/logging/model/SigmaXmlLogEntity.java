package com.sigma.logging.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/8/6-10:48
 * desc: Define the xml log entity.
 **/
@Data
public class SigmaXmlLogEntity extends SigmaLogEntity implements Serializable {

    /**
     * The class name.
     */
    private String className;

    /**
     * The method name.
     */
    private String methodName;

    /**
     * The request xml.
     */
    private String rq;

    /**
     * The response xml.
     */
    private String rs;
}
