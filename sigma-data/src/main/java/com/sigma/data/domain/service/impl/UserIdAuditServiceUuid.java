package com.sigma.data.domain.service.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/19-15:51
 * desc:
 **/
@Configuration
public class UserIdAuditServiceUuid implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("system");
    }
}
