package com.sigma.sdk.interfaces;

import lombok.Builder;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

@Builder
public class UrlSuffix {

    private String url;
    private String format;
    private Object[] arguments;

    public String generate() {
        if (!StringUtils.isEmpty(url)) {
            return url;
        }

        return MessageFormat.format(format, arguments);
    }
}
