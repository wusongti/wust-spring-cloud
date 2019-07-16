package com.wust.springcloud.admin.server.config;


import com.wust.springcloud.common.DynamicDataSource;
import com.wust.springcloud.common.enums.ApplicationEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by WST on 2019/6/20.
 */
@Configuration
@MapperScan(basePackages = {"com.wust.springcloud.admin.server.core.dao"})
public class MybatisConfig implements EnvironmentAware {
    private static Log logger = LogFactory.getLog(MybatisConfig.class);
    private Environment environment;

    @Bean
    DataSource dynamicDataSource() {
        logger.info("environment:" + environment);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        /**
         * 设置默认数据源
         */
        DataSource dataSource = dynamicDataSource.createDataSource(
                ApplicationEnum.DEFAULT.name(),
                environment.getProperty("spring.datasource.url"),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password"),
                environment.getProperty("spring.datasource.driver-class-name"));
        dynamicDataSource.setDefaultTargetDataSource(dataSource);

        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        sessionFactory.setTypeAliasesPackage("com.wust.springcloud.common.entity.**");    // 扫描Model
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/**/*Mapper.xml"));    // 扫描映射文件
        return sessionFactory;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }


    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
