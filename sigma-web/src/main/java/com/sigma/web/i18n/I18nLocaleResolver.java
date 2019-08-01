package com.sigma.web.i18n;

import com.sigma.sigmacore.web.SigmaRequestHeader;
import com.sigma.web.LocaleMessage;
import com.sigma.web.ThreadContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 语言资源加载、切换
 *
 * @author ware zhang
 * @version 1.0.0
 * @date 2018/11/7 10:48
 */
public class I18nLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return request.getLocale();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse httpServletResponse, Locale locale) {
        SigmaRequestHeader header = ThreadContextHolder.getHeader();
        if (header != null && StringUtils.hasLength(header.getLang())) {
            LocaleMessage.setLocale(header.getLang());
        }
    }
}
