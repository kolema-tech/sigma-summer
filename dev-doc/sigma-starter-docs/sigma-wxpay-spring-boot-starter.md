# sigma wxpay starter 

## 1. 引用

```xml
<dependency>
    <groupId>com.github.kolema-tech</groupId>
    <artifactId>sigma-wxpay-spring-boot-starter</artifactId>
    <version>1.0.8-SNAPSHOT</version>
</dependency>
```

## 2. 配置

```yaml
sigmapay:
  wxpay:
    app-id: wx
    merchant-id: 136381
    app-key: Lm0qodcpPTN
```

## 3. 使用

```java
@Autowired
PayWrapperService payWrapperService;

@ApiOperation(value = "统一下单")
@GetMapping("/api/pay/createOrder")
public Object createOrder(String orderId) throws Exception {

    return payWrapperService.unifiedOrderRequest(
            UnifiedOrderRequest.builder()
                    .body("印花")
                    .notifyUrl("http://kolematech.com/")
                    .orderId(orderId)
                    .spbillCreateIp("127.0.0.1")
                    .totalFee(1)
                    .tradeType("NATIVE").build());
}

@ApiOperation(value = "查询订单")
@GetMapping("/api/pay/queryOrder")
public Object queryOrder(String orderId) throws Exception {

    return payWrapperService.queryOrderRequest(
            QueryOrderRequest.builder().orderId(orderId).build());
}

```