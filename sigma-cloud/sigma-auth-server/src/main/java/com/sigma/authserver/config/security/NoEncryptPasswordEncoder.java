package com.sigma.authserver.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/8-13:53
 * desc:
 **/
public class NoEncryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return (String) charSequence;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals((String) charSequence);
    }
}
