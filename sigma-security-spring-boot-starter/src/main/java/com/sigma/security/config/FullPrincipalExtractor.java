package com.sigma.security.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import java.util.Map;

/**
 * @author huston.peng
 * @version 1.0.5
 * date-time: 2019/5/16-22:35
 * desc: 带上自定义信息，全部提取
 **/
public class FullPrincipalExtractor implements PrincipalExtractor {

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        return map;
    }
}
