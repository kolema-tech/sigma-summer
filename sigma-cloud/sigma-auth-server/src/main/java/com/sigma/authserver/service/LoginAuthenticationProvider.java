package com.sigma.authserver.service;

import com.sigma.authserver.config.security.NoEncryptPasswordEncoder;
import com.sigma.security.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/5/16-18:03
 * desc:
 **/
@Service
public class LoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder = new NoEncryptPasswordEncoder();

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        Object salt = null;

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException("Bad credentials:" + userDetails);
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.matches(userDetails.getPassword(), presentedPassword)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException("Bad credentials:" + userDetails);
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate
     * passwords. If not set, the password will be compared as plain text.
     * <p>
     * For systems which are already using salted password which are encoded
     * with a previous release, the encoder should be of type
     * {@code org.springframework.security.authentication.encoding.PasswordEncoder}
     * . Otherwise, the recommended approach is to use
     * {@code org.springframework.security.crypto.password.PasswordEncoder}.
     *
     * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder}
     *                        types.
     */
    public void setPasswordEncoder(Object passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        if (passwordEncoder instanceof PasswordEncoder) {
            this.passwordEncoder = (PasswordEncoder) passwordEncoder;
            return;
        }

        if (passwordEncoder instanceof PasswordEncoder) {
            final PasswordEncoder delegate = (PasswordEncoder) passwordEncoder;
            this.passwordEncoder = new NoEncryptPasswordEncoder() {
                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }

                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }
            };

            return;
        }

        throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
    }


    protected LoginUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(LoginUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            String password = (String) authentication.getCredentials();
            /**
             * 区别:这里使用的是自定义的验证方法
             */
            loadedUser = getUserDetailsService().loadUserByUsername(username, password);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}
