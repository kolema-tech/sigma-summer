package com.sigma.authserver.config.security;

import com.sigma.authserver.service.LoginAuthenticationProvider;
import com.sigma.security.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author huston.peng
 * @version 1.0.6
 * date-time: 2019/7/8-13:58
 * desc:
 **/
@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    /**
     * 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置用户
     * 使用内存中的用户，实际项目中，一般使用的是数据库保存用户，具体的实现类可以使用JdbcDaoImpl或者JdbcUserDetailsManager
     *
     * @return
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin")).authorities("USER").build());
        return manager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(loginAuthicationProvider());
    }

    @Bean
    public LoginAuthenticationProvider loginAuthicationProvider() {
        LoginAuthenticationProvider provider = new LoginAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(loginUserDetailsService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider.setPasswordEncoder(new NoEncryptPasswordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return new NoEncryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll();

        http.cors().and()
                .authorizeRequests()
                .antMatchers("/api/user/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().csrf().disable();

        //认证服务器直接放开Option的
    }
}
