package com.sigma.dataredis.monitor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HotKey 监控配置项
 *
 * <pre>
 * sigma:
 *   redis:
 *     hotkey:
 *       enabled: true
 *       threshold: 100          # 窗口内访问次数超过此值判定为 HotKey
 *       top-n: 10               # 每次上报 Top-N 个 Key
 *       window-seconds: 60      # 滚动窗口大小（秒）
 *       log-interval-seconds: 60 # 日志打印间隔（秒），建议与 window-seconds 保持一致
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "sigma.redis.hotkey")
public class HotKeyMonitorProperties {

    /** 是否启用 HotKey 监控，默认开启 */
    private boolean enabled = true;

    /** 窗口内访问次数超过此值判定为 HotKey，默认 100 */
    private long threshold = 100;

    /** 上报 Top-N 个高频 Key，默认 10 */
    private int topN = 10;

    /** 统计窗口大小（秒），窗口到期后计数重置，默认 60s */
    private int windowSeconds = 60;

    /** 日志打印间隔（秒），默认 60s */
    private int logIntervalSeconds = 60;
}
