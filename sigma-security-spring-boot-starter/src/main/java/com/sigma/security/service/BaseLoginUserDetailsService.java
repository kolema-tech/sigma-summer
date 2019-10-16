package com.sigma.security.service;

import com.sigma.security.SigmaUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        return readMember(username, password);
    }

    /**
     * 读取用户的核心逻辑
     *
     * @param username 用户名（手机号、邮箱）
     * @param password 密码（验证码）
     * @return 用户
     */
    protected abstract SigmaUser readMember(String username, String password);
}
