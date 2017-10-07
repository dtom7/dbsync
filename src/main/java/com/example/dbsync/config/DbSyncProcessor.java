package com.example.dbsync.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.dbsync.util.DbSyncUtil;

@Component
public class DbSyncProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbSyncProcessor.class);

	@Autowired
	private JdbcTemplate sourceJdbcTemplate;

	@Autowired
	private JdbcTemplate targetJdbcTemplate;

	@Autowired
	private DbSyncProperties dbSyncProperties;

	@Autowired
	private DbSyncInitializer dbSyncInitializer;
	
	private List<String> insertList = new ArrayList<>();
	
	private List<String> updateList = new ArrayList<>();

	public void process() {

		List<Map<String, Object>> sourceRows = sourceJdbcTemplate.queryForList(
				"select " + dbSyncInitializer.getColumnSelect() + " from " + dbSyncProperties.getSourceSchemaName() + "." + dbSyncProperties.getTableName());
		StrBuilder checkQuerySB = new StrBuilder();
		for (Map<String, Object> sourceRow : sourceRows) {
			checkQuerySB.clear();
			// Create query to check target database
			String where = createWhere(sourceRow);
			LOGGER.info("where: " + where);
			checkQuerySB.append(dbSyncInitializer.getComparisonSelect());
			checkQuerySB.append(where);
			LOGGER.info("checkQuery: " + checkQuerySB.toString());
			// Execute query in target database
			try {
				Map<String, Object> targetRow = targetJdbcTemplate.queryForMap(checkQuerySB.toString());
				if (targetRow != null && !targetRow.isEmpty()) {
					if (compare(sourceRow, targetRow)) {
						// No action needed
						LOGGER.info("No action needed");
					} else {
						// Update needed
						String update = createUpdate(sourceRow, where);
						LOGGER.info("Final update: " + update);
						updateList.add(update);
					}
				}
			} catch(EmptyResultDataAccessException ex) {
				// Insert needed
				String insert = createInsert(sourceRow);
				LOGGER.info("Final insert: " + insert);
				insertList.add(insert);
			}
		}

	}

	public String createInsert(Map<String, Object> sourceRow) {
		String insert = DbSyncUtil.convertToDML(sourceRow, dbSyncInitializer.getInsertTemplate(), dbSyncInitializer.getColumnMetadataList());
		return insert;
	}

	public String createUpdate(Map<String, Object> sourceRow, String where) {		
		String update = DbSyncUtil.convertToDML(sourceRow, dbSyncInitializer.getUpdateTemplate(), dbSyncInitializer.getColumnMetadataList());
		return update + where + ";";
	}

	public String createWhere(Map<String, Object> sourceRow) {		
		String where = DbSyncUtil.convertToDML(sourceRow, dbSyncInitializer.getWhereTemplate(), dbSyncInitializer.getColumnMetadataList());
		return where;
	}

	public boolean compare(Map<String, Object> sourceRow, Map<String, Object> targetRow) {
		boolean result = true;
		for (String key : targetRow.keySet()) {
			LOGGER.info(" ** key: " + key + " -- ** source value: " + sourceRow.get(key) + " -- ** target value: " + targetRow.get(key));
			if (targetRow.get(key) == null || !targetRow.get(key).equals(sourceRow.get(key))) {
				result = false;
			}
		}
		LOGGER.info("result: " + result);
		return result;
	}

	public JdbcTemplate getSourceJdbcTemplate() {
		return sourceJdbcTemplate;
	}

	public void setSourceJdbcTemplate(JdbcTemplate sourceJdbcTemplate) {
		this.sourceJdbcTemplate = sourceJdbcTemplate;
	}

	public JdbcTemplate getTargetJdbcTemplate() {
		return targetJdbcTemplate;
	}

	public void setTargetJdbcTemplate(JdbcTemplate targetJdbcTemplate) {
		this.targetJdbcTemplate = targetJdbcTemplate;
	}

	public DbSyncProperties getDbSyncProperties() {
		return dbSyncProperties;
	}

	public void setDbSyncProperties(DbSyncProperties dbSyncProperties) {
		this.dbSyncProperties = dbSyncProperties;
	}

	public DbSyncInitializer getDbSyncInitializer() {
		return dbSyncInitializer;
	}

	public void setDbSyncInitializer(DbSyncInitializer dbSyncInitializer) {
		this.dbSyncInitializer = dbSyncInitializer;
	}

	public List<String> getInsertList() {
		return insertList;
	}

	public void setInsertList(List<String> insertList) {
		this.insertList = insertList;
	}

	public List<String> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<String> updateList) {
		this.updateList = updateList;
	}
		
}
