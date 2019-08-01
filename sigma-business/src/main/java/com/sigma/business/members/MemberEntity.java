package com.sigma.business.members;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/16-22:13
 * desc: 会员对象
 **/
@Data
public class MemberEntity {

    /**
     * 会员id
     */
    private int id;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private boolean sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 角色
     */
    private Set<RoleEntity> roleEntities = new HashSet<>();
}
