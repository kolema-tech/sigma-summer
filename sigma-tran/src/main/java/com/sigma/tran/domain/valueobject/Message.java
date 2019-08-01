package com.sigma.tran.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.sigma.data.domain.BaseDomain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-10:46
 * desc: 發佈的消息
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Message extends BaseDomain implements Serializable {

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