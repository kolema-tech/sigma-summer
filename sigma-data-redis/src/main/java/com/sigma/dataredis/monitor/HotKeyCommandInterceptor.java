package com.sigma.dataredis.monitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Redis Connection 命令拦截器（JDK 动态代理 InvocationHandler）
 *
 * <p>拦截常见读写命令，提取 Key 并记录到 {@link HotKeyTracker}。</p>
 */
@Slf4j
@RequiredArgsConstructor
public class HotKeyCommandInterceptor implements InvocationHandler {

    /**
     * 需要追踪的 Redis 命令方法名（以读命令为主，读热点是核心场景）
     */
    private static final Set<String> TRACKED_COMMANDS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            // String 读命令
            "get", "getEx", "getRange", "getDel", "getSet", "mGet",
            // Hash 读命令
            "hGet", "hMGet", "hGetAll", "hVals", "hKeys", "hLen",
            // List 读命令
            "lIndex", "lLen", "lRange",
            // Set 读命令
            "sMembers", "sIsMember", "sCard", "sRandMember",
            // ZSet 读命令
            "zRange", "zRangeByScore", "zRangeByLex", "zRangeWithScores", "zCard", "zScore", "zCount",
            // Key 查询命令
            "exists", "ttl", "pTtl", "type",
            // String 写命令（写热点同样关键）
            "set", "setEx", "pSetEx", "setNX", "mSet", "mSetNX", "incr", "incrBy", "decr", "decrBy"
    )));

    private final Object delegate;
    private final HotKeyTracker tracker;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null && args.length > 0 && TRACKED_COMMANDS.contains(method.getName())) {
            extractAndRecord(args[0]);
        }
        try {
            return method.invoke(delegate, args);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    /**
     * 从命令参数中提取 key 并记录
     * Redis 命令的 key 参数为 byte[] 或 byte[][]（如 mGet/mSet）
     */
    private void extractAndRecord(Object arg) {
        if (arg instanceof byte[]) {
            byte[] key = (byte[]) arg;
            if (key.length > 0) {
                tracker.record(new String(key, StandardCharsets.UTF_8));
            }
        } else if (arg instanceof byte[][]) {
            for (byte[] key : (byte[][]) arg) {
                if (key != null && key.length > 0) {
                    tracker.record(new String(key, StandardCharsets.UTF_8));
                }
            }
        }
    }
}
