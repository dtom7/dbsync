package com.example.dbsync.util;

import org.junit.Assert;
import org.junit.Test;

import com.example.dbsync.TestDataGenerator;

public class DbSyncUtilTest {

	@Test
	public void convertInsertTemplateToDML() {
		String insertTemplate = "INSERT INTO TABLE_ALL_TYPES(COLUMN1,COLUMN2,COLUMN3,COLUMN4,COLUMN6,COLUMN7) VALUES (*COLUMN1*,*COLUMN2*,*COLUMN3*,*COLUMN4*,*COLUMN6*,*COLUMN7*);";
		String insertDMLActual = DbSyncUtil.convertToDML(TestDataGenerator.getSourcerow(), insertTemplate, TestDataGenerator.getColumnmetadatalist());
		Assert.assertNotNull(insertDMLActual);
		String insertDMLExpected = "INSERT INTO TABLE_ALL_TYPES(COLUMN1,COLUMN2,COLUMN3,COLUMN4,COLUMN6,COLUMN7) VALUES ('TEST',1,TO_DATE('28-SEP-2017','DD-MON-RRRR'),'test',TO_TIMESTAMP('27-SEP-2017 12.50.54.160000 AM','DD-MON-RRRR HH.MI.SSXFF AM'),1);";
		Assert.assertEquals(insertDMLExpected, insertDMLActual);
	}
	
	@Test
	public void convertUpdateTemplateToDML() {
		String updateTemplate = "UPDATE TABLE_ALL_TYPES SET COLUMN3 = *COLUMN3*,COLUMN4 = *COLUMN4*,COLUMN6 = *COLUMN6*";
		String updateDMLActual = DbSyncUtil.convertToDML(TestDataGenerator.getSourcerow(), updateTemplate, TestDataGenerator.getColumnmetadatalist());
		Assert.assertNotNull(updateDMLActual);
		String updateDMLExpected = "UPDATE TABLE_ALL_TYPES SET COLUMN3 = TO_DATE('28-SEP-2017','DD-MON-RRRR'),COLUMN4 = 'test',COLUMN6 = TO_TIMESTAMP('27-SEP-2017 12.50.54.160000 AM','DD-MON-RRRR HH.MI.SSXFF AM')";
		Assert.assertEquals(updateDMLExpected, updateDMLActual);
	}
	
	@Test
	public void convertWhereTemplateToDML() {
		String whereTemplate = "WHERE COLUMN1 = *COLUMN1* AND COLUMN2 = *COLUMN2* AND TRUNC(COLUMN3) = *COLUMN3* AND TO_CHAR(COLUMN4) = *COLUMN4*";
		String whereDMLActual = DbSyncUtil.convertToDML(TestDataGenerator.getSourcerow(), whereTemplate, TestDataGenerator.getColumnmetadatalist());
		Assert.assertNotNull(whereDMLActual);
		String whereDMLExpected = "WHERE COLUMN1 = 'TEST' AND COLUMN2 = 1 AND TRUNC(COLUMN3) = TO_DATE('28-SEP-2017','DD-MON-RRRR') AND TO_CHAR(COLUMN4) = 'test'";
		Assert.assertEquals(whereDMLExpected, whereDMLActual);
	}

}
