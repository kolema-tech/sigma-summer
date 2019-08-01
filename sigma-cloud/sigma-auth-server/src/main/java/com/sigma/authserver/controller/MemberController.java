package com.sigma.authserver.controller;

import com.sigma.sigmacore.web.SigmaResponse;
import lombok.experimental.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/8-13:54
 * desc:
 **/
@RestController
@RequestMapping("/api/user")
public class MemberController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping(value = "")
    public Principal user(Principal member) {
        return member;
    }

    @DeleteMapping(value = "/")
    public SigmaResponse revokeToken() {
        var token = httpServletRequest.getHeader("authorization").substring(7);
        return SigmaResponse.ok(consumerTokenServices.revokeToken(token));
    }
}
