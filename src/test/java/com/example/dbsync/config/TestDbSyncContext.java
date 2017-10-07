package com.example.dbsync.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test")
public class TestDbSyncContext {
	
	@Bean
	@Primary
	public DataSource sourceDataSource() throws SQLException {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:scripts/source_schema.sql")
				.addScript("classpath:scripts/source_data.sql")
				.build();
	}
 
    @Bean
    public JdbcTemplate sourceJdbcTemplate() throws SQLException {
        JdbcTemplate sourceJdbcTemplate = new JdbcTemplate(sourceDataSource());
        return sourceJdbcTemplate;
    }
    
    @Bean
    public DataSource targetDataSource() throws SQLException {
		return new EmbeddedDatabaseBuilder()
				.generateUniqueName(true)
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:scripts/target_schema.sql")
				.addScript("classpath:scripts/target_data.sql")
				.build();
    }
 
    @Bean
    public JdbcTemplate targetJdbcTemplate() throws SQLException {
        JdbcTemplate targetJdbcTemplate = new JdbcTemplate(targetDataSource());
        return targetJdbcTemplate;
    }

}
