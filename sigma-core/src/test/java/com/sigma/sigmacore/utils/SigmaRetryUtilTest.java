package com.sigma.sigmacore.utils;

import com.sigma.sigmacore.Person;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-11:31
 * desc:
 **/
@Slf4j
public class SigmaRetryUtilTest {

    @Test
    public void retry() {

        var person = RetryUtil.retry(() -> {
            System.out.println("safd");
            return new Person();
        });

        Assert.notNull(person, "不為空！");
    }

    @Test
    public void retry1() {

        var person = RetryUtil.retry(() -> {
            System.out.println("safd");
            return (Person) null;
        });

        Assert.isNull(person, "為空！");
    }

    @Test
    public void retry2() {
//        String str = "daniu!";
//        var person = RetryUtil.retry(() -> {
//            System.out.println("safd");
//            System.out.println(str);
//            throw new SigmaException(new SigmaResponseHeader());
//        }, RetryConfig.DEFAULT, (ex) -> {
//            System.out.println("記錄異常");
//
//            return null;
//        }, tracer -> {
//            log.debug(tracer.toString());
//        }, retryerBuilder -> {
//        });
//
//        Assert.isNull(person, "為空！");
    }
}