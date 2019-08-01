#Validation

* 依赖
* 规范
* 使用
* BindingResult
* 分组验证
* 方法验证
* MethodArgumentNotValidException

Bean Validation 1.0（JSR-303）是一个校验规范。

在spring Boot项目由于自带了hibernate validator 5(http://hibernate.org/validator )实现。

## 1. 依赖
```xml
<!--jsr 303-->
<dependency>
<groupId>javax.validation</groupId>
<artifactId>validation-api</artifactId>
<version>1.1.0.Final</version>
</dependency>
<!-- hibernate validator-->
<dependency>
<groupId>org.hibernate</groupId>
<artifactId>hibernate-validator</artifactId>
<version>5.2.0.Final</version>
</dependency>
```

## 2. 规范

JSR-303原生支持的限制有如下几种：

限制 | 说明
--- | ---
@Null | 只能为null
@NotNull | 必须不为null
@AssertFalse | 必须为false
@AssertTrue | 必须为true
@DecimalMax(value) | 必须为一个不大于指定值的数字
@DecimalMin(value) | 必须为一个不小于指定值的数字
@Digits(integer,fraction) | 必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
@Future | 必须是一个将来的日期
@Max(value) | 必须为一个不大于指定值的数字
@Min(value) | 必须为一个不小于指定值的数字
@Past | 必须是一个过去的日期
@Pattern(value) | 正则表达式
@Size(max,min) | 字符长度必须在min到max之间

除此之外，hibernate也还提供了其它的限制校验,在org.hibernate.validator.constraints包下

@NotBlank(message =) 验证字符串非null，且长度必须大于0
@Email 被注释的元素必须是电子邮箱地址
@Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
@NotEmpty 被注释的字符串的必须非空
@Range(min=,max=,message=) 被注释的元素必须在合适的范围内

在Controller中加上对RequestBody的校验：——@Valid 对po实体类进行校验

## 3. 用法

### 3.1 从Controller传入的BindingResult
```java
@ApiOperation(value = "創建應用程序")
@RequestMapping(value = "createResourceApplication", method = RequestMethod.POST)
public Response<Boolean> createResourceApplication(@Valid
@RequestBody CreateResourceApplicationRequestDTO requestDTO,
BindingResult bindingResult) {

if(bindingResult.hasErrors()){
List<ObjectError> ls=bindingResult.getAllErrors();
for (int i = 0; i < ls.size(); i++) {
System.out.println("error:"+ls.get(i));
}
}
}
```

### 3.2 全局拦截MethodArgumentNotValidException
```java
@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseBody
public Response<String> handleMethodArgumentNotValidException(
MethodArgumentNotValidException e) {

logger.error("驗證錯誤", "detail:" + e.getMessage());

Response<String> response = Response.create(500, e.getBindingResult()
.getAllErrors().get(0).getDefaultMessage(), null);
return response;
}
```

### 3.3 分组验证

对同一个Model，我们在增加和修改时对参数的校验也是不一样的，这个时候我们就需要定义分组验证。

步骤：
1. 为每一个分组顶一个空接口；
2. 在Model的属性添加注解的时候，指明Groups
3. @Validated时指明哪个组

示例代码：REF:http://www.cnblogs.com/beiyan/p/5946345.html

```java
/**
* 可以在一个Model上面添加多套参数验证规则，此接口定义添加Person模型新增时的参数校验规则
*/
public interface PersonAddView {
}

/**
* 可以在一个Model上面添加多套参数验证规则，此接口定义添加Person模型修改时的参数校验规则
*/
public interface PersonModifyView {
}


public class Person {
private long id;
/**
* 添加groups 属性，说明只在特定的验证规则里面起作用，不加则表示在使用Deafault规则时起作用
*/
@NotNull(groups = {PersonAddView.class, PersonModifyView.class}, message = "添加、修改用户时名字不能为空", payload = ValidateErrorLevel.Info.class)
@ListNotHasNull.List({
@ListNotHasNull(groups = {PersonAddView.class}, message = "添加上Name不能为空"),
@ListNotHasNull(groups = {PersonModifyView.class}, message = "修改时Name不能为空")})
private String name;

@NotNull(groups = {PersonAddView.class}, message = "添加用户时地址不能为空")
private String address;

@Min(value = 18, groups = {PersonAddView.class}, message = "姓名不能低于18岁")
@Max(value = 30, groups = {PersonModifyView.class}, message = "姓名不能超过30岁")
private int age;
//getter setter 方法......
}


/**
* 添加一个Person对象
* 此处启用PersonAddView 这个验证规则
* 备注：此处@Validated(PersonAddView.class) 表示使用PersonAndView这套校验规则，若使用@Valid 则表示使用默认校验规则，
* 若两个规则同时加上去，则只有第一套起作用
*/
@RequestMapping(value = "/person", method = RequestMethod.POST)
public void addPerson(@RequestBody @Validated({PersonAddView.class, Default.class}) Person person) {
System.out.println(person.toString());
}

/**
* 修改Person对象
* 此处启用PersonModifyView 这个验证规则
*/
@RequestMapping(value = "/person", method = RequestMethod.PUT)
public void modifyPerson(@RequestBody @Validated(value = {PersonModifyView.class}) Person person) {
System.out.println(person.toString());
}
```

### Spring validator方法级别验证

JSR和Hibernate validator的校验只能对Object的属性进行校验，不能对单个的参数进行校验，spring 在此基础上进行了扩展，添加了MethodValidationPostProcessor拦截器，可以实现对方法参数的校验

1、实例化MethodValidationPostProcessor
```java
@Bean
public MethodValidationPostProcessor methodValidationPostProcessor() {
return new MethodValidationPostProcessor();
}
```

2、在所要实现方法参数校验的类上面添加@Validated，如下
```
@RestController
@Validated
public class ValidateController {
}
```

3、在方法上面添加校验规则:
```java
@RequestMapping(value = "/test", method = RequestMethod.GET)
public String paramCheck(@Length(min = 10) @RequestParam String name) {
System.out.println(name);
return null;
}
```

当方法上面的参数校验失败,spring 框架就回抛出异常

```json
{
"timestamp": 1476108200558,
"status": 500,
"error": "Internal Server Error",
"exception": "javax.validation.ConstraintViolationException",
"message": "No message available",
"path": "/test"
}
```