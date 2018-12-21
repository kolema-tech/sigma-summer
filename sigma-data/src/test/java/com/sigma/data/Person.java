package com.sigma.data;

import com.sigma.data.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author zhenpeng
 * @version 1.0
 * date-time: 2018/6/4-8:42
 * desc: 測試類
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person extends BaseDomain {
    private int age;
    private String name;
}
