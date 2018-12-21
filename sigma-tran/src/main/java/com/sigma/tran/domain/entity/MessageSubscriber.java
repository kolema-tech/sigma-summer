package com.sigma.tran.domain.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.sigma.data.domain.BaseDomain;
import com.sigma.tran.domain.valueobject.Message;
import com.sigma.tran.domain.valueobject.MessageStatus;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-10:46
 * desc: 接收的消息
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class MessageSubscriber extends BaseDomain {

    /**
     * 业务类型
     * eg: 系統，子系統等.
     */
    @NotNull
    private String businessType;

    /**
     * 负载，直接存儲JSON
     */
    @NotNull
    @JsonRawValue
    private String payload;

    /**
     * 消息状态
     */
    @NotNull
    private MessageStatus messageStatus;

    /**
     * 版本
     * 支持並發
     */
    @NotNull
    @Version
    private Integer lockVersion;
}