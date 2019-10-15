package com.sigma.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sigma.rule.exception.FactValueNullException;
import com.sigma.rule.fact.SimpleFact;
import com.sigma.sigmacore.exception.SigmaException;
import lombok.experimental.var;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhenpeng
 * 規則引擎
 */
public class RuleEngine {

    /**
     * 條件
     */
    private Condition condition = null;


    public void addRule(String rule) throws IOException {
        checkNotNull(rule);

        condition = ConditionFacade.create(rule);
    }


    public RuleResult run(SimpleFact simpleFact) {

        if (condition == null) {
            throw new FactValueNullException("規則為空！");
        }

        checkNotNull(simpleFact);

        //初始化結果
        var ruleResult = RuleResult.SUCCESS;

        try {
            Boolean pass = ConditionFacade.runMatch(condition, simpleFact, (msg) -> {
                ruleResult.getFactMessages().add(msg);
            });

            ruleResult.setPass(pass);

            return ruleResult;
        } catch (SigmaException ex) {
            ruleResult.setPass(false);
        }

        return ruleResult;
    }

    @JsonIgnore
    public SimpleFact getFlatCondition() {
        SimpleFact simpleFact = new SimpleFact();
        ConditionFacade.getFlatCondition(condition, simpleFact);
        return simpleFact;
    }
}
