package com.sigma.rabbit;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018-12-23
 * desc:
 **/
@Getter
@Setter
public class SigmaMessage implements Serializable {
    private String id;
    private LocalDateTime createDateTime;
    private LocalDateTime expiredDateTime;
    private String status;

    public boolean expired() {
        return null != expiredDateTime && LocalDateTime.now().isAfter(expiredDateTime);
    }
}
