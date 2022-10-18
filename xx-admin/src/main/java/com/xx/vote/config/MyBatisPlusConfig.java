package com.xx.vote.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    /**
     * 自动插入创建、更新时间和人员信息
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new GlobalMetaObjectHandler();
    }

}