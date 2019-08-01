package com.sigma.logging;

import com.sigma.sigmacore.utils.MachineUtil;
import lombok.experimental.var;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/10/21-20:28
 * desc:
 **/
@Deprecated
public class SigmaPerformanceUtil {

    public static void send(PerformanceLogConfig performanceLogConfig, String message, String detail, long duration) {

        var performanceEntity = new SigmaPerformanceEntity();

        performanceEntity.setGroup("");
        performanceEntity.setThreadId(String.valueOf(Thread.currentThread().getId()));
        performanceEntity.setThreadName(String.valueOf(Thread.currentThread().getName()));
        performanceEntity.setSystemCode(performanceLogConfig.getSystemCode());
        performanceEntity.setSource(performanceLogConfig.getSource());
        performanceEntity.setMessage(message);
        performanceEntity.setMachineName(MachineUtil.getMachineName());
        performanceEntity.setAppDomainName("");
        performanceEntity.setProcessId(0);
        performanceEntity.setProcessName("");
        performanceEntity.setDetail(detail);
        performanceEntity.setCreateTime(new Date());
        performanceEntity.setIpAddress(MachineUtil.getServerIp());
        performanceEntity.setRemark("");
        performanceEntity.setRq("");
        performanceEntity.setRs("");

        performanceEntity.setDuration(duration);

        var restTemplate = new RestTemplate();
        restTemplate.postForEntity(performanceLogConfig.getUrl(), performanceEntity, String.class);
    }

}
