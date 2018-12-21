package com.sigma.tran.tcc.entity;

import com.sigma.data.domain.BaseDomain;
import com.sigma.tran.tcc.valueobject.TccStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-13:25
 * desc: Tcc 基類
 **/
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
public class NormalTcc extends BaseDomain {

    /**
     * 過期時間
     */
    private LocalDateTime expiryTime;

    /**
     * Tcc狀態
     */
    @Enumerated(EnumType.STRING)
    private TccStatus tccStatus;
}
