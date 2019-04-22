package com.loopme.ads.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseDBOperation {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseDBOperation() {
    }

    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
