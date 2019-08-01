package com.sigma.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.sigma.logging.model.SigmaLogEntity;
import lombok.experimental.var;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-11:15
 * desc: 基礎日誌
 **/
@Deprecated
public class SigmaLoggingAppender extends BaseSigmaLoggingAppender<SigmaLogEntity> {

    /**
     * 日誌轉換
     *
     * @param loggingEvent 日誌對象
     * @return 構造對象
     */
    @Override
    protected SigmaLogEntity convert(ILoggingEvent loggingEvent) {

        if (loggingEvent.getLevel().equals(Level.TRACE)) {
            return null;
        }

        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var sigmaLogEntity = new SigmaLogEntity();
        sigmaLogEntity.setSystemCode(getSystemCode());
        sigmaLogEntity.setSource(getSourceCode());
        sigmaLogEntity.setCreateTime(simpleDateFormat.format(LocalDateTime.now()));
        sigmaLogEntity.setMessage(loggingEvent.getMessage());
        sigmaLogEntity.setThreadId(Thread.currentThread().getId());
        sigmaLogEntity.setMachineName(machineName);
        sigmaLogEntity.setDetail(loggingEvent.getFormattedMessage());
        if (loggingEvent.getThrowableProxy() != null && loggingEvent.getThrowableProxy().getStackTraceElementProxyArray() != null) {
            var line = System.getProperty("line.separator");
            var msg = loggingEvent.getThrowableProxy().getClassName() + line;
            for (StackTraceElementProxy stackTraceElementProxy : loggingEvent.getThrowableProxy().getStackTraceElementProxyArray()) {
                msg += stackTraceElementProxy.toString() + line;
            }

            sigmaLogEntity.setDetail(msg);
        }
        sigmaLogEntity.setIpAddress(ipAddress);
        sigmaLogEntity.setAppDomainName("");
        sigmaLogEntity.setProcessId(Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]));
        sigmaLogEntity.setProcessName(ManagementFactory.getRuntimeMXBean().getName());

        return sigmaLogEntity;
    }
}
