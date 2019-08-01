package com.sigma.data.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/5/19-15:43
 * desc:
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityAutoId {

    /**
     * 默認自增主鍵
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 創建日期
     */
    @CreatedDate
    private LocalDateTime createDateTime;

    /**
     * 創建人
     */
    @CreatedBy
    private String createUserId;

    /**
     * 更新日期
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateDateTime;

    /**
     * 更新人
     */
    @LastModifiedBy
    private String updateUserId;
}
