package com.sigma.web.config;

import com.sigma.web.i18n.I18nLocaleChangeInterceptor;
import com.sigma.web.i18n.I18nLocaleResolver;
import com.sigma.web.interceptor.CatInfoInterceptor;
import com.sigma.web.interceptor.RqContextInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static com.sigma.web.SigmaWebConstants.DEFAULT_PATH_PATTERN;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/4-9:42
 * desc: WEB MVC的配置
 **/
@Configuration
public class SigmaWebAppConfig implements WebMvcConfigurer {

    @Resource
    private MessageSource messageSource;

    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    /**
     * 添加攔截器，只攔截API開頭的
     *
     * @param registry 註冊
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CatInfoInterceptor()).addPathPatterns(DEFAULT_PATH_PATTERN);
        registry.addInterceptor(new RqContextInterceptor()).addPathPatterns(DEFAULT_PATH_PATTERN);
        registry.addInterceptor(new I18nLocaleChangeInterceptor()).addPathPatterns(DEFAULT_PATH_PATTERN);
    }
}
