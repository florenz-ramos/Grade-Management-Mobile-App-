package com.calyx.dev.app;


public class SQLITEParams
{
	public static final String DBNAME="grading.db";
	public static final String[] TABLES={"tblclass","tblstudents","tblgrades"};
	public static final String var="CREATE TABLE ";
	public static final String[] CREATE={
		"CREATE TABLE "+TABLES[0]+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CLASSID TEXT)",
		"CREATE TABLE "+TABLES[1]+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,FULLNAME TEXT,CLASSID TEXT)",
		"CREATE TABLE "+TABLES[2]+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,STUDENTID TEXT,PRELIM TEXT,MIDTERM TEXT,FINALS TEXT,AVERAGE TEXT,CLASSID TEXT)"		
	};


}
