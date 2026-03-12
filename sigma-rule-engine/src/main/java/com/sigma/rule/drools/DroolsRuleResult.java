package com.sigma.rule.drools;

import lombok.Getter;

import java.util.List;

/**
 * Drools 规则引擎执行结果
 */
@Getter
public class DroolsRuleResult {

    /**
     * 是否执行成功
     */
    private final boolean success;

    /**
     * 本次触发的规则名称列表
     */
    private final List<String> firedRules;

    public DroolsRuleResult(boolean success, List<String> firedRules) {
        this.success = success;
        this.firedRules = firedRules;
    }

    /**
     * 是否有规则被触发
     */
    public boolean hasFiredRules() {
        return firedRules != null && !firedRules.isEmpty();
    }
}
