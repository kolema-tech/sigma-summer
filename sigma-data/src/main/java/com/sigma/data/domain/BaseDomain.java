package com.sigma.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/4-12:41
 * desc: 領域類. impl the AuditorAware for auditing.
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseDomain {

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
    private LocalDateTime createTime;

    /**
     * 創建人
     */
    @CreatedBy
    private String createUser;

    /**
     * 更新日期
     */
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @LastModifiedBy
    private String updateUser;
}
