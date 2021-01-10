package com.alexzheng.onlineshop.config.dao;

import com.alexzheng.onlineshop.utils.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @Author Alex Zheng
 * @Date 2020/6/13 22:59
 * @Annotation 配置DataSource到IOC容器里面
 */
@Configuration
@MapperScan("com.alexzheng.onlineshop.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * 生成dataSource 类似之前ssm项目spring-dao.xml中的bean
     *
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        //生成datasource实例
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        //设置配置文件信息
        //驱动
        dataSource.setDriverClass(jdbcDriver);
        //数据库url
        dataSource.setJdbcUrl(jdbcUrl);
        //用户名
        dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
        //密码
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
        // 配置c3p0连接池的私有属性
        // 连接池最大线程数
        dataSource.setMaxPoolSize(30);
        // 连接池最小线程数
        dataSource.setMinPoolSize(10);
        dataSource.setInitialPoolSize(10);
        // 关闭连接后不自动commit
        dataSource.setAutoCommitOnClose(false);
        // 连接超时时间
        dataSource.setCheckoutTimeout(10000);
        // 连接失败重试次数
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }

}
