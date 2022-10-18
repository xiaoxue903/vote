package com.xx.vote;

import com.xx.annoation.EnableGlobalException;
import com.xx.utils.IPUtils;
import com.xx.vote.annotation.EnableAnnotationPage;
import com.xx.vote.annotation.EnableDruidDbFieldEncryptConfig;
import com.xx.vote.annotation.EnableXXTransactionManagement;
import org.mybatis.spring.annotation.MapperScan;
import com.xx.swagger.annotation.EnableCustomSwagger2;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan(basePackages = "com.xx.vote.mapper")
@EnableCustomSwagger2
@EnableGlobalException
@EnableAnnotationPage
// 有点小问题 不开启
//@EnableDruidDbFieldEncryptConfig
@EnableXXTransactionManagement
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
