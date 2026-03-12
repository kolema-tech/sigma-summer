package com.sigma.rule.drools;

import com.sigma.rule.Condition;
import lombok.Data;

import java.util.List;

/**
 * JSON 规则定义模型。
 *
 * <p>示例 JSON：</p>
 * <pre>{@code
 * {
 *   "name": "vip-discount",
 *   "salience": 10,
 *   "factClass": "com.example.Order",
 *   "factAlias": "$order",
 *   "condition": {
 *     "op": "AND",
 *     "facts": [
 *       {"name": "amount", "operator": "GT", "value": 100}
 *     ],
 *     "children": [
 *       {
 *         "op": "OR",
 *         "facts": [
 *           {"name": "vip", "operator": "TRUE"},
 *           {"name": "level", "operator": "EQ", "value": "GOLD"}
 *         ]
 *       }
 *     ]
 *   },
 *   "actions": [
 *     "$order.setDiscount(0.1);",
 *     "update($order);"
 *   ]
 * }
 * }</pre>
 */
@Data
public class RuleDefinition {

    /** 规则唯一名称 */
    private String name;

    /** 优先级，默认 0，数字越大越优先 */
    private int salience = 0;

    /** Fact 的全限定类名，例如 "com.example.Order" */
    private String factClass;

    /** Fact 绑定别名，例如 "$order" */
    private String factAlias = "$fact";

    /** 条件树（AND/OR 嵌套） */
    private Condition condition;

    /** then 块中执行的语句列表 */
    private List<String> actions;
}
