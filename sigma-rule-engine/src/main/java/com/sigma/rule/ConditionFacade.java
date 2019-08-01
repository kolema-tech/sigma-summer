package com.sigma.rule;


import com.sigma.rule.exception.ConditionOperateNotSupportException;
import com.sigma.rule.fact.Fact;
import com.sigma.rule.fact.SimpleFact;
import com.sigma.sigmacore.utils.JsonUtils;

import java.io.IOException;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhenpeng
 * 條件外觀
 */
public class ConditionFacade {

    /**
     * 創建 Condition
     *
     * @param json json條件
     * @return 條件對象
     */
    public static Condition create(String json) throws IOException {
        return JsonUtils.deserialize(json, Condition.class);
    }

    /**
     * condition執行匹配
     *
     * @param condition  條件
     * @param simpleFact 事實
     * @return 是否成功
     */
    public static boolean runMatch(Condition condition, SimpleFact simpleFact, Consumer<String> msgConsumer) {

        checkNotNull(condition);
        checkNotNull(simpleFact);

        Boolean value = true;
        switch (condition.getOp()) {
            case AND: {

                for (Fact fact : condition.getFacts()) {
                    value = value && fact.match(simpleFact, msgConsumer);
                    if (!value) {
                        return value;
                    }
                }

                for (Condition child : condition.getChildren()) {
                    value = value && runMatch(child, simpleFact, msgConsumer);
                }

                return value;
            }

            case OR: {
                value = false;

                for (Fact fact : condition.getFacts()) {
                    value = value || fact.match(simpleFact, msgConsumer);
                    if (value) {
                        return value;
                    }
                }

                for (Condition child : condition.getChildren()) {
                    value = value || runMatch(child, simpleFact, msgConsumer);
                }

                return value;
            }
            default:
                throw new ConditionOperateNotSupportException("不支持的條件運算符！");
        }
    }

    /**
     * 獲取平展的condition事實
     *
     * @param condition  條件
     * @param simpleFact 事實
     */
    public static void getFlatCondition(Condition condition, SimpleFact simpleFact) {
        for (Fact fact : condition.getFacts()) {
            simpleFact.put(fact.getName(), fact.getOperator());
        }

        for (Condition child : condition.getChildren()) {
            getFlatCondition(child, simpleFact);
        }
    }
}
