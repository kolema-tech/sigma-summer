package com.sigma.sigmacore.utils;

import java.lang.reflect.Method;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/8-12:38
 * desc: Enum Util
 **/
public class EnumUtil {
    /**
     * 約定：
     * code：枚舉的第一個參數
     * Code表示枚舉的代碼
     * getCode獲取代碼值
     */
    public static final String GET_CODE_NAME = "getCode";

    /**
     * 獲取第 i 個枚舉
     *
     * @param clazz   枚舉類別
     * @param ordinal 順序，從0開始
     * @param <T>     類型
     * @return 值
     */
    public static <T extends Enum<T>> T indexOf(Class<T> clazz, int ordinal) {
        return (T) clazz.getEnumConstants()[ordinal];
    }

    /**
     * 根據名稱獲取枚舉值
     *
     * @param clazz 枚舉類別
     * @param name  名稱
     * @param <T>   類型
     * @return 值
     */
    public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name) {

        return (T) Enum.valueOf(clazz, name);
    }

    /**
     * 根據代碼獲取枚舉值
     *
     * @param clazz 類別
     * @param code  代碼
     * @param <T>   類型
     * @return 值
     */
    public static <T extends Enum<T>> T codeOf(Class<T> clazz, Object code) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(GET_CODE_NAME);
            Object typeCodeVal = null;
            for (T entity : arr) {
                typeCodeVal = targetMethod.invoke(entity);
                if (code.equals(typeCodeVal)) {
                    result = entity;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
