package com.sigma.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/12/8-10:58
 * desc: 多语言
 **/
@Component
public class LocaleMessage {

    @Autowired
    private MessageSource messageSource;

    /**
     * 设置默认语言
     *
     * @param lang
     */
    public static void setLocale(String lang) {

        if (StringUtils.hasLength(lang)) {
            Locale locale = LocaleContextHolder.getLocale();

            switch (lang) {
                case "zh_hk":
                    locale = Locale.TRADITIONAL_CHINESE;
                    break;
                case "en_hk":
                case "en_sg":
                    locale = Locale.US;
                    break;
                default:
                    break;
            }

            LocaleContextHolder.setLocale(locale);
        }
    }

    public String getMessage(String code) {
        return this.getMessage(code, new Object[]{});
    }

    public String getMessage(String code, String defaultMessage) {
        return this.getMessage(code, null, defaultMessage);
    }

    public String getMessage(String code, String defaultMessage, Locale locale) {
        return this.getMessage(code, null, defaultMessage, locale);
    }

    public String getMessage(String code, Locale locale) {
        return this.getMessage(code, null, "", locale);
    }

    public String getMessage(String code, Object[] args) {
        return this.getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return this.getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(code, args, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}