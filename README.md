# dbsync

This is a simple spring boot utility program to sync up a table between 2 databases. 

Only tables that store static data is supported.

Tables that store transactional data is not supported.

Only Oracle database (11g/12c) is supported. 

Tables should have a unique key (usually surrogate primary key) that can be used in ORDER BY clause.

Columns with BLOB datatype is not supported.

Before running the program, make sure that application.properties file is present in the same directory as the program and populated with correct values.

User is expected to have write permissions to the directory where the program resides.

Logs will be generated in the logs directory, that will be created in the same directory as the program.

Results will be generated in the result directory, that will be created in the same directory as the program.
