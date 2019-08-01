package com.sigma.sigmacore.validation;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.var;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:53
 * desc: 驗證管理器
 **/
@Component
public class ValidationManager {

    private LinkedHashMap<Validation, Object> hashMap = new LinkedHashMap<>(100);
    /**
     * 失敗了是否立即停止
     */
    @Getter
    @Setter
    private Boolean stopWhenFailed;

    /**
     * 添加驗證
     *
     * @param validation  驗證
     * @param dataContext 上下問
     * @return
     */
    public ValidationManager add(Validation validation, Object dataContext) {
        hashMap.put(validation, dataContext);
        return this;
    }

    /**
     * 驗證
     *
     * @return 驗證結果
     */
    public ValidationSummary validate() {

        List<ValidationResult> results = Lists.newArrayList();

        var it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            var validation = it.next();

            var ret = validation.getKey().validate(validation.getValue());
            results.add(ret);

            if (!ret.getSuccess()) {
                break;
            }
        }

        return new ValidationSummary(results);
    }
}
