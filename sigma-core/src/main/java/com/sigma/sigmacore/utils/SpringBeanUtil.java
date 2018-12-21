package com.sigma.sigmacore.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/4-10:01
 * desc: Bean工具類
 **/
@Component
public class SpringBeanUtil implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 獲取Bean
     *
     * @param name 名稱
     * @return 對象
     */
    public static Object getBean(String name) {

        Assert.hasLength(name, "beanName 不能為空！");

        return getApplicationContext().getBean(name);
    }

    /**
     * 獲取指定類型的bean
     *
     * @param clazz 類型
     * @param <T>   泛型
     * @return 對象
     */
    public static <T> T getBean(Class<T> clazz) {

        Assert.notNull(clazz, "clazz 不能為空！");

        return getApplicationContext().getBean(clazz);
    }

    /**
     * 名稱和類別獲取Bean
     *
     * @param name  名稱
     * @param clazz 類別
     * @param <T>   泛型
     * @return 對象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        Assert.hasLength(name, "beanName 不能為空！");
        Assert.notNull(clazz, "clazz 不能為空！");

        return getApplicationContext().getBean(name, clazz);
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }
}
