package com.example.dbsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.dbsync.config.ColumnMetadata;

public class TestDataGenerator {
	
	private static final List<ColumnMetadata> columnMetadataList = new ArrayList<>();
	
	private static final Map<String, Object> sourceRow = new HashMap<>();
	
	static {
		populateColumnMetadataList();
		populatesourceRow();
	}

	public static List<ColumnMetadata> getColumnmetadatalist() {
		return columnMetadataList;
	}
	
	public static Map<String, Object> getSourcerow() {
		return sourceRow;
	}

	private static void populatesourceRow() {
		sourceRow.put("COLUMN1", "TEST");
		sourceRow.put("COLUMN2", 1);
		sourceRow.put("COLUMN3", "28-SEP-2017");
		sourceRow.put("COLUMN4", "test");
		sourceRow.put("COLUMN6", "27-SEP-2017 12.50.54 AM");
		sourceRow.put("COLUMN7", 1);
	}

	private static void populateColumnMetadataList() {
		ColumnMetadata columnMetadata1 = new ColumnMetadata();
		columnMetadata1.setColumnName("COLUMN1");
		columnMetadata1.setColumnType("VARCHAR2");
		columnMetadataList.add(columnMetadata1);
		ColumnMetadata columnMetadata2 = new ColumnMetadata();
		columnMetadata2.setColumnName("COLUMN2");
		columnMetadata2.setColumnType("NUMBER");
		columnMetadataList.add(columnMetadata2);
		ColumnMetadata columnMetadata3 = new ColumnMetadata();
		columnMetadata3.setColumnName("COLUMN3");
		columnMetadata3.setColumnType("DATE");
		columnMetadataList.add(columnMetadata3);
		ColumnMetadata columnMetadata4 = new ColumnMetadata();
		columnMetadata4.setColumnName("COLUMN4");
		columnMetadata4.setColumnType("CLOB");
		columnMetadataList.add(columnMetadata4);
		ColumnMetadata columnMetadata5 = new ColumnMetadata();
		columnMetadata5.setColumnName("COLUMN5");
		columnMetadata5.setColumnType("BLOB");
		columnMetadataList.add(columnMetadata5);
		ColumnMetadata columnMetadata6 = new ColumnMetadata();
		columnMetadata6.setColumnName("COLUMN6");
		columnMetadata6.setColumnType("TIMESTAMP(6)");
		columnMetadataList.add(columnMetadata6);
		ColumnMetadata columnMetadata7 = new ColumnMetadata();
		columnMetadata7.setColumnName("COLUMN7");
		columnMetadata7.setColumnType("LONG");
		columnMetadataList.add(columnMetadata7);
	}

}
