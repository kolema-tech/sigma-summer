package com.sigma.logging.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-12:37
 * desc: 日誌載體
 **/
@Data
@Deprecated
public class SigmaLogEntity implements Serializable {

    /**
     * 系統代碼
     */
    private String systemCode;

    /**
     * 子業務
     */
    private String source;

    /**
     * 消息
     */
    private String message = "";

    /**
     * 詳細
     */
    private String detail;

    /**
     *
     */
    private String appDomainName = "";

    /**
     * 進程Id
     */
    private Long processId;

    /**
     * 進程名稱
     */
    private String processName;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 線程Id
     */
    private Long threadId;

    /**
     * 創建日期
     */
    private String createTime;

    /**
     * 機器名稱
     */
    private String machineName;
}
