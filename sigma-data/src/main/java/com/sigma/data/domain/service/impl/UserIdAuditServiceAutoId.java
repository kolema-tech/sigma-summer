package com.sigma.data.domain.service.impl;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/19-15:51
 * desc:
 **/
public class UserIdAuditServiceAutoId implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(1L);
    }
}
