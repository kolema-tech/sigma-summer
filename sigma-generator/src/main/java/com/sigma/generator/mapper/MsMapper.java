package com.sigma.generator.mapper;

import com.sigma.generator.entity.Ms;

public interface MsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ms record);

    int insertSelective(Ms record);

    Ms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ms record);

    int updateByPrimaryKey(Ms record);
}