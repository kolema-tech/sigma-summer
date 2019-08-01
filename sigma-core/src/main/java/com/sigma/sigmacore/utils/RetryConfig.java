package com.sigma.sigmacore.utils;

import com.github.rholder.retry.WaitStrategies;
import com.github.rholder.retry.WaitStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-10:51
 * desc: SigmaRetry 配置
 **/
@Getter
@Setter
@AllArgsConstructor
public class RetryConfig {
    /**
     * 默認
     */
    public static final RetryConfig DEFAULT = new RetryConfig(3, WaitStrategies.fixedWait(2, TimeUnit.SECONDS), 120);

    /**
     * 重試次數
     * 默認為3
     */
    private int retryCount;

    /**
     * 重試策略
     * 默認為固定等待2s
     */
    private WaitStrategy waitStrategy;

    /**
     * 總共的超時時間
     * 默認120s
     */
    private int totalTimeout;

    public RetryConfig() {
        retryCount = 3;
        waitStrategy = WaitStrategies.fixedWait(2, TimeUnit.SECONDS);
        totalTimeout = 120;
    }
}
