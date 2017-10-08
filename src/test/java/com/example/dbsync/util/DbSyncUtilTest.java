package com.example.dbsync.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.example.dbsync.TestDataGenerator;
import com.example.dbsync.config.RowSelector;

public class DbSyncUtilTest {

	@Test
	public void convertInsertTemplateToDML() {
		String insertTemplate = "INSERT INTO TABLE_ALL_TYPES(COLUMN1,COLUMN2,COLUMN3,COLUMN4,COLUMN6,COLUMN7) VALUES (*COLUMN1*,*COLUMN2*,*COLUMN3*,*COLUMN4*,*COLUMN6*,*COLUMN7*);";
		String insertDMLActual = DbSyncUtil.convertToDML(TestDataGenerator.getSourcerow(), insertTemplate, TestDataGenerator.getColumnmetadatalist());
		Assert.assertNotNull(insertDMLActual);
		String insertDMLExpected = "INSERT INTO TABLE_ALL_TYPES(COLUMN1,COLUMN2,COLUMN3,COLUMN4,COLUMN6,COLUMN7) VALUES ('TEST',1,TO_DATE('28-SEP-2017','DD-MON-RRRR'),'test',TO_TIMESTAMP('27-SEP-2017 12.50.54 AM','DD-MON-RRRR HH.MI.SS AM'),1);";
		Assert.assertEquals(insertDMLExpected, insertDMLActual);
	}
	
	@Test
	public void convertUpdateTemplateToDML() {
		String updateTemplate = "UPDATE TABLE_ALL_TYPES SET COLUMN3 = *COLUMN3*,COLUMN4 = *COLUMN4*,COLUMN6 = *COLUMN6*";
		String updateDMLActual = DbSyncUtil.convertToDML(TestDataGenerator.getSourcerow(), updateTemplate, TestDataGenerator.getColumnmetadatalist());
		Assert.assertNotNull(updateDMLActual);
		String updateDMLExpected = "UPDATE TABLE_ALL_TYPES SET COLUMN3 = TO_DATE('28-SEP-2017','DD-MON-RRRR'),COLUMN4 = 'test',COLUMN6 = TO_TIMESTAMP('27-SEP-2017 12.50.54 AM','DD-MON-RRRR HH.MI.SS AM')";
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
	
	@Test
	public void testGetPaginationList() {
		List<RowSelector> list = null;
		list = DbSyncUtil.getPaginationList(0, 105);
		Assert.assertEquals(0, list.size());
		list = DbSyncUtil.getPaginationList(1, 105);
		Assert.assertEquals(105, list.size());
		list = DbSyncUtil.getPaginationList(5, 105);
		Assert.assertEquals(21, list.size());
		list = DbSyncUtil.getPaginationList(10, 105);
		Assert.assertEquals(11, list.size());		
		list = DbSyncUtil.getPaginationList(15, 105);
		Assert.assertEquals(7, list.size());
		list = DbSyncUtil.getPaginationList(20, 105);
		Assert.assertEquals(6, list.size());
		list = DbSyncUtil.getPaginationList(25, 105);
		Assert.assertEquals(5, list.size());
		list = DbSyncUtil.getPaginationList(30, 105);
		Assert.assertEquals(4, list.size());
		list = DbSyncUtil.getPaginationList(35, 105);
		Assert.assertEquals(3, list.size());
		list = DbSyncUtil.getPaginationList(40, 105);
		Assert.assertEquals(3, list.size());
		list = DbSyncUtil.getPaginationList(45, 105);
		Assert.assertEquals(3, list.size());
		list = DbSyncUtil.getPaginationList(50, 105);
		Assert.assertEquals(3, list.size());
		list = DbSyncUtil.getPaginationList(100, 105);
		Assert.assertEquals(2, list.size());	
		list = DbSyncUtil.getPaginationList(105, 105);
		Assert.assertEquals(1, list.size());
		list = DbSyncUtil.getPaginationList(106, 105);
		Assert.assertEquals(1, list.size());
	}

}
