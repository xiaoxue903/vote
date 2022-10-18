## 1、引入pom

```xml
<dependency>
    <groupId>com.xue</groupId>
    <artifactId>xx-common-swagger</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 2、启动类加入注解

```java
@EnableCustomSwagger2
```

```java
@EnableCustomSwagger2
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
        Environment env = application.getEnvironment();
        String ip = IPUtils.getLocalIp4Address();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        System.out.print("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URL:\n\t" +
                "Local: \thttp://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------\n\n");
    }
}
```

## 3、加入配置

```yaml
# swagger配置
# 路径 /doc.html
swagger:
  enabled: true
  title: 小薛管理平台
  basePackage: com.xx.vote.controller
  description: 小薛管理平台接口文档
```

**可以根据 SwaggerProperties.java 这个类里面的配置进行修改**