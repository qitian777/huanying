# 0、简介

此为huanying项目springcloud部分集群化升级版本，同时复习以前学过的springcloud知识。本项目使用了cloud的Eureka、Ribbon、Hystrix、gateway组件。负载均衡使用了Ribbon，因此没有用到Fegin。

相较于原项目新增了两个eureka-serve服务器模块、一个item-cloud-provider模块和一个gateway网关模块（zuul因版本问题要降级太麻烦放弃）。item-cloud-provider模块中添加Hystrix服务降级和熔断功能。

# 1、Eureka

使用的cloud版本

```
<!--springCloud的依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>2020.0.5</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

## 1.1、简单实现

### 1.1.1、Eureka服务器

新建eureka-server模块，导入依赖：

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

修改启动类：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/6/18
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaServer //开启eureka服务器
public class EurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class,args);
    }
}
```

application.yml配置：

```yaml
server:
  port: 7001

# eureka配置
eureka:
  instance:
    hostname: localhost # eureka服务端实例名称
  client:
    register-with-eureka: false # 表示是否向rureka注册中心注册自己
    fetch-registry: false # fetch-registry为false表示自己就是注册中心
    service-url:  # 监控页面
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false # 关闭Eureka保护配置，生产环境不建议关

spring:
  application:
    name: eureka-server
```

### 1.1.2、生产者

新建item-cloud-provider生产者模块

导入依赖：

```xml
<!-- spring-cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

修改启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //服务提供者身份启动
public class ItemCloudProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemCloudProviderApplication.class, args);
    }
}
```

application.yml配置：

```yaml
# Eureka配置：配置服务注册中心地址
eureka:
  client:
    service-url:
#      defaultZone: http://localhost:7001/eureka/  #注册中心url
    register-with-eureka: true
    fetch-registry: true
    
#设置端口号
server:
  port: 8001
```

controller控制器接口：

```java
@RestController
@RequestMapping("/collection")
@Api(tags = "收藏控制器")
public class UserCollController {
    @Autowired
    private IUserCollService userCollService;

    @ApiOperation(value = "收藏分类列表")
    @PostMapping("/list")
    public RespBean getList(@Valid @RequestBody CloudItemVo cloudItemVo) {
        return RespBean.success(userCollService.getCollSortList(cloudItemVo));
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add")
    public RespBean addColl(@Valid @RequestBody UserColl userColl) {
        if (userCollService.addColl(userColl)>0) return RespBean.success(RespBeanEnum.ADD_COLLECTION_SUCCESS);
        return RespBean.error(RespBeanEnum.ADD_COLLECTION_FAIL);
    }

    @ApiOperation(value = "删除收藏")
    @PostMapping("/delete")
    public RespBean deleteColl(@Valid @RequestBody UserColl userColl) {
        if (userCollService.deleteColl(userColl)>0) return RespBean.success(RespBeanEnum.DELETE_COLLECTION_SUCCESS);
        return RespBean.error(RespBeanEnum.DELETE_COLLECTION_FAIL);
    }

    @ApiOperation(value = "确认是否已收藏")
    @PostMapping("/check")
    public RespBean checkColl( @Valid @RequestBody UserColl userColl) {
        return RespBean.success(userCollService.checkColl(userColl));
    }
}
```

### 1.1.3、消费者

在user-server模块中导入依赖

```xml
<!-- spring-cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

修改启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient  //服务提供者身份启动
public class UserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }
}
```

application.yml配置

```yaml
# Eureka配置：配置服务注册中心地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka    #注册中心url
    fetch-registry: true
    register-with-eureka: false
server:
  port: 8082
```

配置RestTemplate模板

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 注入RestTemplate
 * @Author QiGuang
 * @Date 2022/6/18
 * @Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

远程调用服务

```java
@RestController
@RequestMapping("/userColl")
@Api(tags = "收藏，cloud消费者")
public class CollectionController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url="http://localhost:8001/collection";

    @ApiOperation(value = "收藏分类列表")
    @PostMapping("/list")
    public Object getList(@Valid @RequestBody CloudItemVo cloudItemVo) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cloudItemVo.setUserId(user.getId());
        return restTemplate.postForObject(url + "/list", cloudItemVo, Object.class);
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add")
    public Object addColl(@Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/add", userColl, Object.class);
    }

    @ApiOperation(value = "删除收藏")
    @PostMapping("/delete")
    public Object deleteColl(@Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/delete", userColl, Object.class);
    }

    @ApiOperation(value = "确认是否收藏")
    @PostMapping("/check")
    public Object checkColl( @Valid @RequestBody UserColl userColl) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userColl.setUserId(user.getId());
        return restTemplate.postForObject(url + "/check", userColl, Object.class);
    }
}
```

## 1.2、集群部署

### 1.2.0、准备工作

找到本机hosts文件（C:\Windows\System32\drivers\etc，修改需要管理员权限）并打开，在hosts文件最后加上要访问的本机名称，默认是localhost

