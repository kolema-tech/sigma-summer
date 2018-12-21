Ref:https://blog.csdn.net/u011521890/article/details/78078576

@Cacheable 触发缓存入口

@CacheEvict 触发移除缓存

@CacahePut 更新缓存

@Caching 将多种缓存操作分组

@CacheConfig 类级别的缓存注解，允许共享缓存名称

```java
@Caching(
        put = {
                @CachePut(value = "user", key = "\"user_\" + #user.id"),
                @CachePut(value = "user", key = "#user.name"),
                @CachePut(value = "user", key = "#user.account")
        }
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SaveUserInfo {

}
```