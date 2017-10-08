package com.example.dbsync.config;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("!test")
@PropertySource("file:application.properties")
public class DbSyncContext {
	
	@Autowired
	private DbSyncProperties dbSyncProperties;
	
	@Bean
	@Primary
    public BasicDataSource sourceDataSource() throws SQLException {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		basicDataSource.setUrl(dbSyncProperties.getSourceJdbcUrl());
		basicDataSource.setUsername(dbSyncProperties.getSourceJdbcUsername());
		basicDataSource.setPassword(dbSyncProperties.getSourceJdbcPassword());
		basicDataSource.setInitialSize(1);
        return basicDataSource;
    }
 
    @Bean
    public JdbcTemplate sourceJdbcTemplate() throws SQLException {
        JdbcTemplate sourceJdbcTemplate = new JdbcTemplate(sourceDataSource());
        return sourceJdbcTemplate;
    }
    
    @Bean
    public BasicDataSource targetDataSource() throws SQLException {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		basicDataSource.setUrl(dbSyncProperties.getTargetJdbcUrl());
		basicDataSource.setUsername(dbSyncProperties.getTargetJdbcUsername());
		basicDataSource.setPassword(dbSyncProperties.getTargetJdbcPassword());
		basicDataSource.setInitialSize(1);
        return basicDataSource;
    }
 
    @Bean
    public JdbcTemplate targetJdbcTemplate() throws SQLException {
        JdbcTemplate targetJdbcTemplate = new JdbcTemplate(targetDataSource());
        return targetJdbcTemplate;
    }

}
