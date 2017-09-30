package com.example.dbsync.config;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import com.example.dbsync.validation.ValidListOfString;

@Configuration
@PropertySource("file:application.properties")
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
	@ValidListOfString
	private List<String> seletionColumns = new ArrayList<>();
	@ValidListOfString
	private List<String> comparisonColumns = new ArrayList<>();
	@ValidListOfString(required = false)
	private List<String> excludeColumns = new ArrayList<>();
	
	public DbSyncProperties() {
		super();
	}

	public DbSyncProperties(String sourceJdbcUrl, String sourceJdbcUsername, String sourceJdbcPassword,
			String targetJdbcUrl, String targetJdbcUsername, String targetJdbcPassword, String sourceSchemaName,
			String targetSchemaName, String tableName, List<String> seletionColumns, List<String> comparisonColumns,
			List<String> excludeColumns) {
		super();
		this.sourceJdbcUrl = sourceJdbcUrl;
		this.sourceJdbcUsername = sourceJdbcUsername;
		this.sourceJdbcPassword = sourceJdbcPassword;
		this.targetJdbcUrl = targetJdbcUrl;
		this.targetJdbcUsername = targetJdbcUsername;
		this.targetJdbcPassword = targetJdbcPassword;
		this.sourceSchemaName = sourceSchemaName;
		this.targetSchemaName = targetSchemaName;
		this.tableName = tableName;
		this.seletionColumns = seletionColumns;
		this.comparisonColumns = comparisonColumns;
		this.excludeColumns = excludeColumns;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comparisonColumns == null) ? 0 : comparisonColumns.hashCode());
		result = prime * result + ((excludeColumns == null) ? 0 : excludeColumns.hashCode());
		result = prime * result + ((seletionColumns == null) ? 0 : seletionColumns.hashCode());
		result = prime * result + ((sourceJdbcPassword == null) ? 0 : sourceJdbcPassword.hashCode());
		result = prime * result + ((sourceJdbcUrl == null) ? 0 : sourceJdbcUrl.hashCode());
		result = prime * result + ((sourceJdbcUsername == null) ? 0 : sourceJdbcUsername.hashCode());
		result = prime * result + ((sourceSchemaName == null) ? 0 : sourceSchemaName.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((targetJdbcPassword == null) ? 0 : targetJdbcPassword.hashCode());
		result = prime * result + ((targetJdbcUrl == null) ? 0 : targetJdbcUrl.hashCode());
		result = prime * result + ((targetJdbcUsername == null) ? 0 : targetJdbcUsername.hashCode());
		result = prime * result + ((targetSchemaName == null) ? 0 : targetSchemaName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DbSyncProperties other = (DbSyncProperties) obj;
		if (comparisonColumns == null) {
			if (other.comparisonColumns != null)
				return false;
		} else if (!comparisonColumns.equals(other.comparisonColumns))
			return false;
		if (excludeColumns == null) {
			if (other.excludeColumns != null)
				return false;
		} else if (!excludeColumns.equals(other.excludeColumns))
			return false;
		if (seletionColumns == null) {
			if (other.seletionColumns != null)
				return false;
		} else if (!seletionColumns.equals(other.seletionColumns))
			return false;
		if (sourceJdbcPassword == null) {
			if (other.sourceJdbcPassword != null)
				return false;
		} else if (!sourceJdbcPassword.equals(other.sourceJdbcPassword))
			return false;
		if (sourceJdbcUrl == null) {
			if (other.sourceJdbcUrl != null)
				return false;
		} else if (!sourceJdbcUrl.equals(other.sourceJdbcUrl))
			return false;
		if (sourceJdbcUsername == null) {
			if (other.sourceJdbcUsername != null)
				return false;
		} else if (!sourceJdbcUsername.equals(other.sourceJdbcUsername))
			return false;
		if (sourceSchemaName == null) {
			if (other.sourceSchemaName != null)
				return false;
		} else if (!sourceSchemaName.equals(other.sourceSchemaName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (targetJdbcPassword == null) {
			if (other.targetJdbcPassword != null)
				return false;
		} else if (!targetJdbcPassword.equals(other.targetJdbcPassword))
			return false;
		if (targetJdbcUrl == null) {
			if (other.targetJdbcUrl != null)
				return false;
		} else if (!targetJdbcUrl.equals(other.targetJdbcUrl))
			return false;
		if (targetJdbcUsername == null) {
			if (other.targetJdbcUsername != null)
				return false;
		} else if (!targetJdbcUsername.equals(other.targetJdbcUsername))
			return false;
		if (targetSchemaName == null) {
			if (other.targetSchemaName != null)
				return false;
		} else if (!targetSchemaName.equals(other.targetSchemaName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DbSyncProperties [sourceJdbcUrl=" + sourceJdbcUrl + ", sourceJdbcUsername=" + sourceJdbcUsername
				+ ", sourceJdbcPassword=" + sourceJdbcPassword + ", targetJdbcUrl=" + targetJdbcUrl
				+ ", targetJdbcUsername=" + targetJdbcUsername + ", targetJdbcPassword=" + targetJdbcPassword
				+ ", sourceSchemaName=" + sourceSchemaName + ", targetSchemaName=" + targetSchemaName + ", tableName="
				+ tableName + ", seletionColumns=" + seletionColumns + ", comparisonColumns=" + comparisonColumns
				+ ", excludeColumns=" + excludeColumns + "]";
	}

}
