package com.sigma.security;

import com.sigma.sigmacore.web.SigmaResponse;
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
 * @version 1.0.8
 * date-time: 2019-10-
 * desc:
 **/
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("/member")
    public Principal user(Principal member) {
        return member;
    }

    @DeleteMapping(value = "/logout")
    public SigmaResponse revokeToken() {
        String token = httpServletRequest.getHeader("authorization").substring(7);
        return SigmaResponse.ok(consumerTokenServices.revokeToken(token));
    }
}
