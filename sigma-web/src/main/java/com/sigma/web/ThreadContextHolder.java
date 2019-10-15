package com.sigma.web;

import com.sigma.sigmacore.web.SigmaRequestHeader;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.sigma.web.SigmaWebConstants.*;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/8-12:52
 * desc:
 **/
public class ThreadContextHolder {

    private static final ThreadLocal<Map<String, Object>> TH_CONTEXT_HOLDER = new ThreadLocal<>();

    private static Map<String, Class<?>> PATH_DICT = null;

    static {
        PATH_DICT = new HashMap<>();
        registerPath(SIGMA_HEADER, SigmaRequestHeader.class);
    }

    public static Map<String, Class<?>> getPathDict() {
        return PATH_DICT;
    }


    public static void registerPath(String path, Class classType) {
        PATH_DICT.put(path, classType);
    }


    public static <T> T getValue(String key, Class<T> classType) {

        Map<String, Object> map = TH_CONTEXT_HOLDER.get();
        if (map == null) {
            map = new HashMap<>(16);
            TH_CONTEXT_HOLDER.set(map);
        }

        return castObjectToT(map.get(key), classType);
    }


    public static void setValue(String key, Object value) {

        Map<String, Object> map = TH_CONTEXT_HOLDER.get();
        if (map == null) {
            map = new HashMap<>(16);
            TH_CONTEXT_HOLDER.set(map);
        }

        map.put(key, value);
    }


    public static void setValueMap(String key, Map value, Class<?> beanClass) {

        try {
            Object obj = mapToObject(value, beanClass);
            if (obj != null) {
                setValue(key, obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T castObjectToT(Object o, Class<T> t) {
        if (t != null) {
            return o == null ? null : (T) o;
        }
        return null;
    }

    private static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

    public static SigmaRequestHeader getHeader() {
        return getValue(SIGMA_HEADER, SigmaRequestHeader.class);
    }

    public static String getSigmaUserId() {
        return getValue(SIGMA_USER_ID, String.class);
    }

    public static String getSigmaUserName() {
        return getValue(SIGMA_USER_NAME, String.class);
    }
}
