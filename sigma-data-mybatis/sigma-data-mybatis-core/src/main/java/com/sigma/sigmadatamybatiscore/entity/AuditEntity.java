package com.sigma.sigmadatamybatiscore.entity;

import com.sigma.sigmadatamybatiscore.audit.UpdateUserId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * 如果表格中不含此字段，可以不继承此类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuditEntity extends BaseEntity {

    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 创建人id
     */
    @Column(name = "create_user_name")
    private String createUserName;


    /**
     * 创建时间
     */
    @Column(name = "create_date_time")
    private LocalDateTime createTime;

    /**
     * 最后更新人id
     */
    @UpdateUserId
    @Column(name = "update_user_id")
    private String updateUserId;

    /**
     * 最后更新人id
     */
    @Column(name = "update_user_name")
    private String updateUserName;

    /**
     * 最后更新时间
     */
    @Column(name = "update_date_time")
    private LocalDateTime updateTime;
}
