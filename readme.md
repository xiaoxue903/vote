## 一、项目架构与环境要求

### 1、项目架构

- SpringBoot
- MyBatisPlus
- Redis
- Swagger

### 2、环境要求

- IDEA
- Maven3.6
- MySQL8.0
- Redis

### 3、环境安装

#### 1、Redis

https://blog.xueqimiao.com/redis/37e5da/#_2%E3%80%81%E5%AE%89%E8%A3%85

#### 2、MySQL

https://blog.xueqimiao.com/docker/cf2853/#_2%E3%80%81%E5%AE%89%E8%A3%85mysql

## 二、启动说明

### 1、配置修改

application-dev.yml

### 2、加密方法

```java
public class JasyptTest {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的密钥
        textEncryptor.setPassword("xiaoxueblog");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("mac_root");
        //String decrypt = textEncryptor.decrypt("83T+oQSPVtjizgExNQCo0yplG39laem/");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }
}
```



### 3、一些注解与配置类

- @EnableGlobalException
  - 开启全局异常
- @EnableAnnotationPage
  - 开启注解分页
- @EnableXXTransactionManagement
  - 开启自定义事务
- @EnableCustomSwagger2
  - 开启swagger
- 全局异常类
  - GlobalExceptionHandler
  
- 启动类
  - com.xx.vote.Application



##  三、一些使用方法

### 1、分页

```java
@NeedPage
@ApiOperation(value = "获取列表")
@ApiImplicitParams({
    @ApiImplicitParam(name = "currentPage", value = "当前页", paramType = "query", dataType = "Integer"),
    @ApiImplicitParam(name = "pageSize", value = "一页个数", paramType = "query", dataType = "Integer")
})
@GetMapping("/getList")
public ResultData<CandidateVO> getList() {
    return candidateService.getList();
}
```





## 四、部署

```sh
clean package -s "/settings.xml"  -Dmaven.test.skip=true
```

```dockerfile
FROM openjdk:8-jdk-alpine
LABEL maintainer="919417579@qq.com"
#复制打好的jar包
COPY target/*.jar /app.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone; \
touch /app.jar;

ENV JAVA_OPTS=""
ENV PARAMS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar /app.jar $PARAMS" ]
```

```sh
docker run -m 1200M --name=xx-admin -v /logs/xx-admin/:/logs/xx-admin/ -p 8080:8080 -e PARAMS=\"--spring.profiles.active=prod" -d xx-admin:1.0
```



