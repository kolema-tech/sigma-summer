package com.sigma.tran.domain.entity;

import com.sigma.tran.tcc.entity.NormalTcc;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-13:30
 * desc: Person Tcc
 **/
@Entity
@Getter
@Setter
public class PersonTccEntity extends NormalTcc {
    private BigDecimal amount;
    private String orderId;
}
