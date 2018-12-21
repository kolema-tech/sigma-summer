package com.sigma.sigmadatamybatiscore.mapper;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/21-14:25
 * desc: Base mapper.
 **/
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>,
        MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T>, ExampleMapper<T> {
}
