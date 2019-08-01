package com.sigma.business.members;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-22:17
 * desc:
 **/
@Data
public class PermissionEntity {

    /**
     * 权限Id
     */
    private int id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 符号
     */
    private String slug;

    /**
     * 请求方法
     */
    private String httpMethod;

    /**
     * 路径
     */
    private String httpPath;

    /**
     * 权限类别
     */
    private Boolean permissionType;

    /**
     * 创建日期
     */
    private LocalDateTime createdAt;

    /**
     * 更新日期
     */
    private LocalDateTime updatedAt;
}
