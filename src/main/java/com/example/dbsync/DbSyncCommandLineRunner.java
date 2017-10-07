package com.example.dbsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.dbsync.config.DbSyncInitializer;
import com.example.dbsync.config.DbSyncProcessor;

@Component
@Profile("!test")
public class DbSyncCommandLineRunner implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DbSyncCommandLineRunner.class);

	@Autowired
	private DbSyncInitializer dbSyncInitializer;

	@Autowired
	private DbSyncProcessor dbSyncProcessor;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Start ..");
		try {
			dbSyncInitializer.init();
			dbSyncProcessor.process();
			if (dbSyncProcessor.getInsertList().size() != 0) {
				System.out.println("Inserts needed: " + dbSyncProcessor.getInsertList().size() + " ..");
			} else {
				System.out.println("No inserts needed ..");
			}
			if (dbSyncProcessor.getUpdateList().size() != 0) {
				System.out.println("Updates needed: " + dbSyncProcessor.getUpdateList().size() + " ..");
			} else {
				System.out.println("No updates needed ..");
			}
		} catch (Exception e) {
			LOGGER.error("Exception: {}", e.toString());
			System.err.println("Exception - Please check log ..");
		}
		System.out.println("Done ..");
	}

}
