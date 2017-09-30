package com.example.dbsync.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dbsync.config.ColumnMetadata;

public class DbSyncUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbSyncUtil.class);

	public static String convertToDML(Map<String, Object> row, String template, List<ColumnMetadata> columnMetadataList) {
		StrBuilder strBuilder = new StrBuilder(template);
		for (String key : row.keySet()) {
			LOGGER.info("key: " + key + " -- value: " + row.get(key));
			String columnType = columnMetadataList
					.get(columnMetadataList.indexOf(new ColumnMetadata(key, null)))
					.getColumnType();
			replaceValue(columnType, strBuilder, key, row.get(key));
		}
		return strBuilder.toString();
	}

	public static void replaceValue (String columnType, StrBuilder strBuilder, String key, Object value) {
		LOGGER.info("key: " + key + " -- value: " + value + " -- columnType: " + columnType);
		switch (columnType) {
		case "CLOB":
		case "CHAR":
		case "VARCHAR":
		case "VARCHAR2":
			strBuilder.replaceAll("*" + key + "*", "'" + value.toString() + "'");
			break;
		case "NUMBER":
		case "LONG":
			strBuilder.replaceAll("*" + key + "*", value.toString());
			break;
		case "DATE":
			strBuilder.replaceAll("*" + key + "*", "TO_DATE('" + value.toString() + "','DD-MON-RRRR')");
			break;
		case "TIMESTAMP(6)":
			strBuilder.replaceAll("*" + key + "*", "TO_TIMESTAMP('" + value.toString() + "','DD-MON-RRRR HH.MI.SSXFF AM')");
			break;
		default:
			break;
		}
	}

}
