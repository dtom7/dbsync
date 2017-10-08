package com.example.dbsync.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dbsync.config.ColumnMetadata;
import com.example.dbsync.config.RowSelector;

public class DbSyncUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbSyncUtil.class);

	public static String convertToDML(Map<String, Object> row, String template, List<ColumnMetadata> columnMetadataList) {
		StrBuilder strBuilder = new StrBuilder(template);
		for (String key : row.keySet()) {
			LOGGER.info("key: " + key + " -- value: " + row.get(key));
			String columnType = getColumnType(key, columnMetadataList);
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
		case "BIGINT":
		case "INTEGER":
		case "NUMBER":
		case "LONG":
			strBuilder.replaceAll("*" + key + "*", value.toString());
			break;
		case "DATE":
			strBuilder.replaceAll("*" + key + "*", "TO_DATE('" + value.toString() + "','DD-MON-RRRR')");
			break;
		case "TIMESTAMP":
		case "TIMESTAMP(6)":
			strBuilder.replaceAll("*" + key + "*", "TO_TIMESTAMP('" + value.toString() + "','DD-MON-RRRR HH.MI.SS AM')");
			break;
		default:
			break;
		}
	}
	
	public static void convertSelectColumns(StringBuilder sb, String columnType, String columnName) {
		if (columnType.equals("DATE")) {
			sb.append("TO_CHAR(");
			sb.append(columnName);
			sb.append(",");
			sb.append("'DD-MON-RRRR')");
			sb.append(" AS ");
			sb.append(columnName);
		} else if (columnType.equals("TIMESTAMP(6)") || columnType.equals("TIMESTAMP")) {
			sb.append("TO_CHAR(");
			sb.append(columnName);
			sb.append(",");
			sb.append("'DD-MON-RRRR HH.MI.SS AM')");
			sb.append(" AS ");
			sb.append(columnName);
		} else {
			sb.append(columnName);
		}
	}
	
	public static String getColumnType(String columnName, List<ColumnMetadata> columnMetadataList) {
		String columnType = columnMetadataList.get(columnMetadataList.indexOf(new ColumnMetadata(columnName, null)))
				.getColumnType();
		return columnType;
	}
	
	public static List<RowSelector> getPaginationList(int pageSize, int rowCount) {
		List<RowSelector> list = new ArrayList<>();
		if (pageSize != 0 && rowCount != 0) {
			if (rowCount <= pageSize) {
				list.add(new RowSelector(1, 1, rowCount));
			} else {
				int remainder = rowCount % pageSize;
				int quotient = (rowCount - remainder) / pageSize;
				int min = 1;
				int max = 0;
				int i;
				for (i = 1; i <= quotient; i++) {
					max = i * pageSize;
					list.add(new RowSelector(i, min, max));
					min = min + pageSize;
				}
				if (remainder != 0) {
					list.add(new RowSelector(i, min, (max + remainder)));
				}
			}
		}
		return list;
	}

}
