package com.sigma.dataredis.monitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.*;

import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 带 HotKey 追踪能力的 RedisConnectionFactory 包装器
 *
 * <p>通过 JDK 动态代理拦截每个 {@link RedisConnection}，
 * 将 Key 访问信息透明地上报到 {@link HotKeyTracker}，
 * 对业务代码完全无侵入。</p>
 */
@Slf4j
@RequiredArgsConstructor
public class HotKeyTrackingConnectionFactory implements RedisConnectionFactory {

    private final RedisConnectionFactory delegate;
    private final HotKeyTracker tracker;

    @Override
    public RedisConnection getConnection() {
        RedisConnection connection = delegate.getConnection();
        return proxyConnection(connection, RedisConnection.class);
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return delegate.getClusterConnection();
    }

    @Override
    public boolean getConvertPipelineAndTxResults() {
        return delegate.getConvertPipelineAndTxResults();
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return delegate.getSentinelConnection();
    }

    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return delegate.translateExceptionIfPossible(ex);
    }

    /**
     * 为 RedisConnection 创建 JDK 动态代理，植入 HotKey 拦截逻辑
     */
    @SuppressWarnings("unchecked")
    private <T extends RedisConnection> T proxyConnection(T connection, Class<T> primaryInterface) {
        Set<Class<?>> allInterfaces = collectAllInterfaces(connection.getClass());
        allInterfaces.add(primaryInterface);

        return (T) Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                allInterfaces.toArray(new Class[0]),
                new HotKeyCommandInterceptor(connection, tracker)
        );
    }

    /**
     * 递归收集类及其父类实现的所有接口
     */
    private Set<Class<?>> collectAllInterfaces(Class<?> clazz) {
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        while (clazz != null && clazz != Object.class) {
            for (Class<?> iface : clazz.getInterfaces()) {
                interfaces.add(iface);
                interfaces.addAll(collectAllInterfaces(iface));
            }
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }
}
