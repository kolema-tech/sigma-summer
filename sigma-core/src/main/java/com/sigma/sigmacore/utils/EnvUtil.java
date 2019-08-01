package com.sigma.sigmacore.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/16-23:35
 * desc:
 **/
public class EnvUtil {

    private static EnvUtil envUtil;
    @Autowired
    Environment environment;

    public static String getCurrentProfile() {
        return envUtil.environment.getActiveProfiles()[0];
    }

    @PostConstruct
    public void init() {
        envUtil = this;
        envUtil.environment = this.environment;
    }
}
