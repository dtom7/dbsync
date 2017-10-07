package com.example.dbsync.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DbSyncInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbSyncInitializer.class);

	@Autowired
	private JdbcTemplate sourceJdbcTemplate;

	@Autowired
	private DbSyncProperties dbSyncProperties;

	private List<ColumnMetadata> columnMetadataList;
	
	private String columnNames;

	private String columnSelect; 

	private String columnValue; 

	private String insertTemplate; 

	private String comparisonSelect; 

	private String whereTemplate; 

	private String updateTemplate;

	public void init() {
		
		LOGGER.info("dbSyncProperties: " + dbSyncProperties.toString());
		
		String queryAllColumns = "";
		if(dbSyncProperties.getDatabaseVendor().equals("ORACLE")) {
			queryAllColumns = "SELECT COLUMN_NAME, DATA_TYPE FROM ALL_TAB_COLUMNS where TABLE_NAME = '"
					+ dbSyncProperties.getTableName() + "' ORDER BY COLUMN_ID";
		} else {
			queryAllColumns = "SELECT COLUMN_NAME, TYPE_NAME AS DATA_TYPE FROM INFORMATION_SCHEMA.SYSTEM_COLUMNS where TABLE_SCHEM = 'SOURCE' AND TABLE_NAME = '"
					+ dbSyncProperties.getTableName() + "'";
		}

		columnMetadataList = sourceJdbcTemplate
				.query(queryAllColumns, new RowMapper<ColumnMetadata>() {
							@Override
							public ColumnMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
								ColumnMetadata columnMetadata = new ColumnMetadata();
								columnMetadata.setColumnName(rs.getString("column_name"));
								columnMetadata.setColumnType(rs.getString("data_type"));
								return columnMetadata;
							}
						});

		LOGGER.info("columnMetadataList: " + columnMetadataList);

		StringBuilder columnSelectSB = new StringBuilder();
		StringBuilder columnNamesSB = new StringBuilder();
		StringBuilder columnValueSB = new StringBuilder();
		for (ColumnMetadata columnMetadata : columnMetadataList) {
			if (!dbSyncProperties.getExcludeColumns().contains(columnMetadata.getColumnName())) {
				if (columnMetadata.getColumnType().equals("DATE")) {
					columnSelectSB.append("TO_CHAR(");
					columnSelectSB.append(columnMetadata.getColumnName());
					columnSelectSB.append(",");
					columnSelectSB.append("'DD-MON-RRRR')");
					columnSelectSB.append(" AS ");
					columnSelectSB.append(columnMetadata.getColumnName());
				} else if (columnMetadata.getColumnType().equals("TIMESTAMP(6)")) {
					columnSelectSB.append("TO_CHAR(");
					columnSelectSB.append(columnMetadata.getColumnName());
					columnSelectSB.append(",");
					columnSelectSB.append("'DD-MON-RRRR HH.MI.SSXFF AM')");
					columnSelectSB.append(" AS ");
					columnSelectSB.append(columnMetadata.getColumnName());
				} else {
					columnSelectSB.append(columnMetadata.getColumnName());					
				}
				columnSelectSB.append(",");
				columnNamesSB.append(columnMetadata.getColumnName());
				columnNamesSB.append(",");
				columnValueSB.append("*");
				columnValueSB.append(columnMetadata.getColumnName());
				columnValueSB.append("*");
				columnValueSB.append(",");
			}
		}
		columnSelectSB.deleteCharAt(columnSelectSB.lastIndexOf(","));
		columnSelect = columnSelectSB.toString();
		columnValueSB.deleteCharAt(columnValueSB.lastIndexOf(","));
		columnValue = columnValueSB.toString();
		columnNamesSB.deleteCharAt(columnNamesSB.lastIndexOf(","));
		columnNames = columnNamesSB.toString();
		
		LOGGER.info("columnSelect: " + columnSelect);
		LOGGER.info("columnValue: " + columnValue);
		LOGGER.info("columnNames: " + columnNames);

		StringBuilder insertSB = new StringBuilder();
		insertSB.append("INSERT INTO ");
		insertSB.append(dbSyncProperties.getTargetSchemaName());
		insertSB.append(".");
		insertSB.append(dbSyncProperties.getTableName());
		insertSB.append("(");
		insertSB.append(columnNames);
		insertSB.append(")");
		insertSB.append(" VALUES ");
		insertSB.append("(");
		insertSB.append(columnValue);
		insertSB.append(");");

		insertTemplate = insertSB.toString();

		LOGGER.info("insert: " + insertTemplate);

		StringBuilder comparisonSelectSB = new StringBuilder();
		StringBuilder updateTemplateSB = new StringBuilder();
		comparisonSelectSB.append("SELECT ");
		updateTemplateSB.append("UPDATE ");
		updateTemplateSB.append(dbSyncProperties.getTargetSchemaName());
		updateTemplateSB.append(".");
		updateTemplateSB.append(dbSyncProperties.getTableName());
		updateTemplateSB.append(" SET ");
		for (String comparisonSelect : dbSyncProperties.getComparisonColumns()) {
			comparisonSelectSB.append(comparisonSelect);
			comparisonSelectSB.append(",");
			updateTemplateSB.append(comparisonSelect);
			updateTemplateSB.append(" = ");
			updateTemplateSB.append("*");
			updateTemplateSB.append(comparisonSelect);
			updateTemplateSB.append("*");
			updateTemplateSB.append(",");
		}
		comparisonSelectSB.deleteCharAt(comparisonSelectSB.lastIndexOf(","));
		comparisonSelectSB.append(" FROM ");
		comparisonSelectSB.append(dbSyncProperties.getTargetSchemaName());
		comparisonSelectSB.append(".");
		comparisonSelectSB.append(dbSyncProperties.getTableName());
		comparisonSelect = comparisonSelectSB.toString();
		updateTemplateSB.deleteCharAt(updateTemplateSB.lastIndexOf(","));
		updateTemplate = updateTemplateSB.toString();

		LOGGER.info("comparisonSelect: " + comparisonSelect);
		LOGGER.info("updateTemplate: " + updateTemplate);

		StrBuilder whereTemplateSB = new StrBuilder();
		whereTemplateSB.append(" WHERE ");
		for (String whereColumn : dbSyncProperties.getSeletionColumns()) {
			String columnType = columnMetadataList
					.get(columnMetadataList.indexOf(new ColumnMetadata(whereColumn, null)))
					.getColumnType();
			switch(columnType) {
			case "DATE":
				whereTemplateSB.append("TRUNC(");
				whereTemplateSB.append(whereColumn);
				whereTemplateSB.append(")");
				break;
			case "CLOB":
				whereTemplateSB.append("TO_CHAR(");
				whereTemplateSB.append(whereColumn);
				whereTemplateSB.append(")");
				break;
				default:
					whereTemplateSB.append(whereColumn);
					break;
			}
			whereTemplateSB.append(" = ");
			whereTemplateSB.append("*");
			whereTemplateSB.append(whereColumn);
			whereTemplateSB.append("*");
			whereTemplateSB.append("&");
		}
		whereTemplateSB.deleteCharAt(whereTemplateSB.lastIndexOf("&"));
		whereTemplateSB.replaceAll("&", " AND ");
		whereTemplate = whereTemplateSB.toString();

		LOGGER.info("whereTemplate: " + whereTemplate);

	}

	public DbSyncInitializer() {
		super();
	}

	public JdbcTemplate getSourceJdbcTemplate() {
		return sourceJdbcTemplate;
	}

	public void setSourceJdbcTemplate(JdbcTemplate sourceJdbcTemplate) {
		this.sourceJdbcTemplate = sourceJdbcTemplate;
	}

	public DbSyncProperties getDbSyncProperties() {
		return dbSyncProperties;
	}

	public void setDbSyncProperties(DbSyncProperties dbSyncProperties) {
		this.dbSyncProperties = dbSyncProperties;
	}

	public List<ColumnMetadata> getColumnMetadataList() {
		return columnMetadataList;
	}

	public String getColumnSelect() {
		return columnSelect;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public String getInsertTemplate() {
		return insertTemplate;
	}

	public String getComparisonSelect() {
		return comparisonSelect;
	}

	public String getWhereTemplate() {
		return whereTemplate;
	}

	public String getUpdateTemplate() {
		return updateTemplate;
	}

	public String getColumnNames() {
		return columnNames;
	}

}
