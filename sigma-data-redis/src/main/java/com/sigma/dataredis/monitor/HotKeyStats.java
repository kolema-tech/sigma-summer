package com.sigma.dataredis.monitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HotKey 统计数据
 */
@Getter
@AllArgsConstructor
public class HotKeyStats {

    /**
     * Redis Key
     */
    private final String key;

    /**
     * 当前窗口内的访问次数
     */
    private final long count;

    /**
     * 统计窗口开始时间（毫秒时间戳）
     */
    private final long windowStart;
}
