package com.example;

import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Factory
public class JdbcTemplateConfig {
    @EachBean(DataSource.class)
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
