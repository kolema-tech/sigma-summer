package com.sigma.sigmacore.aspects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-13:14
 * desc:
 **/
@RunWith(SpringRunner.class)
public class SigmaRetryAspectTest {

    @Autowired
    RetryerSample retryerSample;

    @Test
    public void around() throws Exception {
        retryerSample.getBaidu();
    }

}