package com.sigma.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.sigma.logging.model.SigmaXmlLogEntity;
import com.sigma.sigmacore.utils.ZipUtils;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/8/6-11:12
 * desc:
 **/
@Slf4j
public class XmlLogAppender extends BaseSigmaLoggingAppender<SigmaXmlLogEntity> {

    @Override
    protected SigmaXmlLogEntity convert(ILoggingEvent loggingEvent) {

        DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (loggingEvent.getLevel().equals(Level.TRACE)
                && loggingEvent.getArgumentArray() != null
                && loggingEvent.getArgumentArray().length >= 1) {
            if (loggingEvent.getArgumentArray()[0] instanceof XmlLoggingModel) {

                var xmlLogEntity = new SigmaXmlLogEntity();
                xmlLogEntity.setSystemCode(getSystemCode());
                xmlLogEntity.setSource(getSourceCode());
                xmlLogEntity.setCreateTime(simpleDateFormat.format(LocalDateTime.now()));
                xmlLogEntity.setMessage(loggingEvent.getMessage());
                xmlLogEntity.setThreadId(Thread.currentThread().getId());
                xmlLogEntity.setMachineName(machineName);
                xmlLogEntity.setDetail(loggingEvent.getFormattedMessage());
                xmlLogEntity.setIpAddress(ipAddress);
                xmlLogEntity.setAppDomainName("");
                xmlLogEntity.setProcessId(Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]));
                xmlLogEntity.setProcessName(ManagementFactory.getRuntimeMXBean().getName());

                var event = (XmlLoggingModel) loggingEvent.getArgumentArray()[0];

                xmlLogEntity.setRq(ZipUtils.gzip(event.getRq()));
                xmlLogEntity.setRs(ZipUtils.gzip(event.getRs()));
                xmlLogEntity.setClassName(event.getClassName());
                xmlLogEntity.setMethodName(event.getMethodName());
                xmlLogEntity.setDetail(event.getDetail());

                return xmlLogEntity;
            }
        }

        return null;
    }
}
