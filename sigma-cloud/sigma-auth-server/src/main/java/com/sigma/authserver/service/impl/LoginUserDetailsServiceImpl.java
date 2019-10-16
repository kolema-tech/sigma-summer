package com.sigma.authserver.service.impl;

import com.sigma.security.SigmaUser;
import com.sigma.security.service.BaseLoginUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    protected SigmaUser readMember(String username, String password) {
        return new SigmaUser(1, username, password, new ArrayList<>());
    }
}
