package com.sigma.rule.drools;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于 Drools 的动态规则引擎
 *
 * <p>支持在运行时动态添加、更新、删除 DRL 规则，无需重启应用。
 * 每次规则变更后会自动重新构建 KieContainer。</p>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * DroolsRuleEngine engine = new DroolsRuleEngine();
 *
 * String drl = "package com.sigma.rules\n" +
 *              "rule \"discount rule\"\n" +
 *              "  when\n" +
 *              "    $order: Order(amount > 100)\n" +
 *              "  then\n" +
 *              "    $order.setDiscount(0.1);\n" +
 *              "end\n";
 *
 * engine.addRule("discount-rule", drl);
 * DroolsRuleResult result = engine.fire(order);
 * }</pre>
 */
@Slf4j
public class DroolsRuleEngine {

    /**
     * 规则存储：规则名 -> DRL 字符串
     */
    private final Map<String, String> rulesMap = new ConcurrentHashMap<>();

    /**
     * 当前活跃的 KieContainer
     */
    private volatile KieContainer kieContainer;

    /**
     * 添加或更新一条规则。
     * 若规则名已存在，则覆盖原规则并重新编译。
     *
     * @param ruleName 规则唯一标识（不含扩展名）
     * @param drl      Drools 规则语言（DRL）字符串
     * @throws DroolsBuildException 规则编译失败时抛出
     */
    public void addRule(String ruleName, String drl) {
        String previous = rulesMap.put(ruleName, drl);
        try {
            rebuild();
        } catch (DroolsBuildException e) {
            // 编译失败，回滚本次变更
            if (previous == null) {
                rulesMap.remove(ruleName);
            } else {
                rulesMap.put(ruleName, previous);
            }
            throw e;
        }
    }

    /**
     * 批量添加规则。
     *
     * @param rules 规则 Map，key 为规则名，value 为 DRL 字符串
     * @throws DroolsBuildException 规则编译失败时抛出
     */
    public void addRules(Map<String, String> rules) {
        Map<String, String> snapshot = new ConcurrentHashMap<>(rulesMap);
        rulesMap.putAll(rules);
        try {
            rebuild();
        } catch (DroolsBuildException e) {
            // 编译失败，回滚至快照
            rulesMap.clear();
            rulesMap.putAll(snapshot);
            throw e;
        }
    }

    /**
     * 移除指定规则并重新构建引擎。
     *
     * @param ruleName 规则名称
     */
    public void removeRule(String ruleName) {
        rulesMap.remove(ruleName);
        if (rulesMap.isEmpty()) {
            dispose();
        } else {
            rebuild();
        }
    }

    /**
     * 清空所有规则。
     */
    public void clearRules() {
        rulesMap.clear();
        dispose();
    }

    /**
     * 获取当前已加载的规则数量。
     */
    public int getRuleCount() {
        return rulesMap.size();
    }

    /**
     * 对传入的事实对象执行规则匹配（无状态会话）。
     *
     * @param facts 事实对象，可以是任意 POJO
     * @return 规则执行结果，包含本次触发的规则名称列表
     * @throws IllegalStateException 未添加任何规则时抛出
     */
    public DroolsRuleResult fire(Object... facts) {
        if (kieContainer == null) {
            throw new IllegalStateException("规则引擎未初始化，请先通过 addRule() 添加规则");
        }

        List<String> firedRules = new ArrayList<>();
        StatelessKieSession session = kieContainer.newStatelessKieSession();

        session.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                String name = event.getMatch().getRule().getName();
                firedRules.add(name);
                log.debug("Drools rule fired: {}", name);
            }
        });

        session.execute(Arrays.asList(facts));

        log.debug("Drools execution finished, fired rules: {}", firedRules);
        return new DroolsRuleResult(true, firedRules);
    }

    /**
     * 重新构建 KieContainer（动态规则的核心机制）。
     * 每次规则变更后调用此方法，使用新的 ReleaseId 隔离旧容器。
     */
    private synchronized void rebuild() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        // 使用随机版本号隔离每次构建，避免 KieRepository 缓存干扰
        ReleaseId releaseId = ks.newReleaseId(
                "com.sigma.rule",
                "dynamic-rules",
                UUID.randomUUID().toString()
        );
        kfs.generateAndWritePomXML(releaseId);

        for (Map.Entry<String, String> entry : rulesMap.entrySet()) {
            String path = "src/main/resources/rules/" + entry.getKey() + ".drl";
            kfs.write(path, entry.getValue());
            log.debug("Writing rule to KieFileSystem: {}", path);
        }

        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();

        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            String errors = kb.getResults().getMessages(Message.Level.ERROR).toString();
            throw new DroolsBuildException("规则编译失败: " + errors);
        }

        KieContainer oldContainer = kieContainer;
        kieContainer = ks.newKieContainer(releaseId);

        // 释放旧容器资源
        if (oldContainer != null) {
            oldContainer.dispose();
        }

        log.info("Drools rule engine rebuilt successfully with {} rule(s)", rulesMap.size());
    }

    /**
     * 释放 KieContainer 资源。
     */
    public void dispose() {
        if (kieContainer != null) {
            kieContainer.dispose();
            kieContainer = null;
            log.info("Drools KieContainer disposed");
        }
    }
}
