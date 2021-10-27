# c-uaa-sdk-starter

> 基于Spring security的安全认证服务的框架支持，使用jwt作为认证token，实现精准的接口级权限控制

## 1 配置仓库

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <mirrors>
        <mirror>
            <id>maven-releases</id>
            <mirrorOf>*</mirrorOf>
            <url>http://repo.ionehe.com/repository/maven-public/</url>
        </mirror>
        <mirror>
            <id>maven-snapshots</id>
            <mirrorOf>*</mirrorOf>
            <url>http://repo.ionehe.com/repository/maven-public/</url>
        </mirror>
    </mirrors>


    <profiles>
        <profile>
            <id>nexusProfile</id>
            <repositories>
                <repository>
                    <id>nexus</id>
                    <name>Nexus Public Repository</name>
                    <url>http://repo.ionehe.com/repository/maven-public/</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                        <updatePolicy>always</updatePolicy>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>nexusProfile</activeProfile>
    </activeProfiles>
</settings>
```

## 2 maven配置

```text
    <dependency>
        <groupId>com.ionehe.public</groupId>
        <artifactId>c-uaa-sdk-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

## 3 配置文件配置示例

### 3.1 application.yml jwt策略配置

```yaml
jwt:
  tokenHeader: Authorization #JWT存储的请求头 (勿修改)
  secret: demo-secret # 自定义JWT加解密使用的密钥
  expiration: 604800 # 自定义JWT的超期限时间(60*60*24*7)
  tokenHead: 'Bearer '  # JWT负载中拿到开头 (勿修改)
```

### 3.2 privilege.yml 匿名访问接口配置

```yaml
privilege:
  # anonymous access 匿名可访问
  anonymous:
    - path: /swagger-ui.html
      methods: post,get
    - path: /swagger-resources/**
      methods: post,get
    - path: /swagger/**
      methods: post,get
    - path: /**/v2/api-docs
      methods: post,get
    - path: /webjars/springfox-swagger-ui/**
      methods: post,get
    - path: /login
      methods: post
    - path: /test/anonymous
      methods: get
  # anonymous access 登陆可访问
  authentication:
    - path: /test/authentication
      methods: get
```

## 4 核心配置类PermissionsSecurityConfig.java

```java

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class PermissionsSecurityConfig extends SecurityConfig {
    @Autowired
    private UserReadService userReadService;

    /**
     * 获取用户信息（用户名+用户权限集合）
     *
     * @return 返回一个用户对象给spring security进行认证鉴权
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        return username -> userReadService.loadUserByUsername(username).getData();
    }

    /**
     * TODO
     * 加载需要鉴权的资源信息
     *
     * @return 需要鉴权的资源信息
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            // mock数据库录入的资源列表
            List<ConfigAttribute> list = mockConfigAttributeMap();
            log.info("PermissionsSecurityConfig[]resource:{}", list);
            return list;
        };
    }


    /**
     * mock数据库录入的资源列表
     *
     * @return 资源列表
     */
    private List<ConfigAttribute> mockConfigAttributeMap() {
        List<ConfigAttribute> list = new ArrayList<>(2);
        list.add(new org.springframework.security.access.SecurityConfig("/test/list"));
        list.add(new org.springframework.security.access.SecurityConfig("/test/add"));
        return list;
    }
}
```

## 5 LoginController：登陆成功返回token，token中包含【用户名、token创建时间和过期时间】

```java

@Slf4j
@RestController
@RequestMapping("/login")
@Api(tags = "登陆服务", value = "登陆服务api")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserReadService userReadService;

    @ApiOperation("登陆成功后返回token")
    @PostMapping
    public Response<Map<String, String>> login(@RequestBody LoginUser user) {
        String token;
        try {
            // 密码需要客户端加密后传递
            UserDetails userDetails = userReadService.loadUserByUsername(user.getUsername()).getData();
            passwordCheck(user.getPassword(), userDetails.getPassword());

            // 存储到spring security上下文中
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成token
            token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> tokenMap = new HashMap<>(2);
            tokenMap.put("tokenHead", "Bearer ");
            tokenMap.put("token", token);
            return Response.yes(tokenMap);
        } catch (AuthenticationException e) {
            log.warn("LoginController[]login[]error! cause:{}", e.getMessage());
            return Response.no(e.getMessage());
        } catch (Exception e) {
            log.error("LoginController[]login[]error! cause:{}", Throwables.getStackTraceAsString(e));
            return Response.no("登陆异常");
        }
    }

    /**
     * 密码校验
     *
     * @param pw       用户输入密码
     * @param password 数据库密码
     */
    private void passwordCheck(String pw, String password) {
        if (!passwordEncoder.matches(pw, password)) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
    }
}
```

## 6 c-uaa-sdk-starter-demo使用详细代码示例，附c-uaa-sdk-starter源码

### 6.1 代码仓库

[gitee](https://gitee.com/ionehe/c-uaa-sdk-starter)

[github](https://github.com/ionehe/c-uaa-sdk-starter)

### 6.2 c-uaa-sdk-starter-demo代码结构说明

```text
├── AppRun.java
├── application # 应用层
│   ├── rest
│   │   ├── LoginController.java    # 内含登陆接口示例，登陆成功返回token
│   │   └── test
│   │       └── TestController.java # 权限测试接口，提供测试接口，验证权限控制
│   └── vo
│       └── LoginUser.java  # 用户登陆表单对应的实体类
├── domain  # 领域层
│   ├── dto
│   │   ├── CommonUserDetails.java  # 自定义实现UserDetails 该对象用于给spring security进行认证鉴权
│   │   └── UserInfo.java   # 自定义用户信息实体类
│   └── service
│       └── UserReadService.java    # 模拟用户读相关服务，至少提供用户名查询用户信息接口
└── infrastructure   # 基础设施层，对其它层提供基础配置和数据支持
    ├── config  # 配置相关
    │   ├── PermissionsSecurityConfig.java  # 权限框架核心配置
    │   ├── log
    │   │   └── LogServer.java
    │   └── swagger
    │       ├── BaseSwaggerConfig.java
    │       ├── SwaggerConfig.java
    │       └── SwaggerProperties.java
    └── repository
        └── module
            └── Resource.java
```

### 6.3 接口验证

[点击访问swagger](http://localhost:8888/swagger-ui.html#/)

![](https://gitee.com/ionehe/c-uaa-sdk-starter/raw/master/.README_images/1f6fbf33.png)

### 6.4 前端携带token访问接口

![](https://gitee.com/ionehe/c-uaa-sdk-starter/raw/master/.README_images/c288b104.png)

## changelog

- 框架权限容器由Map升级为List，允许一个用户下拥有多个相同的权限
