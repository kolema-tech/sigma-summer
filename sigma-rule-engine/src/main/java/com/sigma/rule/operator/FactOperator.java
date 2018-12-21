package com.sigma.rule.operator;

import com.sigma.rule.converter.BooleanConverter;
import com.sigma.rule.converter.DateConverter;
import com.sigma.rule.converter.ListConverter;
import com.sigma.rule.converter.NumberConverter;
import com.sigma.rule.exception.FactDataTypeNotMatchException;
import com.sigma.rule.exception.FactOpNotSupportException;
import lombok.experimental.var;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/26-19:09
 * desc: 事實運算符
 **/
public enum FactOperator {
    /**
     * 等于
     * type: *
     */
    EQ,

    /**
     * 不等於
     * type: *
     */
    NOT_EQ,

    /**
     * 大於
     * type: number
     */
    GT,

    /**
     * 大於等於
     * type: number
     */
    GT_INC,

    /**
     * 小於
     * type: number
     */
    LT,

    /**
     * 小於等於
     * type: number
     */
    LT_INC,

    /**
     * 包含
     * type: List
     */
    IN,

    /**
     * type: List
     */
    NOT_IN,

    /**
     * 之後
     * type: Date
     */
    AFTER,

    /**
     * 之前
     * type: Date
     */
    BEFORE,

    /**
     * 在之间
     * type: Date
     */
    BETWEEN_DATE,

    /**
     * 对于传递进来的日期数组，存在一个在之间
     */
    EXIST_BETWEEN_DATE,

    /**
     * 真
     * type: boolean
     */
    TRUE,

    /**
     * 假：
     * type: boolean
     */
    FALSE,

    /**
     * 存在交集
     * type: list
     */
    INTERSECTION,

    /**
     * 不存在交集
     */
    NO_INTERSECTION,

    /**
     * 正则表达式
     */
    REGEX;

    public static Boolean eq(Object object1, Object object2) {
        return Objects.equals(object1, object2);
    }

    public static Boolean notEq(Object object1, Object object2) {
        return !eq(object1, object2);
    }

    public static Boolean number(Object object1, Object object2, FactOperator factOperator) throws FactOpNotSupportException {
        NumberConverter numberConverter = new NumberConverter();
        double d1 = numberConverter.convert(object1);
        double d2 = numberConverter.convert(object2);
        switch (factOperator) {
            case GT:
                return d1 > d2;
            case GT_INC:
                return d1 >= d2;
            case LT:
                return d1 < d2;
            case LT_INC:
                return d1 <= d2;
            default:
                throw new FactOpNotSupportException(factOperator + "不支持");
        }
    }

    public static Boolean bool(Object object1, FactOperator factOperator) throws FactOpNotSupportException {
        BooleanConverter booleanConverter = new BooleanConverter();
        boolean b1 = booleanConverter.convert(object1);
        switch (factOperator) {
            case TRUE:
                return b1;
            case FALSE:
                return !b1;
            default:
                throw new FactOpNotSupportException(factOperator + "不支持");
        }
    }

    public static Boolean date(Object object1, Object object2, FactOperator factOperator) throws FactOpNotSupportException {
        DateConverter dateConverter = new DateConverter();
        LocalDateTime d1 = dateConverter.convert(object1);
        LocalDateTime d2 = dateConverter.convert(object2);
        switch (factOperator) {
            case BEFORE:
                return d1.isBefore(d2);
            case AFTER:
                return d1.isAfter(d2);
            default:
                throw new FactOpNotSupportException(factOperator + "不支持");
        }
    }

    public static Boolean list(Object object1, Object object2, FactOperator factOperator) {
        ListConverter listConverter = new ListConverter();
        List<Object> d1 = listConverter.convert(object1);
        List<Object> d2 = listConverter.convert(object2);

        switch (factOperator) {
            case IN:
                return d2.containsAll(d1);
            case NOT_IN:
                return !d2.containsAll(d1);
            default:
                throw new FactOpNotSupportException(factOperator + "不支持");
        }
    }

    public static boolean intersection(Object simpleValue, Object value) {
        ListConverter listConverter = new ListConverter();
        List<Object> d1 = listConverter.convert(simpleValue);
        List<Object> d2 = listConverter.convert(value);

        for (Object object : d1) {
            for (Object obj2 : d2) {
                if (obj2.equals(object)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean betweenDate(Object simpleValue, Object value) {
        ListConverter listConverter = new ListConverter();
        List<Object> d1 = listConverter.convert(value);
        if (d1.size() != 2) {
            throw new FactDataTypeNotMatchException(value.toString(), "betweenDate 长度应该为2");
        }

        DateConverter dateConverter = new DateConverter();
        LocalDateTime start = dateConverter.convert(d1.get(0));
        LocalDateTime end = dateConverter.convert(d1.get(1));
        var val = dateConverter.convert(simpleValue);

        return start.isBefore(val) && end.isAfter(val);
    }

    public static boolean existBetweenDate(Object simpleValues, Object factValue) {

        var dateConverter = new DateConverter();
        var listConverter = new ListConverter();

        List<Object> dateRange = listConverter.convert(factValue);
        if (dateRange.size() != 2) {
            throw new FactDataTypeNotMatchException(factValue.toString(), "existBetweenDate 长度应该为2");
        }

        LocalDateTime start = dateConverter.convert(dateRange.get(0));
        LocalDateTime end = dateConverter.convert(dateRange.get(1));

        List<Object> inputDates = listConverter.convert(simpleValues);
        for (Object inputDate : inputDates) {
            var date = dateConverter.convert(inputDate);
            if (start.isBefore(date) && end.isAfter(date)) {
                return true;
            }
        }

        return false;
    }

    public static boolean regex(Object simpleValue, Object value) {
        return Pattern.matches(value.toString(), simpleValue.toString());
    }
}
