package com.sigma.redis.annotation;

import com.sigma.redis.constants.CacheScope;
import com.sigma.redis.parser.ICacheResultParser;
import com.sigma.redis.parser.IKeyGenerator;
import com.sigma.redis.parser.impl.DefaultKeyGenerator;
import com.sigma.redis.parser.impl.DefaultResultParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface Cache {
    /**
     * 缓存key menu_{0.id}{1}_type
     *
     * @return
     * @author Ace
     * @date 2017年5月3日
     */
    String key() default "";

    /**
     * 作用域
     *
     * @return
     * @author Ace
     * @date 2017年5月3日
     */
    CacheScope scope() default CacheScope.application;

    /**
     * 过期时间
     *
     * @return
     * @author Ace
     * @date 2017年5月3日
     */
    int expire() default 720;

    /**
     * 描述
     *
     * @return
     * @author Ace
     * @date 2017年5月3日
     */
    String desc() default "";

    /**
     * 返回类型
     *
     * @return
     * @author Ace
     * @date 2017年5月4日
     */
    Class[] result() default Object.class;

    /**
     * 返回结果解析类
     *
     * @return
     */
    Class<? extends ICacheResultParser> parser() default DefaultResultParser.class;

    /**
     * 键值解析类
     *
     * @return
     */
    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
