package com.sigma.sigmadatamybatiscore.service.impl;

import com.sigma.sigmadatamybatiscore.mapper.BaseMapper;
import com.sigma.sigmadatamybatiscore.service.BaseService;

import java.util.List;

/**
 * 基本服务类，其他service可以按实际情况自行继承
 * url:https://blog.csdn.net/q564495021/article/details/81607515
 *
 * @author ware
 * @version 1.0.2
 * date 2018/09/18
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    /**
     * 獲取Mapper
     *
     * @return BaseMapper
     */
    protected abstract BaseMapper<T> getMapper();

    @Override
    public int deleteByPrimaryKey(Object o) {
        return getMapper().deleteByExample(o);
    }

    @Override
    public int delete(T t) {
        return getMapper().delete(t);
    }

    @Override
    public int insert(T t) {

        createAndUpdateAudit(t);

        return getMapper().insert(t);
    }

    @Override
    public int insertSelective(T t) {

        createAndUpdateAudit(t);

        return getMapper().insertSelective(t);
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return getMapper().existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return getMapper().selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return getMapper().selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return getMapper().select(t);
    }

    @Override
    public T selectOne(T t) {
        return getMapper().selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {

        updateAudit(t);

        return getMapper().updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {

        updateAudit(t);

        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public int deleteByCondition(Object o) {
        return getMapper().deleteByCondition(o);
    }

    @Override
    public List<T> selectByCondition(Object o) {
        return getMapper().selectByCondition(o);
    }

    @Override
    public int selectCountByCondition(Object o) {
        return getMapper().selectCountByCondition(o);
    }

    @Override
    public int updateByCondition(T t, Object o) {

        updateAudit(t);

        return getMapper().updateByCondition(t, o);
    }

    @Override
    public int updateByConditionSelective(T t, Object o) {

        updateAudit(t);

        return getMapper().updateByConditionSelective(t, o);
    }

    @Override
    public int deleteByExample(Object o) {
        return getMapper().deleteByExample(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return getMapper().selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return getMapper().selectCountByExample(o);
    }

    @Override
    public int updateByExample(T t, Object o) {

        updateAudit(t);

        return getMapper().updateByExample(t, o);
    }

    @Override
    public int updateByExampleSelective(T t, Object o) {

        updateAudit(t);

        return getMapper().updateByExampleSelective(t, o);
    }

    @Override
    public int deleteByIds(String s) {
        return getMapper().deleteByIds(s);
    }

    @Override
    public List<T> selectByIds(String s) {
        return getMapper().selectByIds(s);
    }

    @Override
    public int insertList(List<T> list) {

        list.forEach(zw -> createAndUpdateAudit(zw));

        return getMapper().insertList(list);
    }

    @Override
    public int insertUseGeneratedKeys(T t) {

        createAndUpdateAudit(t);

        return getMapper().insertUseGeneratedKeys(t);
    }
}
