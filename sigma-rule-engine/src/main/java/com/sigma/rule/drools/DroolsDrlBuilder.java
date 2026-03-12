package com.sigma.rule.drools;

import com.sigma.rule.Condition;
import com.sigma.rule.fact.Fact;
import com.sigma.rule.operator.ConditionOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 将 {@link RuleDefinition} 翻译为 Drools DRL 字符串。
 *
 * <p>支持 AND/OR 嵌套条件，生成标准 DRL 规则文本。</p>
 */
public class DroolsDrlBuilder {

    /**
     * 构建完整的 DRL 字符串。
     */
    public String build(RuleDefinition rule) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.sigma.rule.generated\n\n");
        sb.append("rule \"").append(rule.getName()).append("\"\n");
        sb.append("    salience ").append(rule.getSalience()).append("\n");
        sb.append("    when\n");
        sb.append("        ").append(rule.getFactAlias()).append(": ")
          .append(rule.getFactClass()).append("(");

        if (rule.getCondition() != null) {
            sb.append(buildConditionExpr(rule.getCondition()));
        }

        sb.append(")\n");
        sb.append("    then\n");

        if (rule.getActions() != null) {
            for (String action : rule.getActions()) {
                sb.append("        ").append(action).append("\n");
            }
        }

        sb.append("end\n");
        return sb.toString();
    }

    /**
     * 递归将条件树转为布尔表达式字符串。
     * AND → expr1 && expr2 ...
     * OR  → expr1 || expr2 ...
     */
    private String buildConditionExpr(Condition condition) {
        String joiner = condition.getOp() == ConditionOperator.AND ? " && " : " || ";
        List<String> parts = new ArrayList<>();

        // 当前层的 facts
        if (condition.getFacts() != null) {
            for (Fact fact : condition.getFacts()) {
                parts.add(buildFactExpr(fact));
            }
        }

        // 子条件（嵌套）
        if (condition.getChildren() != null) {
            for (Condition child : condition.getChildren()) {
                String childExpr = buildConditionExpr(child);
                // 子条件用括号包裹，避免优先级问题
                parts.add("(" + childExpr + ")");
            }
        }

        return String.join(joiner, parts);
    }

    /**
     * 将单个 Fact 转为 DRL 字段表达式。
     * 例如：amount > 100、vip == true、status in ("A", "B")
     */
    private String buildFactExpr(Fact fact) {
        String field = fact.getName();
        Object value = fact.getValue();

        switch (fact.getOperator()) {
            case EQ:           return field + " == " + formatValue(value);
            case NOT_EQ:       return field + " != " + formatValue(value);
            case GT:           return field + " > " + formatValue(value);
            case GT_INC:       return field + " >= " + formatValue(value);
            case LT:           return field + " < " + formatValue(value);
            case LT_INC:       return field + " <= " + formatValue(value);
            case TRUE:         return field + " == true";
            case FALSE:        return field + " == false";
            case IN:           return field + " in " + formatListValue(value);
            case NOT_IN:       return field + " not in " + formatListValue(value);
            case REGEX:        return field + " matches \"" + value + "\"";
            default:
                throw new UnsupportedOperationException("Drools DRL 不支持 operator: " + fact.getOperator());
        }
    }

    private String formatValue(Object value) {
        if (value == null) return "null";
        if (value instanceof String) return "\"" + value + "\"";
        return value.toString();
    }

    private String formatListValue(Object value) {
        // 期望 value 是逗号分隔字符串或 List
        if (value instanceof Iterable) {
            List<String> items = new ArrayList<>();
            for (Object item : (Iterable<?>) value) {
                items.add(formatValue(item));
            }
            return "(" + String.join(", ", items) + ")";
        }
        // 兜底：直接用字符串
        return "(" + value + ")";
    }
}
