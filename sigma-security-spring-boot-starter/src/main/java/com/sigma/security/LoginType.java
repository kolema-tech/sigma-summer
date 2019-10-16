package com.sigma.security;

/**
 * @author huston.peng
 * @version 1.0.0
 * date-time: 2019/5/29-14:04
 * desc: 登录类型
 **/
public enum LoginType {

    /**
     * 用户名 + 密码方式
     */
    PASSWORD,

    /**
     * 手机 + 验证码
     */
    VCODE,

    /**
     * 邮箱 + 验证码
     */
    EMAIL;
}
