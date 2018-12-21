package com.sigma.web;

import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import javax.validation.*;
import javax.xml.bind.ValidationException;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/5-10:41
 * desc: Bean 驗證器
 **/
@Slf4j
public class BeanValidator {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();


    /**
     * 驗證對象，如果失敗，則返回第一個驗證失敗的對象
     *
     * @param object 對象
     * @param <T>    泛型
     * @throws ValidationException 驗證不通過，則拋出異常
     */
    public static <T> void validate(T object) throws ValidationException {

        Validator validator = factory.getValidator();
        var constraintViolations = validator.validate(object);

        ConstraintViolation<T> constraintViolation = null;

        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            constraintViolation = constraintViolations.iterator().next();
        }

        if (constraintViolation != null) {
            throw new ValidationException(constraintViolation.getMessage());
        }
    }
}
