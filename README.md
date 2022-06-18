# final-security

## 介绍
final-security，一个基于RBAC，专注于授权认证的轻量级框架<br/>

## 返璞归真
云淡风轻，回归真我，专注其一，`final-security` 一心追寻认证授权的真我。

# 01.快速入门

`final-security 前提条件：java 7+`

## 依赖
### 一、spring 体系中
克隆项目，`git clean https://gitee.com/lingkang_top/final-security.git`
执行打包`mvn package` 得到`final-security-core.jar` ，也可以从gitee发版上下载
> final-security 依赖于web，将spring-web与final-security一并引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-security-core</artifactId>
    <version>2.0.2</version>
</dependency>
```

快速开始：`final-security`
```java
@EnableFinalSecurity // 开启 FinalSecurity
@Configuration
public class Myconfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        // 对项目进行配置
        properties.checkAuthorize()
                .pathMatchers("/user").hasAnyRole("user", "vip1") // 有其中任意角色就能访问
                .pathMatchers("/vip/**").hasAllRole("user", "vip1");// 必须同时有所有角色才能访问
    }
}
```
`更多配置请查看 FinalConfigProperties 类`
> 默认所有请求都能通过

### 二、传统 servlet 中
引入依赖
```xml
<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-security-core</artifactId>
    <version>2.0.2</version>
</dependency>
```
配置 `web.xml`
```xml
<filter-mapping>
    <filter-name>securityFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
<filter>
    <filter-name>securityFilter</filter-name>
    <filter-class>top.lingkang.example_servlet.FinalSecurityConfig</filter-class>
</filter>
```
配置类
```java
public class FinalSecurityConfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        // 对项目进行配置
        properties.checkAuthorize()
                .pathMatchers("/user").hasAnyRole("user", "vip1") // 有其中任意角色就能访问
                .pathMatchers("/vip/**").hasAllRole("user", "vip1") // 必须有所有角色才能访问
                .pathMatchers("/about").hasLogin();// 需要登录才能访问
    }
}
```


## 1、登录
自动装配 `FinalSecurityHolder` 持有者进行操作
```java
@Autowired
private FinalSecurityHolder securityHolder;
```

登录username通常指用户唯一username，化繁为简，在web中会生成对应的会话，`final-security`底层基于session验证
```java
@Autowired
private FinalSecurityHolder securityHolder;

@GetMapping("login")
public Object login() {
    securityHolder.login("asd", new String[]{"user"});
    securityHolder.getUsername();// 获取会话用户名 string
    securityHolder.getRole(); // 获取会话中的角色 array
    securityHolder.isLogin(); // 判断当前会话是否登录 boolean
    return "login-success";
}
```
#### 在 servlet 中
```java
// 实例化
private FinalSecurityHolder securityHolder=new FinalSecurityHolder();
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // 直接使用 FinalSecurityHolder 后面不再赘述
    securityHolder.login("zhangsan",null);
    // ...
}
```

> 登录后即可访问其他资源

## 2、注销

注销当前用户username

```java
// 注销当前会话
@GetMapping("logout")
public Object logout() {
    securityHolder.logout();
    return "ok";
}
```

## 3、对路径进行鉴权
```java
@EnableFinalSecurity // 开启 FinalSecurity
@Configuration
public class Myconfig extends FinalSecurityConfiguration {
    @Override
    protected void config(FinalHttpProperties properties) {
        properties.checkAuthorize()
                .pathMatchers("/user").hasAnyRole("user", "vip1") // 有其中任意角色就能访问
                .pathMatchers("/vip/**").hasAllRole("user", "vip1") // 必须有所有角色才能访问
                .pathMatchers("/about").hasLogin();// 需要登录才能访问

        // 排除鉴权路径匹配, 匹配优先级别：排除路径 > checkAuthorize > 注解
        properties.setExcludePath("/login", "/logout", "/vip/total", "/vip/user/**");
    }
}
```
> 通过指定路径，路径通配符等进行角色权限鉴权。注意，排除路径会使checkAuthorize失效。优先等级：排除路径 > checkAuthorize > 注解

### 前端解析视图中获取用户、角色
final-security依赖session，直接从session中读取即可。在`jsp`中
```html
is login：${sessionScope.final_isLogin}<br/>
username：${sessionScope.final_loginUsername}<br/>
role：<br/>
${sessionScope.final_hasRoles}
<br/>
<%=Arrays.toString((String[]) request.getSession().getAttribute("final_hasRoles"))%>
<br/>
```
![pay](https://gitee.com/lingkang_top/final-security/raw/master/document/fontend-servlet.png)

在`Thymeleaf`中
```html
是否登录了：[[${session.final_isLogin}]]
<br>
登录的用户：[[${session.final_loginUsername}]]
<br>
用户拥有的角色：[[${session.final_hasRoles}]]
<br>
<!-- Thymeleaf 遍历-->
<span th:each="item:${session.final_hasRoles}">[[${item}]]，</span>
```
![pay](https://gitee.com/lingkang_top/final-security/raw/master/document/fontend-springboot.png)

## 4、使用注解进行鉴权
`final-security`没有引入AOP注解所需依赖，需要手动引入AOP依赖，否则将会报AOP包类找不到异常。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
开启注解：
```java
@EnableFinalSecurity // 开启 FinalSecurity
@EnableFinalSecurityAnnotation // 开启注解
@Configuration
public class Myconfig extends FinalSecurityConfiguration {
    // ...
}
```
使用注解作用于`controller`上
```java
    // 检查登录情况
    @FinalCheckLogin
    @GetMapping("/")
    public Object index() {
        return "index";
    }
    
    // 通过角色权限检查
    @FinalCheck(orRole = "admin",andRole = {"admin","system"})
    @GetMapping("/")
    public Object index() {
        return "index";
        }
