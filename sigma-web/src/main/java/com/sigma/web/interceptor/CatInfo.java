package com.sigma.web.interceptor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.sigma.sigmacore.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/6-14:42
 * desc: 請求監控
 **/
@Getter
@Setter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatInfo {

    private static ThreadLocal<CatInfo> catInfoThreadLocal = new ThreadLocal<>();

    /**
     * 類名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 請求參數
     */
    private Object[] args;

    /**
     * 請求響應
     */
    private Object response;

    /**
     * 執行時間
     */
    private ExecutedTimeMetrics controllerMetric;

    /**
     * API執行時間
     */
    private List<ExecutedTimeMetrics> apiRequestMetrics;

    /**
     * 異常
     */
    private Exception exception;

    /**
     * 构造器
     */
    public CatInfo() {
        controllerMetric = new ExecutedTimeMetrics();
        apiRequestMetrics = Lists.newArrayList();
    }

    /**
     * 按照類名和方法名初始化
     *
     * @param className  類名
     * @param methodName 方法名
     */
    static void init(String className, String methodName) {
        var catInfo = new CatInfo();
        catInfo.setClassName(className);
        catInfo.setMethodName(methodName);
        catInfoThreadLocal.set(catInfo);
    }

    static void logAndRemove() {

        var info = get();
        var message = String.format("Time cost: %d %s->%s", info.getControllerMetric().getDuration(), info.getClassName(), info.getMethodName());
        try {
            log.info(message.concat("\n" + JsonUtils.serialize(catInfoThreadLocal.get(), true)));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("logAndRemove序列化catInfo出錯", e);
        }

        catInfoThreadLocal.remove();
    }

    /**
     * 設置API請求性能
     *
     * @param executedTimeMetrics 性能計數
     */
    public static void addApiRequestMetrics(ExecutedTimeMetrics executedTimeMetrics) {
        get().getApiRequestMetrics().add(executedTimeMetrics);
    }

    /**
     * 添加控制器执行时间
     *
     * @param executedTimeMetrics 性能計數
     */
    public static void addControllerExecutedTime(ExecutedTimeMetrics executedTimeMetrics) {
        get().setControllerMetric(executedTimeMetrics);
    }

    /**
     * 添加参数
     *
     * @param args 請求參數
     */
    public static void addArgs(Object[] args) {
        get().setArgs(args);
    }

    /**
     * 添加响应
     *
     * @param response 響應結果
     */
    public static void addResponse(Object response) {
        get().setResponse(response);
    }

    /**
     * 添加異常
     *
     * @param exception 異常
     */
    public static void addException(Exception exception) {
        get().setException(exception);
    }

    private static CatInfo get() {

        if (catInfoThreadLocal.get() == null) {
            catInfoThreadLocal.set(new CatInfo());
        }

        return catInfoThreadLocal.get();
    }
}
