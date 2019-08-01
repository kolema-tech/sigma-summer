package com.sigma.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/10/21-18:39
 * desc:
 **/
@Deprecated
@Getter
@Setter
public class SigmaPerformanceEntity {
    /**
     * project group
     */
    public String group;

    /**
     * current thread id
     */
    public String threadId;

    /**
     * current thread name
     */
    public String threadName;

    /**
     * code of system
     */
    public String systemCode;

    /**
     * code of system
     */
    public String source;

    /**
     * text message of log
     */
    public String message;

    /**
     * name of current application machine
     */
    public String machineName;

    /**
     * application domain name
     */
    public String appDomainName;

    /**
     * current process id
     */
    public int processId;

    /**
     * current process name
     */
    public String processName;
    /**
     * the detail of log
     */
    public String detail;
    /**
     * record time for log
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    /**
     * the ip address of the machine of current application
     */
    public String ipAddress;

    /**
     * log remark
     */
    public String remark;

    /**
     * request
     */
    public String rq;

    /**
     * response
     */
    public String rs;

    /**
     * duration
     */
    public long duration;
}