```tex
//修改本地 hosts 文件为 

127.0.0.1       eureka7001.com

127.0.0.1       eureka7002.com 

127.0.0.1       eureka7003.com
```

 

### 1.2.1、Eureka服务器

新建eureka-server-7002、eureka-server-7003 模块，参考eureka-server模块。

**注意配置eureka-server-7002端口为7002、eureka-server-7003 端口为7003**

**修改service-url，让eureka-server关联到eureka-server-7002、eureka-server-7003 ，同时在集群中使eureka-server-7002关联eureka-server、eureka-server-7003，使eureka-server-7003关联eureka-server、eureka-server-7002。**

```yaml
server:
  port: 7001

# eureka配置
eureka:
  instance:
    hostname: localhost # eureka服务端实例名称
  client:
    register-with-eureka: false # 表示是否向rureka注册中心注册自己
    fetch-registry: false # fetch-registry为false表示自己就是注册中心
    service-url:  # 监控页面
      defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
#      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
  server:
    enable-self-preservation: false # 关闭Eureka保护配置，生产环境不建议关

spring:
  application:
    name: eureka-server
```

### 1.2.2、生产者

新建item-cloud-provider02模块，参考item-cloud-provider，注意修改接口。

# 2、Ribbon：负载均衡(基于客户端)

消费者（user-server模块）导入依赖（实际未导入该依赖也能使用相关功能，可能eureka-client中已集成）

```xml
<!--Ribbon-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
</dependency>
```

修改RestTemplate配置类，添加@LoadBalanced注解

```java
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 注入RestTemplate
 * @Author QiGuang
 * @Date 2022/6/18
 * @Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    @LoadBalanced //支持负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

修改远程调用中url，用生产者中自定义的应用名替换域名即可实现负载均衡

```java
@RestController
@RequestMapping("/userColl")
@Api(tags = "收藏，cloud消费者")
public class CollectionController {
    @Autowired
    private RestTemplate restTemplate;

//    private static final String url="http://localhost:8001/collection";
    // 使用应用名获取服务
    private static final String url="http://item-cloud-provider/collection";

