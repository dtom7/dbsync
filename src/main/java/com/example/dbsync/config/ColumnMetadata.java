package com.example.dbsync.config;

public class ColumnMetadata {
	
	private String columnName;
	private String columnType;
		
	public ColumnMetadata() {
		super();
	}

	public ColumnMetadata(String columnName, String columnType) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
	}

	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnType() {
		return columnType;
	}
	
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
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
		ColumnMetadata other = (ColumnMetadata) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColumnMetadata [columnName=" + columnName + ", columnType=" + columnType + "]";
	}

}
