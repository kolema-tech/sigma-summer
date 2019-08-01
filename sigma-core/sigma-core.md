# sigma-core

## utils

名称|描述|使用场景|状态
--|--|--|--
JsonUtils|JSON序列化与反序列化的工具。| JSON序列化 | 完成
RetryUtil|重试工具。| 网络请求、逻辑重试 | 完成
SpringBeanUtil|Bean访问工具。| 手动获取Bean | 完成
EnumUtil|获取枚举的工具，支持索引、名称、code方式获取。 | 根据code获取枚举 | 完成
BeanValidator|Hi验证工具，失败抛出异常ValidationException。 | 校验Bean | 完成
BasicRestTemplate|待Basic认证的RestTemplate。| 带认证的请求 | 完成
AesUtil|AES加密解密工具，支持AES/ECB/PKCS5Padding。| AES加解密 | 完成

## validation

提供验证简易封装

## 功能

* CatInfoInterceptor + WebControllerAspect + SigmaMetrics 记录性能
* SigmaRetry + RetryerAspect 提供重试
* 默认支持全部跨域
* 默认集成Swagger
* GlobalDefaultExceptionHandler：未知异常、验证异常、SigmaException

## 定义

* Request、RequestHeader
* Response、ResponseHeader