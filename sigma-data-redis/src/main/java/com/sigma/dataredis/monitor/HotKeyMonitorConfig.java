package com.sigma.dataredis.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * HotKey 监控自动配置
 *
 * <p>通过 {@link BeanPostProcessor} 自动包装所有 {@link RedisConnectionFactory} Bean，
 * 植入透明的命令拦截层，无需修改任何业务代码。</p>
 *
 * <p>定时任务每隔 {@code sigma.redis.hotkey.log-interval-seconds} 秒打印一次热 Key 报告，
 * 并在 {@code sigma.redis.hotkey.window-seconds} 秒后重置计数窗口。</p>
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(HotKeyMonitorProperties.class)
@ConditionalOnProperty(prefix = "sigma.redis.hotkey", name = "enabled", havingValue = "true", matchIfMissing = true)
public class HotKeyMonitorConfig {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public HotKeyTracker hotKeyTracker(HotKeyMonitorProperties props) {
        log.info("[HotKey Monitor] enabled, threshold={}, topN={}, windowSeconds={}",
                props.getThreshold(), props.getTopN(), props.getWindowSeconds());
        return new HotKeyTracker(props.getThreshold(), props.getTopN());
    }

    /**
     * BeanPostProcessor：包装 RedisConnectionFactory，无侵入植入追踪逻辑
     */
    @Bean
    public BeanPostProcessor hotKeyConnectionFactoryWrapper(HotKeyTracker tracker) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RedisConnectionFactory
                        && !(bean instanceof HotKeyTrackingConnectionFactory)) {
                    log.info("[HotKey Monitor] Wrapping RedisConnectionFactory bean '{}'", beanName);
                    return new HotKeyTrackingConnectionFactory((RedisConnectionFactory) bean, tracker);
                }
                return bean;
            }
        };
    }

    /**
     * 定时打印 HotKey 报告并重置统计窗口。
     * 间隔通过 {@code sigma.redis.hotkey.window-seconds} 控制，单位毫秒。
     *
     * 注意：@Scheduled fixedDelayString 从 Properties 读取，单位为秒，转换为毫秒字符串。
     */
    @Bean
    public HotKeyReporter hotKeyReporter(HotKeyTracker tracker, HotKeyMonitorProperties props) {
        return new HotKeyReporter(tracker, props);
    }

    /**
     * HotKey 定时上报器（内部类，避免额外文件）
     */
    public static class HotKeyReporter {

        private final HotKeyTracker tracker;
        private final HotKeyMonitorProperties props;

        public HotKeyReporter(HotKeyTracker tracker, HotKeyMonitorProperties props) {
            this.tracker = tracker;
            this.props = props;
        }

        /**
         * 每隔 window-seconds 打印热 Key 报告并重置窗口
         * 默认 60000 ms = 60s，可通过配置调整
         */
        @Scheduled(fixedDelayString = "#{${sigma.redis.hotkey.window-seconds:60} * 1000}")
        public void report() {
            String windowStartStr = LocalDateTime
                    .ofInstant(Instant.ofEpochMilli(tracker.getWindowStart()), ZoneId.systemDefault())
                    .format(FMT);

            List<HotKeyStats> hotKeys = tracker.getHotKeysAboveThreshold();
            List<HotKeyStats> topKeys = tracker.getTopNKeys();

            if (hotKeys.isEmpty()) {
                log.info("[HotKey Monitor] No hotkeys detected. Window={}, TrackedKeys={}, Top1={}",
                        windowStartStr,
                        tracker.getTrackedKeyCount(),
                        topKeys.isEmpty() ? "none" : topKeys.get(0).getKey() + "(" + topKeys.get(0).getCount() + ")");
            } else {
                log.warn("[HotKey Monitor] {} hotkey(s) detected! Threshold={}, Window={}",
                        hotKeys.size(), props.getThreshold(), windowStartStr);
                for (int i = 0; i < hotKeys.size(); i++) {
                    HotKeyStats s = hotKeys.get(i);
                    log.warn("[HotKey Monitor]   #{} key='{}' count={}", i + 1, s.getKey(), s.getCount());
                }
            }

            // 重置窗口计数
            tracker.resetWindow();
        }
    }
}
