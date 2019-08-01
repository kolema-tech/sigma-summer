package com.sigma.redis.annotation;

import com.sigma.redis.parser.IKeyGenerator;
import com.sigma.redis.parser.impl.DefaultKeyGenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface CacheClear {
    /**
     * 缓存key的前缀
     *
     * @return
     * @author Ace
     */
    String pre() default "";

    /**
     * 缓存key
     *
     * @return
     * @author Ace
     */
    String key() default "";

    /**
     * 缓存keys
     *
     * @return
     * @author Ace
     */
    String[] keys() default "";

    /**
     * 键值解析类
     *
     * @return
     */
    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
