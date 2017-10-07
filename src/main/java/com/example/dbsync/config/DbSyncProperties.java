package com.example.dbsync.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import com.example.dbsync.validation.ValidListOfString;

@Configuration
@ConfigurationProperties(prefix = "dbsync")
@Validated
public class DbSyncProperties {
	
	@NotEmpty
	private String sourceJdbcUrl;
	@NotEmpty
	private String sourceJdbcUsername;
	@NotEmpty
	private String sourceJdbcPassword;
	@NotEmpty
	private String targetJdbcUrl;
	@NotEmpty
	private String targetJdbcUsername;
	@NotEmpty
	private String targetJdbcPassword;
	@NotEmpty
	private String sourceSchemaName;
	@NotEmpty
	private String targetSchemaName;
	@NotEmpty
	private String tableName;
	@NotEmpty
	private String databaseVendor = "ORACLE";
	@ValidListOfString
	private List<String> seletionColumns = new ArrayList<>();
	@ValidListOfString
	private List<String> comparisonColumns = new ArrayList<>();
	@ValidListOfString(required = false)
	private List<String> excludeColumns = new ArrayList<>();
	
	public DbSyncProperties() {
		super();
	}

	public String getSourceJdbcUrl() {
		return sourceJdbcUrl;
	}

	public void setSourceJdbcUrl(String sourceJdbcUrl) {
		this.sourceJdbcUrl = sourceJdbcUrl;
	}

	public String getSourceJdbcUsername() {
		return sourceJdbcUsername;
	}

	public void setSourceJdbcUsername(String sourceJdbcUsername) {
		this.sourceJdbcUsername = sourceJdbcUsername;
	}

	public String getSourceJdbcPassword() {
		return sourceJdbcPassword;
	}

	public void setSourceJdbcPassword(String sourceJdbcPassword) {
		this.sourceJdbcPassword = sourceJdbcPassword;
	}

	public String getTargetJdbcUrl() {
		return targetJdbcUrl;
	}

	public void setTargetJdbcUrl(String targetJdbcUrl) {
		this.targetJdbcUrl = targetJdbcUrl;
	}

	public String getTargetJdbcUsername() {
		return targetJdbcUsername;
	}

	public void setTargetJdbcUsername(String targetJdbcUsername) {
		this.targetJdbcUsername = targetJdbcUsername;
	}

	public String getTargetJdbcPassword() {
		return targetJdbcPassword;
	}

	public void setTargetJdbcPassword(String targetJdbcPassword) {
		this.targetJdbcPassword = targetJdbcPassword;
	}

	public String getSourceSchemaName() {
		return sourceSchemaName;
	}

	public void setSourceSchemaName(String sourceSchemaName) {
		this.sourceSchemaName = sourceSchemaName;
	}

	public String getTargetSchemaName() {
		return targetSchemaName;
	}

	public void setTargetSchemaName(String targetSchemaName) {
		this.targetSchemaName = targetSchemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDatabaseVendor() {
		return databaseVendor;
	}

	public void setDatabaseVendor(String databaseVendor) {
		this.databaseVendor = databaseVendor;
	}

	public List<String> getSeletionColumns() {
		return seletionColumns;
	}

	public void setSeletionColumns(List<String> seletionColumns) {
		this.seletionColumns = seletionColumns;
	}

	public List<String> getComparisonColumns() {
		return comparisonColumns;
	}

	public void setComparisonColumns(List<String> comparisonColumns) {
		this.comparisonColumns = comparisonColumns;
	}

	public List<String> getExcludeColumns() {
		return excludeColumns;
	}

	public void setExcludeColumns(List<String> excludeColumns) {
		this.excludeColumns = excludeColumns;
	}

	@Override
	public String toString() {
		return "DbSyncProperties [sourceJdbcUrl=" + sourceJdbcUrl + ", sourceJdbcUsername=" + sourceJdbcUsername
				+ ", sourceJdbcPassword=" + sourceJdbcPassword + ", targetJdbcUrl=" + targetJdbcUrl
				+ ", targetJdbcUsername=" + targetJdbcUsername + ", targetJdbcPassword=" + targetJdbcPassword
				+ ", sourceSchemaName=" + sourceSchemaName + ", targetSchemaName=" + targetSchemaName + ", tableName="
				+ tableName + ", databaseVendor=" + databaseVendor + ", seletionColumns=" + seletionColumns
				+ ", comparisonColumns=" + comparisonColumns + ", excludeColumns=" + excludeColumns + "]";
	}

}
