package com.sigma.rule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sigma.rule.fact.Fact;
import com.sigma.rule.operator.ConditionOperator;
import com.sigma.sigmacore.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author pzdn
 * 条件运算
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Condition {

    /**
     * 運算符
     */
    private ConditionOperator op;
    /**
     * 子條件
     */
    private List<Condition> children;
    /**
     * 事實
     */
    private List<Fact> facts;

    /**
     * 構造器
     */
    public Condition() {
        children = new ArrayList<>();
        facts = new ArrayList<>();
    }

    /**
     * 添加 孩子
     *
     * @param condition 條件
     */
    public void addChild(Condition condition) {
        checkNotNull(condition);
        children.add(condition);
    }

    @Override
    public String toString() {
        try {
            return JsonUtils.serialize(this, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
