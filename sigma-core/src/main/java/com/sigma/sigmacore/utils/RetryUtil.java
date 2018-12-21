package com.sigma.sigmacore.utils;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-10:43
 * desc: 重試工具
 **/
@Slf4j
public class RetryUtil {

    /**
     * 執行retry
     * 默認三次，固定兩次
     *
     * @param body body
     * @param <T>  泛型參數
     * @return 執行結果
     */
    public static <T> T retry(Supplier<T> body) {
        return retry(body, null);
    }

    /**
     * 執行retry
     * 自定義配置
     *
     * @param body        body
     * @param retryConfig 自定義配置
     * @param <T>         泛型參數
     * @return 執行結果
     */
    public static <T> T retry(Supplier<T> body, RetryConfig retryConfig) {
        return retry(body, retryConfig, null);
    }

    /**
     * 執行retry
     *
     * @param body        body
     * @param retryConfig 自定義配置
     * @param recover     重試之後仍失敗的邏輯
     * @param <T>         泛型參數
     * @return 執行結果
     */
    public static <T> T retry(Supplier<T> body, RetryConfig retryConfig, Function<Exception, T> recover) {
        return retry(body, retryConfig, recover, null);
    }

    /**
     * 執行retry
     *
     * @param body        body
     * @param retryConfig 自定義配置
     * @param recover     重試之後仍失敗的邏輯
     * @param retryTracer 每次重試跟蹤
     * @param <T>         泛型參數
     * @return 執行結果
     */
    public static <T> T retry(Supplier<T> body, RetryConfig retryConfig, Function<Exception, T> recover, Consumer<Attempt> retryTracer) {
        return retry(body, retryConfig, recover, retryTracer, null);
    }

    /**
     * 執行retry
     *
     * @param body                   body
     * @param retryConfig            自定義配置
     * @param recover                重試之後仍失敗的邏輯
     * @param retryTracer            每次重試跟蹤
     * @param retryerBuilderConsumer 擴展重試
     * @param <T>                    泛型參數
     * @return 執行結果
     */
    public static <T> T retry(Supplier<T> body, RetryConfig retryConfig, Function<Exception, T> recover,
                              Consumer<Attempt> retryTracer, Consumer<RetryerBuilder> retryerBuilderConsumer) {

        if (retryConfig == null) {
            retryConfig = RetryConfig.DEFAULT;
        }

        var retryerBuilder = RetryerBuilder.<T>newBuilder()
                .retryIfResult(Predicates.equalTo(null))
                .retryIfException()
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(retryConfig.getTotalTimeout(), TimeUnit.SECONDS))
                .withWaitStrategy(retryConfig.getWaitStrategy())
                .withStopStrategy(StopStrategies.stopAfterAttempt(retryConfig.getRetryCount()))
                .withRetryListener(new RetryListener() {
                    @Override
                    public <T> void onRetry(Attempt<T> attempt) {

                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append("重試次數：" + attempt.getAttemptNumber());
                        stringBuilder.append(",延時=" + attempt.getDelaySinceFirstAttempt());
                        stringBuilder.append(",異常=" + attempt.hasException());
                        stringBuilder.append(",結果=" + attempt.hasResult());

                        if (attempt.hasException()) {

                            try {
                                stringBuilder.append(",causeBy=" + attempt.getExceptionCause().toString() + attempt.get());
                            } catch (ExecutionException e) {
                                stringBuilder.append(e.toString());
                            }


                        } else {
                            stringBuilder.append(",result=" + attempt.getResult());
                        }

                        log.info(stringBuilder.toString());

                        if (retryTracer != null) {
                            retryTracer.accept(attempt);
                        }

                    }
                });

        if (retryerBuilderConsumer != null) {
            retryerBuilderConsumer.accept(retryerBuilder);
        }

        T obj = null;

        try {

            log.debug("retry 開始");
            obj = retryerBuilder.build().call(() -> body.get());
        } catch (RetryException ex) {
            if (recover != null) {
                return recover.apply(ex);
            } else {
                log.error("重試異常", ex);
            }
        } catch (ExecutionException ex) {
            if (recover != null) {
                return recover.apply(ex);
            } else {
                log.error("重試異常", ex);
            }
        } finally {
            log.debug("retry 結束");
        }

        return obj;
    }
}
