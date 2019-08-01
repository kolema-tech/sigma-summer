package com.sigma.rule.fact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sigma.rule.FactMessage;
import com.sigma.rule.exception.FactDataTypeNotMatchException;
import com.sigma.rule.exception.FactKeyNotFoundException;
import com.sigma.rule.exception.FactOpNotSupportException;
import com.sigma.rule.exception.FactValueNullException;
import com.sigma.rule.operator.FactOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static java.text.MessageFormat.format;

/**
 * @author zhenpeng
 * 事实
 */
@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Fact implements FactMessage {

    /**
     * 格式
     */
    private static final String FORMAT = "Fact: name:{0}, op:{1},value:{2}, 参数: {3} , the result is {4}";

    /**
     * 名稱
     */
    private String name;

    /**
     * 運算符
     */
    private FactOperator operator;

    /**
     * 值
     */
    private Object value;

    /**
     * 消息
     */
    @JsonIgnore
    private String message;

    public Boolean match(SimpleFact simpleFact) {
        return match(simpleFact, null);
    }

    /**
     * 直接運行
     *
     * @param simpleFact 事實
     * @return 返回
     * @throws Exception
     */
    public Boolean match(SimpleFact simpleFact, Consumer<String> factMessageConsumer) {
        if (!simpleFact.containsKey(name)) {
            throw new FactKeyNotFoundException(name);
        }

        Object simpleValue = simpleFact.get(name);
        if (simpleValue == null) {
            throw new FactValueNullException(name);
        }

        var result = false;
        Exception exception = null;
        try {
            switch (operator) {
                case EQ:
                    result = FactOperator.eq(simpleValue, value);
                    break;
                case NOT_EQ:
                    result = FactOperator.notEq(simpleValue, value);
                    break;
                /**
                 * 數值型的
                 */
                case GT:
                case GT_INC:
                case LT:
                case LT_INC:
                    result = FactOperator.number(simpleValue, value, operator);
                    break;
                /**
                 * 布爾型
                 */
                case TRUE:
                case FALSE:
                    result = FactOperator.bool(simpleValue, operator);
                    break;
                /**
                 * 時間型
                 */
                case AFTER:
                case BEFORE:
                    result = FactOperator.date(simpleValue, value, operator);
                    break;
                case IN:
                case NOT_IN:
                    result = FactOperator.list(simpleValue, value, operator);
                    break;
                case INTERSECTION:
                    result = FactOperator.intersection(simpleValue, value);
                    break;
                case NO_INTERSECTION:
                    result = !FactOperator.intersection(simpleValue, value);
                    break;
                case BETWEEN_DATE:
                    result = FactOperator.betweenDate(simpleValue, value);
                    break;
                case EXIST_BETWEEN_DATE:
                    result = FactOperator.existBetweenDate(simpleValue, value);
                    break;
                case REGEX:
                    result = FactOperator.regex(simpleValue, value);
                    break;
                default:
                    throw new FactOpNotSupportException("不支持");
            }

            return result;
        } catch (FactOpNotSupportException ex) {
            exception = ex;
            throw ex;
        } catch (FactDataTypeNotMatchException ex) {
            exception = ex;
            throw ex;
        } finally {
            if (exception != null) {
                message = format(FORMAT, name, operator, value, simpleValue, exception);
            } else {
                message = format(FORMAT, name, operator, value, simpleValue, result);
            }

            if (factMessageConsumer != null) {
                factMessageConsumer.accept(message);
            }

            log.debug(message);
        }
    }
}
