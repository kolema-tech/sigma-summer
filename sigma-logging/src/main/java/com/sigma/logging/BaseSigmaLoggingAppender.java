package com.sigma.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.sigma.sigmacore.utils.MachineUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/7-10:52
 * desc: 異步日誌Appender基類.
 **/
public abstract class BaseSigmaLoggingAppender<T> extends UnsynchronizedAppenderBase<ILoggingEvent> {

    /**
     * 機器名
     */
    protected static String machineName;

    /**
     * IP地址
     */
    @Getter
    protected String ipAddress;

    /**
     * 系統代碼
     */
    @Getter
    @Setter
    private String systemCode = null;
    /**
     * 渠道類型
     */
    @Getter
    @Setter
    private String sourceCode = null;
    /**
     * 日誌地址
     */
    @Getter
    @Setter
    private String logServiceUrl;

    public BaseSigmaLoggingAppender() {
        this.ipAddress = MachineUtil.getServerIp();
        machineName = MachineUtil.getMachineName();
    }

    protected String getUrlByLevel(Level level) {

        if (level.levelStr.equals(Level.TRACE.levelStr)) {
            return getLogServiceUrl().concat("/xml");
        }

        if (level.levelStr.equals(Level.DEBUG.levelStr) || level.levelStr.equals(Level.INFO.levelStr)) {
            return getLogServiceUrl().concat("/debug");
        }

        return getLogServiceUrl().concat("/error");
    }

    /**
     * 執行日誌處理
     *
     * @param loggingEvent 日誌對象
     */
    @Override
    protected void append(ILoggingEvent loggingEvent) {
        send(convert(loggingEvent), loggingEvent.getLevel());
    }

    /**
     * 日誌轉換
     *
     * @param loggingEvent 日誌對象
     * @return 泛型T
     */
    protected abstract T convert(ILoggingEvent loggingEvent);

    /**
     * 日誌發送
     *
     * @param entity 對象
     */
    protected void send(T entity, Level level) {
        if (null == entity) {
            return;
        }

        LoggingPool.getInstance().push(new LoggingPool.LogObject(getUrlByLevel(level), entity));
    }
}
