package com.sigma.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/16-22:37
 * desc: 自定义用户
 **/
public class SigmaUser extends User {

    /**
     * 未登录用户
     */
    public static final String ANONYMOUS_USER = "anonymousUser";
    /**
     * 未登录用户
     */
    public static Integer USER_NO_LOGIN = -1;
    /**
     * 用户ID
     */
    @Getter
    @Setter
    private Integer userId;
    /**
     * 用户数组
     */
    @Getter
    @Setter
    private Map<String, Object> userMap;

    public SigmaUser(Integer userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(userId, username, password, true, true, true, true, authorities);
    }

    public SigmaUser(Integer userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    /**
     * 读取用户Id
     *
     * @return 返回uid
     */
    public static Integer readUserId() {

        if (ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return USER_NO_LOGIN;
        }

        return (Integer) ((LinkedHashMap) getPrincipal().get("principal")).get("userId");
    }

    /**
     * 读取用户名
     *
     * @return 返回user name
     */
    public static String readUserName() {
        if (ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return ANONYMOUS_USER;
        }

        return (String) ((LinkedHashMap) getPrincipal().get("principal")).get("username");
    }

    /**
     * 读取用户属性，只支持String类型
     *
     * @return 返回属性
     */
    public static String readProperty(String proName) {
        if (ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            return ANONYMOUS_USER;
        }

        return (String) ((LinkedHashMap) getPrincipal().get("principal")).get(proName);
    }

    /**
     * 读取 getPrincipal
     *
     * @return
     */
    @JsonIgnore
    public static LinkedHashMap getPrincipal() {
        return (LinkedHashMap) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
