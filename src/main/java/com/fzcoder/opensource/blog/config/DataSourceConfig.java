package com.fzcoder.opensource.blog.config;

import com.fzcoder.opensource.blog.config.autoconfigure.C3p0ConfigProperties;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
public class DataSourceConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired
    private C3p0ConfigProperties c3p0ConfigProperties;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(dataSourceProperties.getDriverClassName());
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUser(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setMinPoolSize(c3p0ConfigProperties.getMinPoolSize());
        dataSource.setMaxPoolSize(c3p0ConfigProperties.getMaxPoolSize());
        dataSource.setMaxIdleTime(c3p0ConfigProperties.getMaxIdleTime());
        dataSource.setAcquireIncrement(c3p0ConfigProperties.getAcquireIncrement());
        dataSource.setMaxStatements(c3p0ConfigProperties.getMaxStatements());
        dataSource.setInitialPoolSize(c3p0ConfigProperties.getInitialPoolSize());
        dataSource.setIdleConnectionTestPeriod(c3p0ConfigProperties.getIdleConnectionTestPeriod());
        dataSource.setAcquireRetryAttempts(c3p0ConfigProperties.getAcquireRetryAttempts());
        dataSource.setBreakAfterAcquireFailure(c3p0ConfigProperties.isBreakAfterAcquireFailure());
        dataSource.setTestConnectionOnCheckout(c3p0ConfigProperties.isTestConnectionOnCheckout());
        return dataSource;
    }
}
