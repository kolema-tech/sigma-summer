package com.sigma.data.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/19-15:48
 * desc: 根据UUID映射实体
 **/
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityUuid {

    @NotNull
    @Id
    @Column(nullable = false, length = 40)
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @CreatedBy
    private String createUserId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @LastModifiedBy
    private String updateUserId;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateDateTime;
}
