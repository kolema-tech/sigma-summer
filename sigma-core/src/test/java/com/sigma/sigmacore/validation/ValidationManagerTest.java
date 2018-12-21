package com.sigma.sigmacore.validation;

import com.sigma.sigmacore.Person;
import lombok.experimental.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:59
 * desc:
 **/
@RunWith(SpringRunner.class)
public class ValidationManagerTest {

    @Autowired
    BasicValidation basicValidation;

    @Autowired
    PersonValidation personValidation;

    @Autowired
    ValidationManager vm;

    @Test
    public void test_success() {

        //Arrange
        Person person = new Person(22, "pzdn2009");

        vm.setStopWhenFailed(true);

        var ret = vm
                .add(basicValidation, person)
                .add(personValidation, person)
                .validate();

        assertEquals(ret.success(), true);
    }

}