```
作用于`service`上
```java
@Service
public class UserServiceImpl implements UserService {
    @FinalCheck(orRole = "user")
    @Override
    public String getNickname() {
        return "lingkang";
    }
}
```
作用在类上
```java
@FinalCheck(orRole = "user")
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getNickname() {
        return "lingkang";
    }

    @FinalCheck(orRole = "admin")
    @Override
    public String getUsername() {
        return "asd";
    }
}
```
AOP切面的异常需要`手动捕获`
```java
@RestControllerAdvice
public class ErrorAopHandler {
    @ExceptionHandler(FinalBaseException.class)
    public void finalBaseException(FinalBaseException e, HttpServletRequest request, HttpServletResponse response) {
        // 异常处理
    }
}
```

# 02.自定义
默认配置可能不满足实际场景需求，这里介绍了final-security的自定义功能。

### 自定义异常处理
final-security的所有异常处理均在接口 `FinalExceptionHandler` 处理，可实现它进行自定义。
```java
properties.setExcludePath(new String[]{"/login", "/logout"});
// 自定义异常处理
properties.setExceptionHandler(new FinalExceptionHandler() {
    @Override
    public void permissionException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        // 异常处理
    }

    @Override
    public void notLoginException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        // 异常处理
    }
    
    @Override
    public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        // 异常处理
    }
});
```
> 常见的自定义有重定向到登录、未授权响应等。

### 记住我remember

可以通过自定义异常处理来达到记住我remember。
<br/>例如登录时先将rememberToken存储到静态map（数据库）中，记录时间，自定义异常时判断map（数据库）中是否存在remember，然后重新登录并再次转发当前请求即可。


# 03.集群
final-security依赖session，因此整合分布式会话可以轻松实现无限扩展集群。
## 通过 final-session 实现集群
`final-session` 是一个轻量级分布式session框架，它可以无限水平扩展你的集群。
`gitee`:https://gitee.com/lingkang_top/final-session
<br>
引入依赖
```xml
<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-session-core</artifactId>
    <version>2.0.1</version>
</dependency>
```
配置
```java
@Order(-19951219)
@Component
public class MyFinalSessionConfig extends FinalSessionConfigurerAdapter {
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Override
    protected void configurer(FinalSessionProperties properties) {
        // 默认会话存储于内存中，下面将会话存储于redis中，需要引入RedisTemplate依赖
        properties.setRepository(new FinalRedisRepository(redisTemplate));
    }
}
```

# ~~04.打包~~
~~注意，springboot的`spring-boot-maven-plugin`插件打包需要配置将system作用域的依赖打进入项目~~
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <id>repackage</id>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```


## 其他
有问题issues，也可以邮箱：**ling-kang@qq.com**
<br><br>也能请我喝冰可乐：
<br>
![pay](https://gitee.com/lingkang_top/final-security/raw/master/document/pay.png)
<br><br>