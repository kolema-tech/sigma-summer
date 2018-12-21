package com.sigma.tran.domain.valueobject;

import lombok.Getter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-10:50
 * desc: 消息處理狀態
 **/
public enum MessageStatus {

    /**
     * 未知錯誤
     */
    ERROR(-100),

    /**
     * 消息没有到exchange,则confirm回调,ack=false
     */
    NOT_FOUND(-2),

    /**
     * exchange到queue失败,则回调return
     */
    NO_ROUTE(-1),

    /**
     * 新建
     */
    NEW(0),

    /**
     * 處理中
     */
    PENDING(1),

    /**
     * 已完成
     */
    DONE(2);

    @Getter
    private final int status;

    MessageStatus(int status) {
        this.status = status;
    }
}
