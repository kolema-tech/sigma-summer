package com.sigma.tran.domain.entity;

import com.sigma.tran.domain.repository.PersonTccRepository;
import com.sigma.tran.tcc.valueobject.TccStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-13:32
 * desc:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonTccTest {

    @Autowired
    PersonTccRepository personTccRepository;

    @Test
    public void test_tcc_entity() {
        PersonTccEntity personTccEntity = new PersonTccEntity();
        personTccEntity.setAmount(new BigDecimal("100"));
        personTccEntity.setOrderId("asdfas");
        personTccEntity.setExpiryTime(LocalDateTime.now());
        personTccEntity.setTccStatus(TccStatus.USED);

        personTccRepository.save(personTccEntity);
    }
}
