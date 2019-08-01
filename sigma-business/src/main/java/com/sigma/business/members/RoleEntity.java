package com.sigma.business.members;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-22:16
 * desc: 角色实体
 **/
@Data
public class RoleEntity {

    /**
     * 角色Id
     */
    private int id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否启用
     */
    private short valid;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    private LocalDateTime updateTime;

    /**
     * 权限
     */
    private Set<PermissionEntity> permissionEntities = new HashSet<>();
}
