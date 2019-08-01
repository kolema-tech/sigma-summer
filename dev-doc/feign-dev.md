
@EnableFeignClients

```java
@Bean  
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {  
        return new BasicAuthRequestInterceptor("user","pass123");  
    } 
``` 


* Decoder feignDecoder: 
  
  ResponseEntityDecoder (which wraps a SpringDecoder)
* Encoder feignEncoder: 

  SpringEncoder
* Logger feignLogger: 
  
  Slf4jLogger
* Contract feignContract: 
  
  SpringMvcContract
* Feign.Builder feignBuilder: 

  HystrixFeign.Builder
* Client feignClient: 
  
  if Ribbon is enabled it is a LoadBalancerFeignClient, otherwise the default feign client is used.
  
  
注意 若开发人员需要在RequestInterceptor中使用ThreadLocal变量，则需要将Hystrix的隔离级别设置为“SEMAPHORE”，或直接禁用Hystrix。

application.yml

```yaml
# To disable Hystrix in Feign
feign:
  hystrix:
    enabled: false
    
# To set thread isolation to SEMAPHORE
hystrix:
  command:
    default:
      execution:
        isolation:
          strategy: SEMAPHORE
```

@FeignClient(name = "${feign.name}", url = "${feign.url}")


ref： https://www.cnblogs.com/dreamingodd/p/7545685.html