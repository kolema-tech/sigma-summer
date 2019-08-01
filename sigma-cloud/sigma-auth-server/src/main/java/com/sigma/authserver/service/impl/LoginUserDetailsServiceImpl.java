package com.sigma.authserver.service.impl;

import com.sigma.business.members.MemberEntity;
import com.sigma.business.members.RoleEntity;
import com.sigma.security.service.BaseLoginUserDetailsService;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/5/16-18:38
 * desc:
 **/
@Service
@Slf4j
public class LoginUserDetailsServiceImpl extends BaseLoginUserDetailsService {

    @Override
    protected MemberEntity findMemberByUsernameAndPassword(String username, String password) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId(1);
        memberEntity.setMemberName("admin");
        memberEntity.setPassword("admin");
        memberEntity.setMobile("18576670179");
        memberEntity.setEmail("1050244110@qq.com");
        memberEntity.setSex(true);
        memberEntity.setBirthday(LocalDate.now());
        memberEntity.setCreateTime(new Date());

        var roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setRoleName("管理员");
        roleEntity.setValid((short) 1);
        roleEntity.setCreateTime(LocalDateTime.now());
        roleEntity.setUpdateTime(LocalDateTime.now());

        memberEntity.getRoleEntities().add(roleEntity);

        return memberEntity;
    }

    @Override
    protected Map<String, Object> configMemberProperties(MemberEntity memberEntity) {
        return new HashMap<>();
    }
}
