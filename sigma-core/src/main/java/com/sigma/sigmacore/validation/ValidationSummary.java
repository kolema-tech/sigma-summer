package com.sigma.sigmacore.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.var;

import java.util.List;
import java.util.Optional;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-15:48
 * desc: 匯總
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationSummary {

    /**
     * 驗證結果列表
     */
    private List<ValidationResult> resultList;

    /**
     * 是否成功
     *
     * @return
     */
    public boolean success() {
        return !findFirst().isPresent();
    }

    /**
     * 獲取第一個 驗證不通過結果
     *
     * @return 第一個不成功的
     */
    public Optional<ValidationResult> findFirst() {
        return resultList.stream().filter(zw -> !zw.getSuccess()).findAny();
    }
}