    @ApiOperation(value = "收藏分类列表")
    @PostMapping("/list")
    public Object getList(@Valid @RequestBody CloudItemVo cloudItemVo) {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cloudItemVo.setUserId(user.getId());
        return restTemplate.postForObject(url + "/list", cloudItemVo, Object.class);
    }

......
```

# 3、 Hystrix：服务熔断

## 3.1、简单案例

生产者导入依赖

```xml
<!-- spring-cloud熔断 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
```

修改启动类，添加@EnableHystrix注解开启熔断

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix // 开启熔断
@EnableEurekaClient
public class ItemCloudProvider02Application {
    public static void main(String[] args) {
        SpringApplication.run(ItemCloudProvider02Application.class, args);
    }
}
```

常用配置

```yaml
hystrix:
  metrics:
    enabled: true   # 启用Hystrix指标轮询，默认值为true
    polling-interval-ms: 2000   # 后续轮询度量之间的间隔，默认值为2000
```

controller中接口添加注解和回调方法

```java
@RestController
@RequestMapping("/collection")
@Api(tags = "收藏控制器")
public class UserCollController {
    @Autowired
    private IUserCollService userCollService;

    /**
     * @Author QiGuang
     * @Description HYSTRIX失败时的回调函数
     * @Param
     */
    public RespBean fallback(@Valid @RequestBody CloudItemVo cloudItemVo){
        System.out.println("------------HYSTRIX_ERROR-----------");
        return RespBean.error(RespBeanEnum.HYSTRIX_ERROR);
    }

    @ApiOperation(value = "收藏分类列表")
    // 声明一个失败回滚处理函数
    @HystrixCommand(fallbackMethod = "fallback")
    @PostMapping("/list")
    public RespBean getList(@Valid @RequestBody CloudItemVo cloudItemVo) throws InterruptedException {
        System.out.println("------------02 provider-----------");
        Thread.sleep(5000);
        return RespBean.success(userCollService.getCollSortList(cloudItemVo));
    }
    ......
}
```

## 3.2、配置

大部分场景下使用默认属性即可，不需要配置那么多属性，更多的属性可以参考：https://github.com/Netflix/Hystrix/wiki/Configuration

### 3.2.1、@HystrixCommand

```java
@RequestMapping("/getToOrderMemberAll")
@HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")})
public List<String> getToOrderMemberAll() throws Exception {
    System.out.println("memberFegin 开始调用member工程。。。" + count++);
    return memberFeign.getToOrderMemberAll();
}
```

现在使用的配置大概有如下几种：

```java
@HystrixCommand(groupKey = "hello",commandKey = "hello-service",threadPoolKey = "hello-pool",
        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "30"),
                @HystrixProperty(name = "maxQueueSize", value = "101"),
                @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
        },
        commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
        @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD")},
        fallbackMethod = "helloFallBack"
        )

```

### 3.2.2、@DefaultProperties

一个类中有很多方法，不能在每个方法都配置一遍相同的属性，容易造成配置代码的冗余；所以Javanica提供了 @DefaultProperties注解，解释如下：

​	1、@DefaultProperties是类（类型）级别的注释，允许默认命令属性，如groupKey，threadPoolKey，commandProperties，threadPoolProperties，ignoreExceptions和raiseHystrixExceptions。 
​	2、使用此注解指定的属性，将在类中使用@HystrixCommand注解的方法中公用 ，除非某个方法明确使用相应的@HystrixCommand参数来指定这些属性。

**在Controller上使用DefaultProperties注解的defaultFallback指定统一降级方法**

```java
@RestController
@RequestMapping("/order")
/**
 * @DefaultProperties : 指定此接口中公共的熔断设置
 *      如果过在@DefaultProperties指定了公共的降级方法
 *      在@HystrixCommand不需要单独指定了
 */
@DefaultProperties(defaultFallback = "defaultFallBack")
public class OrderController {
    /**
     * 指定统一的降级方法
     *  * 参数 : 没有参数
     *注意返回值，所有方法的返回值都要相同
     */
    public Product defaultFallBack() {
        Product product = new Product();
        product.setProductName("触发统一的降级方法");
        return product;
    }
    //这些方法的降级方法都是defaultFallBack方法
    public Product orderFallBack(Long id) {...}
    public Product orderFallBack2(Long id) {...}
}
```

### 3.2.3、fallback方法，回退逻辑及异常

1、定义一个fallback方法需要注意以下几点：
（1）fallback方法必须和指定fallback方法的主方法在一个类中
（2）fallback方法的参数必须要和主方法的参数一致
否则不生效
（3）使用fallback方法需要根据依赖服务设置合理的超时时间，即
commandProperties = {
@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")}
如果不配置的话默认是1s，如果依赖服务的响应时间超过1s，则执行fallback；所以需要根据实际情况而定

2、可以在fallback方法中通过Throwable e参数获得主方法抛出的异常，例如：
@HystrixCommand(fallbackMethod = "fallback1")
        User getUserById(String id) {
            throw new RuntimeException("getUserById command failed");
        }

```java
    @HystrixCommand(fallbackMethod = "fallback2")
    User fallback1(String id, Throwable e) {
        assert "getUserById command failed".equals(e.getMessage());
        throw new RuntimeException("fallback1 failed");
    }

    @HystrixCommand(fallbackMethod = "fallback3")
    User fallback2(String id) {
        throw new RuntimeException("fallback2 failed");
    }

    @HystrixCommand(fallbackMethod = "staticFallback")
    User fallback3(String id, Throwable e) {
        assert "fallback2 failed".equals(e.getMessage());
        throw new RuntimeException("fallback3 failed");
    }

    User staticFallback(String id, Throwable e) {
        assert "fallback3 failed".equals(e.getMessage());
        return new User("def", "def");
    }
    
    // test
    @Test
    public void test() {
    assertEquals("def", getUserById("1").getName());
    }
```

# 4、路由网关

## 4.1、zuul（失败）

引入依赖

```xml
<!-- 热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<!-- spring-cloud zuul</网关 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    <version>2.2.6.RELEASE</version>
</dependency>
<!-- spring-cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

配置

```yaml
server:
  port: 9527

spring:
  application:
    name: springcloud-zuul #微服务名称

# eureka 注册中心配置
eureka:
  client:
    service-url:
#      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
      defaultZone: http://localhost:7001/eureka/
  instance: #实例的id
    instance-id: eureka9527.com
    prefer-ip-address: true # 显示ip
info:
  app.name: tian.springcloud # 项目名称
  company.name: tiantian # 公司名称
```

启动类

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class SpringcloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudZuulApplication.class, args);
    }

}
```

不知道是否是版本问题，访问zuul代理控制台总是报错：

**com.netflix.client.ClientException: Load balancer does not have available server for client**

在网上找了各种方法均无法解决，鉴于新版springboot对zuul的支持不是很好，决定改用gateway

## 4.2、gateway

导入依赖

```xml
<!-- 热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
<!-- spring-cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!-- spring-cloud -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

yml配置

```yaml
server:
  port: 9009
spring:
  cloud:
    gateway:
      routes:
        - id: service-hi
          uri: lb://item-cloud-provider # lb 表示负载均衡loadbalance，item-cloud-provider是服务名称
          predicates:
            - Path=/demo/** # 所有demo开头的请求走这个路由
          filters:
            - StripPrefix=1 # 转发之前将/demo去掉
  application:
    name: spring-cloud-gateway
# eureka 注册中心配置
eureka:
  client:
    service-url:
      #      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
      defaultZone: http://localhost:7001/eureka/
```

启动后访问代理http://127.0.0.1:9009/demo//collection/test成功
