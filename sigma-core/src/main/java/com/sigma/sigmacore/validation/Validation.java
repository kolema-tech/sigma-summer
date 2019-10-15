package com.sigma.sigmacore.validation;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-14:48
 * desc: 驗證接口
 **/
public interface Validation<T> {

    /**
     * 執行驗證
     *
     * @param context 上下文
     * @return 验证结果
     */
    ValidationResult validate(T context);
}
