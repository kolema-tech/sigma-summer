package com.sigma.sigmacore.service;

import com.sigma.sigmacore.aspects.SigmaMetrics;
import org.springframework.stereotype.Service;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-15:28
 * desc:
 **/
@Service
public class HelloWorldService {

    @SigmaMetrics
    public void helloWorldApi() throws InterruptedException {
        Thread.sleep(2000);
    }

}
