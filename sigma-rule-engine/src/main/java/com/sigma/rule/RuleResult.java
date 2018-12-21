package com.sigma.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenpeng
 * 規則結果
 */
@Getter
@Setter
public class RuleResult {
    public static final RuleResult SUCCESS = new RuleResult(true);
    /**
     * 是否通過
     */
    private Boolean pass;

    /**
     * 事實消息
     */
    private List<String> factMessages;

    public RuleResult(Boolean pass) {
        this.pass = pass;
        this.factMessages = new ArrayList<>();
    }
}
