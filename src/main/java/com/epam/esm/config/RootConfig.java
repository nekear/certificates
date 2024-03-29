package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = {"com.epam.esm"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
)
@PropertySource("classpath:app.properties")
public class RootConfig {
    @Autowired
    ConfigurableEnvironment env;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds){
        return new JdbcTemplate(ds);
    }

    @Bean
    public TransactionManager transactionManager(DataSource ds){
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    @Profile("dev")
    public DataSource devDS(){
        HikariConfig hc = new HikariConfig();
        hc.setDriverClassName(env.getProperty("db.driver"));
        hc.setJdbcUrl(env.getProperty("db.url"));
        hc.setUsername(env.getProperty("db.username"));
        hc.setPassword(env.getProperty("db.password"));

        hc.setMaximumPoolSize(5);
        hc.setConnectionTestQuery("SELECT 1");
        hc.setPoolName("certificatesHikari");

        hc.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        hc.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        hc.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        hc.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

        return new HikariDataSource(hc);
    }

    @Bean
    @Profile("qa")
    public DataSource qaDS(){
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(env.getProperty("test.db.h2.url"));
        dataSource.setUser(env.getProperty("test.db.h2.username"));
        dataSource.setPassword(env.getProperty("test.db.h2.password"));

        return dataSource;
    }

}
