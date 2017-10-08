package com.example.dbsync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.dbsync.config.DbSyncInitializer;
import com.example.dbsync.config.DbSyncProcessor;
import com.example.dbsync.config.DbSyncProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application.test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DbsyncApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbsyncApplicationTests.class);

	@Autowired
	private DbSyncInitializer dbSyncInitializer;

	@Autowired
	private DbSyncProcessor dbSyncProcessor;

	@Autowired
	private DbSyncProperties dbSyncProperties;

	@Autowired
	private JdbcTemplate targetJdbcTemplate;

	private String targetTable, insert1, insert2, insert3, insert4, insert22, insert33;
	
	public void initVariables() {
		targetTable = dbSyncProperties.getTargetSchemaName() + "." + dbSyncProperties.getTableName();
		insert1 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST1',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,1)";
		insert2 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST2',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,2)";
		insert22 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST2',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,22)";
		insert3 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST3',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,3)";
		insert33 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST3',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,33)";
		insert4 = "INSERT INTO " + targetTable + "(" + dbSyncInitializer.getColumnNames() + ") VALUES ('TEST4',1,CURRENT_DATE,'test',CURRENT_TIMESTAMP,4)";		
	}

	@Before
	public void init() {
		dbSyncInitializer.init();
		initVariables();
		dbSyncProcessor.getInsertList().clear();
		dbSyncProcessor.getUpdateList().clear();
		targetJdbcTemplate.execute("DELETE FROM " + targetTable);
		int count = targetJdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + targetTable, Integer.class);
		assertEquals("Target table must be empty", 0, count);
	}

	@Test
	public void test1WithNoTargetRows() {
		try {
			dbSyncProcessor.process();
			assertEquals("Insert list size should be 4", 4, dbSyncProcessor.getInsertList().size());
			assertEquals("Update list size should be 0", 0, dbSyncProcessor.getUpdateList().size());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.toString());
			fail("Exception occurred, which is not expected");
		}
	}

	@Test
	public void test2WithAllTargetRows() {
		try {
			targetJdbcTemplate.execute(insert1);
			targetJdbcTemplate.execute(insert2);
			targetJdbcTemplate.execute(insert3);
			targetJdbcTemplate.execute(insert4);
			int count = targetJdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + targetTable, Integer.class);
			assertEquals("Target table must contain 4 rows", 4, count);
			dbSyncProcessor.process();
			assertEquals("Insert list size should be 0", 0, dbSyncProcessor.getInsertList().size());
			assertEquals("Update list size should be 0", 0, dbSyncProcessor.getUpdateList().size());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.toString());
			fail("Exception occurred, which is not expected");
		}		
	}
	
	@Test
	public void test3WithSomeTargetRows() {
		try {
			targetJdbcTemplate.execute(insert22);
			targetJdbcTemplate.execute(insert33);
			int count = targetJdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + targetTable, Integer.class);
			assertEquals("Target table must contain 2 rows", 2, count);
			dbSyncProcessor.process();
			assertEquals("Insert list size should be 2", 2, dbSyncProcessor.getInsertList().size());
			assertEquals("Update list size should be 2", 2, dbSyncProcessor.getUpdateList().size());
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.toString());
			fail("Exception occurred, which is not expected");
		}
	}

}
