package com.sigma.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/11-13:30
 * desc: 工廠模板
 **/
@Component
@Slf4j
public class FallbackFactoryTemplate<T> implements FallbackFactory<T> {

    @Override
    public T create(Throwable throwable) {

        log.error("fallback reason was: {} ", throwable.getMessage());

        return null;
    }
}
