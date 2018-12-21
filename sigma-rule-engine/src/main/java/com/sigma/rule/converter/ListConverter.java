package com.sigma.rule.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sigma.rule.exception.FactDataTypeNotMatchException;
import com.sigma.sigmacore.utils.JsonUtils;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author zhenpeng
 * 列表轉化
 */
@Slf4j
public class ListConverter implements FactConverter<List<Object>> {

    @Override
    public List<Object> convert(Object value) throws FactDataTypeNotMatchException {

        List<Object> list = null;
        try {
            list = JsonUtils.deserialize(value.toString(), new TypeReference<List<Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list == null || list.size() == 0) {
            throw new FactDataTypeNotMatchException("", value + "轉列表失敗！");
        }

        return list;
    }
}
