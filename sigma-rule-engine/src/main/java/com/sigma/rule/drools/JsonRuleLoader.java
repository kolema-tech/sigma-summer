package com.sigma.rule.drools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 从 JSON 加载规则并注入 {@link DroolsRuleEngine}，支持动态刷新。
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * DroolsRuleEngine engine = new DroolsRuleEngine();
 * JsonRuleLoader loader = new JsonRuleLoader(engine);
 *
 * // 加载单条规则（自动编译并注入引擎）
 * loader.load(jsonString);
 *
 * // 刷新规则（重新加载，已存在则覆盖）
 * loader.load(updatedJsonString);
 *
 * // 批量加载
 * loader.loadAll(List.of(json1, json2));
 *
 * // 移除规则
 * loader.remove("vip-discount");
 * }</pre>
 */
@Slf4j
public class JsonRuleLoader {

    private final DroolsRuleEngine engine;
    private final ObjectMapper objectMapper;
    private final DroolsDrlBuilder drlBuilder;

    public JsonRuleLoader(DroolsRuleEngine engine) {
        this(engine, new ObjectMapper());
    }

    public JsonRuleLoader(DroolsRuleEngine engine, ObjectMapper objectMapper) {
        this.engine = engine;
        this.objectMapper = objectMapper;
        this.drlBuilder = new DroolsDrlBuilder();
    }

    /**
     * 从 JSON 字符串加载一条规则。若规则名已存在，则覆盖（动态刷新）。
     *
     * @param json RuleDefinition JSON
     * @throws IOException           JSON 解析失败
     * @throws DroolsBuildException  DRL 编译失败
     */
    public void load(String json) throws IOException {
        RuleDefinition rule = objectMapper.readValue(json, RuleDefinition.class);
        String drl = drlBuilder.build(rule);
        log.debug("Generated DRL for rule '{}':\n{}", rule.getName(), drl);
        engine.addRule(rule.getName(), drl);
        log.info("Rule '{}' loaded successfully", rule.getName());
    }

    /**
     * 批量加载规则。
     */
    public void loadAll(List<String> jsonList) throws IOException {
        for (String json : jsonList) {
            load(json);
        }
    }

    /**
     * 移除指定规则（动态生效）。
     */
    public void remove(String ruleName) {
        engine.removeRule(ruleName);
        log.info("Rule '{}' removed", ruleName);
    }

    /**
     * 将 JSON 翻译为 DRL 字符串（仅预览，不加载到引擎）。
     */
    public String toDrl(String json) throws IOException {
        RuleDefinition rule = objectMapper.readValue(json, RuleDefinition.class);
        return drlBuilder.build(rule);
    }
}
