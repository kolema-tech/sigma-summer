package com.sigma.rule.converter;

import com.sigma.rule.exception.FactDataTypeNotMatchException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhenpeng
 * 時間轉換器
 */
@Slf4j
public class DateConverter implements FactConverter<LocalDateTime> {

    @Override
    public LocalDateTime convert(Object value) throws FactDataTypeNotMatchException {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            if (value instanceof LocalDateTime) {
                return (LocalDateTime) value;
            }

            return LocalDateTime.parse(value.toString(), df);
        } catch (Exception ex) {
            log.error("验证失败", ex);
            throw new FactDataTypeNotMatchException("", ex.getMessage());
        }
    }
}
