SET DATABASE SQL SYNTAX ORA TRUE;

CREATE SCHEMA IF NOT EXISTS SOURCE;

CREATE TABLE "SOURCE"."TABLE_ALL_TYPES" 
   ("COLUMN1" VARCHAR (20), 
	"COLUMN2" INTEGER, 
	"COLUMN3" DATE, 
	"COLUMN4" CLOB, 
	"COLUMN5" BLOB, 
	"COLUMN6" TIMESTAMP (6), 
	"COLUMN7" BIGINT
   );