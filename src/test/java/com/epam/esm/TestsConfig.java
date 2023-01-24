package com.epam.esm;

import com.epam.esm.utils.DatabaseManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class TestsConfig {
    @Bean
    public DatabaseManager getDM(JdbcTemplate jdbcTemplate){
        return new DatabaseManager(jdbcTemplate);
    }
}
