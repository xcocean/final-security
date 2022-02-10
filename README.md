# final-security

## 介绍
final-security，一个基于spring、专注于认证授权的轻量级框架<br/>

## 返璞归真
云淡风轻，回归真我，专注其一，final-security一心追寻认证授权的真我。


# 01.快速入门

`final-security 前提条件：java 7+ spring 5以上`

## 依赖
克隆项目，`git clean https://gitee.com/lingkang_top/final-security.git`
执行打包`mvn package` 得到`final-security-core.jar`
> final-security 依赖于spring-web，将spring-web与final-security一并引入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>top.lingkang</groupId>
    <artifactId>final-security-core</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/final-security-core-1.0.0.jar</systemPath>
</dependency>
```

## 前言

`final-security`使用前需要先配置排除路径：

```yaml
final:
  security:
    exclude-path: /login,/logout
```

或者代码中：

```aidl
@Configuration
public class FinalConfig {
    @Bean
    public FinalConfigProperties configProperties(){
        FinalConfigProperties properties=new FinalConfigProperties();
        properties.setExcludePath(new String[]{"/login","/logout"});
        return properties;
    }
}
```
`更多配置请查看 FinalConfigProperties 类`
> 不配置排除路径，所有请求都无法通过。

## 1、登录
首先注入FinalHolder持有者进行操作
```java
@Autowired
private FinalHolder finalHolder;
```

登录id通常指用户唯一id，也可以使用唯一的用户名做登录id，化繁为简，final-security认为登录是准备好角色权限和一些用户的信息数据；在web中会生成对应的会话，通过cookie维持
```java
@Autowired
private FinalHolder finalHolder;

@GetMapping("login")
public Object login(){
    finalHolder.login("lk", null, null, null);// 登陆
    // finalHolder.login("lk", user, new String[]{"admin","system"}, null);// 登陆
    return"index";
}
```

> 登录后即可访问其他资源

## 2、注销

注销当前用户id或指定用户id

```java
@Autowired
private FinalHolder finalHolder;

// 注销当前会话
finalHolder.logout();
// 注销指定id
// finalHolder.logoutById(String id);
```

## 3、对路径进行鉴权
```java
    @Bean
    public FinalHttpSecurity httpSecurity(){
        FinalHttpSecurity security=new DefaultFinalHttpSecurity();
        HashMap<String, FinalAuth> map = new HashMap<>();
        map.put("/user", new FinalAuth().hasRoles("user"));// 必须拥有user角色
        map.put("/about", new FinalAuth().hasPermission("get"));// 必须拥有get权限
        map.put("/updatePassword", new FinalAuth().hasRoles("user").hasPermission("update"));// 需要拥有user角色和update权限
        map.put("/index", new FinalAuth().hasRoles("admin", "system").hasPermission("get"));// 至少有一个角色并拥有get权限
        map.put("/vip/**", new FinalAuth().hasAllRoles("user","vip"));// 需要同时拥有角色
        security.setCheckAuths(map);
        return security;
    }
```
> 通过指定路径，路径通配符等进行角色权限鉴权

## 4、使用注解进行鉴权
final-security不会帮你引入aop注解所需依赖，需要你手动引入aop依赖，否则将会报aop包类找不到异常。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
使用注解作用于controller上
```java
    // 检查登录情况
    @FinalCheckLogin
    @GetMapping("/")
    public Object index() {
        return "index";
    }
    
    // 通过角色权限检查
    @FinalCheck(orRole = "admin",andRole = {"admin,system"},orPermission = "get")
    @GetMapping("/")
    public Object index() {
        return "index";
        }
```
作用于service上
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

<br/><br/><br/>
> final-security 一如既往简洁、简单、专一。