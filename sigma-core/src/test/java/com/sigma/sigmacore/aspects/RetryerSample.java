package com.sigma.sigmacore.aspects;

import com.github.rholder.retry.WaitStrategies;
import com.sigma.sigmacore.utils.RetryConfig;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-13:42
 * desc:
 **/
@Component
public class RetryerSample {

    @SigmaRetry
    public void getBaidu() throws Exception {
        System.out.println("daniutest");
        throw new Exception();
    }
}

class CC extends RetryConfig {
    public CC() {
        super(4, WaitStrategies.fixedWait(2, TimeUnit.SECONDS), 20);
    }
}
