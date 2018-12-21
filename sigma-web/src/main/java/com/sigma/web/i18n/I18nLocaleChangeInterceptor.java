package com.sigma.web.i18n;

import com.sigma.sigmacore.web.SigmaRequestHeader;
import com.sigma.web.ThreadContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义语言拦截器
 * 因为前端传过来的是JSON字符串，所以我们需要重写拦截器，否则request中无法获取lang参数
 *
 * @author ware zhang
 * @version 1.0.0
 * @date 2018/11/6 17:56
 */
public class I18nLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        SigmaRequestHeader header = ThreadContextHolder.getHeader();
        if (header != null && header.getLang() != null && this.checkHttpMethod(request.getMethod())) {
            String newLocale = header.getLang();
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            try {
                localeResolver.setLocale(request, response, this.parseLocaleValue(newLocale));
            } catch (IllegalArgumentException var7) {
                if (!this.isIgnoreInvalidLocale()) {
                    throw var7;
                }

                this.logger.error("Ignoring invalid locale value [" + newLocale + "]: " + var7.getMessage());
            }
        }

        return true;
    }

    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = this.getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        } else {
            String[] var3 = configuredMethods;
            int var4 = configuredMethods.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String configuredMethod = var3[var5];
                if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                    return true;
                }
            }

            return false;
        }
    }
}
