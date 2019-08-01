package com.sigma.sigmadatamybatiscore.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/12/21-14:28
 * desc:
 **/
@Data
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
}
