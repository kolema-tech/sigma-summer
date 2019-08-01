package com.sigma.scheduler.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author pzdn
 * cron表达式更新操作
 */
public class CronUpdateEvent extends ApplicationEvent {

    public CronUpdateEvent(Object source) {
        super(source);
    }
}
