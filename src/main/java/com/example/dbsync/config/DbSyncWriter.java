package com.example.dbsync.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class DbSyncWriter {
	
	private final String insertFileName = "result/insert.sql";
	private final String updateFileName = "result/update.sql";
	private final String newLine = System.getProperty("line.separator");

	public void writeToFile(List<String> dmlList, String action) throws IOException {
		File file = new File((action.equals("insert") ? insertFileName : updateFileName));
		FileUtils.deleteQuietly(file);
		for (String dml : dmlList) {
			FileUtils.writeStringToFile(file, (dml + newLine), Charset.forName("UTF-8"), true);
		}
		FileUtils.writeStringToFile(file, "COMMIT;" + newLine, Charset.forName("UTF-8"), true);
	}

}
