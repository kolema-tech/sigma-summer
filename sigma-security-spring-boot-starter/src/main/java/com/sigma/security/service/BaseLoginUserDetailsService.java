package com.sigma.security.service;

import com.sigma.security.SigmaUser;
import com.sigma.security.entity.MemberEntity;
import com.sigma.security.entity.PermissionEntity;
import com.sigma.security.entity.RoleEntity;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/6/16-22:24
 * desc: 自定义对象读取
 **/
@Slf4j
public abstract class BaseLoginUserDetailsService implements LoginUserDetailsService {

    /**
     * 进行登录验证
     */
    @Override
    public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {

        var memberEntity = findMemberByUsernameAndPassword(username, password);

        return readMember(memberEntity, password);
    }

    /**
     * 读取用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户独享
     */
    protected abstract MemberEntity findMemberByUsernameAndPassword(String username, String password);

    /**
     * 配置其他属性
     *
     * @param memberEntity 对象
     * @return Map
     */
    protected abstract Map<String, Object> configMemberProperties(MemberEntity memberEntity);

    private User readMember(MemberEntity memberEntity, String password) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;
        for (RoleEntity role : memberEntity.getRoleEntities()) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            for (PermissionEntity permission : role.getPermissionEntities()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(permission.getHttpPath());
                grantedAuthorities.add(authority);
            }
        }

        var user = new SigmaUser(memberEntity.getId(), memberEntity.getMemberName(), password,
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);

        user.setUserId(memberEntity.getId());
        user.setUserMap(configMemberProperties(memberEntity));

        return user;
    }
}
