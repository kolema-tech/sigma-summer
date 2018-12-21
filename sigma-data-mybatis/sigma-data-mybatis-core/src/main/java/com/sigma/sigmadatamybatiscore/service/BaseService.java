package com.sigma.sigmadatamybatiscore.service;

import com.sigma.sigmadatamybatiscore.context.AuditContextHandler;
import com.sigma.sigmadatamybatiscore.entity.AuditEntity;
import com.sigma.sigmadatamybatiscore.mapper.BaseMapper;
import lombok.experimental.var;

import java.time.LocalDateTime;

/**
 * @author zen peng.
 * @version 1.0.3
 * date-time: 2018/12/21-14:27
 * desc: 基礎服務接口
 **/
public interface BaseService<T> extends BaseMapper<T> {

    /**
     * 创建审核
     *
     * @param entity
     */
    default void createAudit(T entity) {
        if (entity != null && entity instanceof AuditEntity) {
            var t = (AuditEntity) entity;
            t.setCreateTime(LocalDateTime.now());
            t.setCreateUserId(AuditContextHandler.getUserId());
            t.setCreateUserName(AuditContextHandler.getUserId());
        }
    }

    /**
     * 更新審核
     *
     * @param entity
     */
    default void updateAudit(T entity) {
        if (entity != null && entity instanceof AuditEntity) {
            var t = (AuditEntity) entity;
            t.setUpdateTime(LocalDateTime.now());
            t.setUpdateUserId(AuditContextHandler.getUserId());
            t.setUpdateUserName(AuditContextHandler.getUserId());
        }
    }

    /**
     *
     * @param entity
     */
    default void createAndUpdateAudit(T entity) {
        createAudit(entity);
        updateAudit(entity);
    }
}
