package com.sigma.dataredis.monitor;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * HotKey 访问追踪器
 *
 * <p>使用滚动窗口（Tumbling Window）统计 Redis Key 的访问频次。
 * 每次调用 {@link #resetWindow()} 后计数清零、窗口刷新。</p>
 */
@Slf4j
public class HotKeyTracker {

    /** 单个 Key 被判定为 HotKey 的最低访问次数阈值 */
    private final long threshold;

    /** 最多返回的 Top-N 条数 */
    private final int topN;

    /** Key -> 访问计数 */
    private final ConcurrentHashMap<String, LongAdder> counts = new ConcurrentHashMap<>(256);

    /** 当前窗口起始时间（毫秒） */
    private volatile long windowStart = System.currentTimeMillis();

    public HotKeyTracker(long threshold, int topN) {
        this.threshold = threshold;
        this.topN = topN;
    }

    /**
     * 记录一次 Key 访问
     */
    public void record(String key) {
        counts.computeIfAbsent(key, k -> new LongAdder()).increment();
    }

    /**
     * 获取超过阈值的 HotKey 列表（降序）
     */
    public List<HotKeyStats> getHotKeysAboveThreshold() {
        long ws = windowStart;
        return counts.entrySet().stream()
                .filter(e -> e.getValue().sum() >= threshold)
                .map(e -> new HotKeyStats(e.getKey(), e.getValue().sum(), ws))
                .sorted(Comparator.comparingLong(HotKeyStats::getCount).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 获取访问频次 Top-N 的 Key 列表（无论是否超阈值）
     */
    public List<HotKeyStats> getTopNKeys() {
        long ws = windowStart;
        return counts.entrySet().stream()
                .map(e -> new HotKeyStats(e.getKey(), e.getValue().sum(), ws))
                .sorted(Comparator.comparingLong(HotKeyStats::getCount).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

    /**
     * 获取当前追踪的 Key 总数
     */
    public int getTrackedKeyCount() {
        return counts.size();
    }

    /**
     * 滚动窗口：清空计数，刷新窗口起始时间
     */
    public void resetWindow() {
        counts.clear();
        windowStart = System.currentTimeMillis();
        log.debug("HotKeyTracker window reset");
    }

    public long getThreshold() {
        return threshold;
    }

    public long getWindowStart() {
        return windowStart;
    }
}
