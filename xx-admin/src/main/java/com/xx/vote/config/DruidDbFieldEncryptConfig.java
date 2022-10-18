package com.xx.vote.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xx.vote.db.AesMysqlSupport;
import com.xx.vote.db.Encrypt;
import com.xx.vote.interceptor.DaoMethodAspect;
import com.xx.vote.interceptor.DecryptReadInterceptor;
import com.xx.vote.interceptor.SensitiveAndEncryptWriteInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 15:16
 */
public class DruidDbFieldEncryptConfig {

    @Value("${mybatis-plus.mapper-locations}")
    private String mybatisMapperLocations;

    /**
     * 数据库加密key
     */
    @Value("${mybatis.encrypt.key}")
    private String dbEncryptKey;

    /**
     * 加密方式
     *
     * @return
     * @throws Exception
     */
    @Bean
    Encrypt encryptor() throws Exception {
        return new AesMysqlSupport(dbEncryptKey);
    }

    /**
     * mybatis查询条件信息加密插件初始化
     *
     * @return
     * @throws Exception
     */
    @Bean
    DaoMethodAspect daoMethodAspect() throws Exception {
        return new DaoMethodAspect(encryptor());
    }

    @Bean("druidMysqlDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidMysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }


    @Bean("mysqlSqlSessionFactory")
    @Primary
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("druidMysqlDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean ();
        DecryptReadInterceptor decryptReadInterceptor = new DecryptReadInterceptor(encryptor());
        SensitiveAndEncryptWriteInterceptor sensitiveAndEncryptWriteInterceptor = new SensitiveAndEncryptWriteInterceptor(encryptor());
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{decryptReadInterceptor, sensitiveAndEncryptWriteInterceptor});
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisMapperLocations));
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.getObject().getConfiguration().setCallSettersOnNulls(true);
        return sqlSessionFactoryBean.getObject();
    }
}
