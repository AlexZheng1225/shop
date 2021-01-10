package com.alexzheng.onlineshop.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @Author Alex Zheng
 * @Date 2020/6/14 17:55
 * @Annotation 对应SSM的spring-service.xml配置中的TransactionManager
 * 继承xml配置中的TransactionManager是因为来气annotation-driven
 */
@Configuration
/**
 * 开启事务支持
 */
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    /**
     * 关于事务管理，需要返回TransactionManager的实现
     * @return
     */
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
