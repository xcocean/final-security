# final-security

## 介绍
一个基于spring boot 2.x 轻量级权限认证框架<br/>
## 区分
关于final-security的session与运行容器（tomcat等）的 JSESSIONID 关系：final-security维护的session与tomcat维护的会话相互独立开。<br/>
final-security的FinalSession是request的封装，实际应用中请使用FinalSession

## 使用
拉取项目，使用Maven打包，将`final-security-core-1.0.0.jar`引入springboot项目
```
// 登录 建议id为全局唯一
FinalContextHolder.login(String.valueOf(user.getId()));

// 注销 当前用户
FinalContextHolder.logout();

// 注销 指定用户 ，token为指定用户token
FinalContextHolder.logout(token);

// 获取会话
FinalContextHolder.getFinalSession()
```

## 基本配置
1、配置鉴权路径和排除路径
```
    @Bean
    public FinalHttpSecurity finalHttpSecurity() {
        FinalHttpSecurity security = new FinalHttpSecurity();
        HashMap<String, CheckAuth> map = new HashMap<String, CheckAuth>();
        map.put("/*", new DefaultCheckAuth().checkLogin());
        security.setCheckAuthHashMap(map);
        security.setExcludePath("/login", "/assets/**");
        return security;
    }
```
2、properties配置
```
# 配置 token 名称
final.security.token-name=token

# 配置排除路径 优先级：Bean 配置大于文件配置
final.security.exclude-path=/login,/assets/**

# 配置 会话（token） 失效时间 单位 毫秒 默认30分钟
final.security.session-max-valid=1800000

# 配置每次访问是否更新 session 失效时间 默认开启
final.security.access-update-session-time=true
```
3、配置会话监听
```
@Component
public class MySessionListener implements SessionListener {
    public void create(String id, String token) {
        System.out.println("session-id创建" + token);
    }

    public void delete(FinalSession finalSession) {
        System.out.println("session-token删除" + finalSession.getToken());
    }
}
```
4、自定义token的值生成
```
    @Bean
    public FinalTokenGenerate generate(){
        return new FinalTokenGenerate() {
            @Override
            public String generateToken() {
                return UUID.randomUUID().toString();
            }
        };
    }
```
5、自定义鉴权异常处理
```
@Component
public class MyFinalExceptionHandler implements FinalExceptionHandler {
    public void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        response.getWriter().print("{\"code\":403,\"message\":\"" + e.getMessage() + "\"}");
    }

    public void tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        response.getWriter().print("{\"code\":401,\"message\":\"" + e.getMessage() + "\"}");
    }

    public void permissionException(FinalPermissionException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        response.getWriter().print("{\"code\":401,\"message\":\"" + e.getMessage() + "\"}");
    }
}
```
## token存储配置
添加依赖 `final-security-data` 、`spring-boot-starter-data-redis`
```
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public FinalRedisSessionManager finalRedisSessionManager() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return new FinalRedisSessionManager(redisTemplate);
    }
```

## oauth 支持
oauth支持配置如下：
```
# 关闭访问session更新
final.security.access-update-session-time=false
```
token相关
```
// 获取token的失效时间
FinalContextHolder.getSessionExpire()
// 更新当前用户的会话失效时间
FinalContextHolder.updateSessionExpire()
```