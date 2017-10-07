package com.example.dbsync.config;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@Profile("!test")
@PropertySource("file:application.properties")
public class DbSyncContext {
	
	@Autowired
	private DbSyncProperties dbSyncProperties;
	
	@Bean
	@Primary
    public OracleDataSource sourceDataSource() throws SQLException {
    	OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(dbSyncProperties.getSourceJdbcUrl());
        dataSource.setUser(dbSyncProperties.getSourceJdbcUsername());
        dataSource.setPassword(dbSyncProperties.getSourceJdbcPassword());
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate sourceJdbcTemplate() throws SQLException {
        JdbcTemplate sourceJdbcTemplate = new JdbcTemplate(sourceDataSource());
        return sourceJdbcTemplate;
    }
    
    @Bean
    public OracleDataSource targetDataSource() throws SQLException {
    	OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(dbSyncProperties.getTargetJdbcUrl());
        dataSource.setUser(dbSyncProperties.getTargetJdbcUsername());
        dataSource.setPassword(dbSyncProperties.getTargetJdbcPassword());
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate targetJdbcTemplate() throws SQLException {
        JdbcTemplate targetJdbcTemplate = new JdbcTemplate(targetDataSource());
        return targetJdbcTemplate;
    }

}
