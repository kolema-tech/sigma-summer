package com.sigma.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/6/16-22:03
 * desc: 兼容一些旧的系统
 **/
public interface LoginUserDetailsService {
    /**
     * 根据用户名密码验证用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException;
}
