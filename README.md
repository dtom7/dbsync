# dbsync

This is a simple spring boot utility program to sync up a table between 2 databases. 

Only tables that store static data is supported.This program is not meant for synching tables that store transactional data.

Only Oracle database (11g/12c) is supported. 

Columns with BLOB datatype is not supported.

Before running the program, make sure that application.properties file is present in the same diretory as the program and populated with correct
values.

Logs will be generated in the logs directory, that will be created in the same directory as the program.
