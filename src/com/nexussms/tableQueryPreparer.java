package com.nexussms;

import java.util.ArrayList;

public class tableQueryPreparer {
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS ";
	
	private static final String TEXT_DATA_TYPE = " VARCHAR";
	private static final String DATA_NOT_NULL = " not null";
	private static final String COL_SEPARATOR= ",";
	private static final String STR_QUOTE = "'";
	private static final String BEFORE_START_FIELDS= "(";
	private static final String AFTER_END_FIELDS= ");";
	
	//sqlite> CREATE TABLE COMPANY(
//	   ID INT PRIMARY KEY     NOT NULL,
//	   NAME           TEXT    NOT NULL,
//	   AGE            INT     NOT NULL,
//	   ADDRESS        CHAR(50),
//	   SALARY         REAL
//	);
	public String createTableQuery(String tableName, ArrayList<String> fields)
	{
		StringBuilder queryStr = new StringBuilder();
		
		// add database name
		queryStr.append(tableQueryPreparer.DATABASE_CREATE + tableName + tableQueryPreparer.BEFORE_START_FIELDS);
		
		// add fields
		for (int i = 0; i < fields.size(); i++) 
		{
            queryStr.append(fields.get(i));
            queryStr.append(tableQueryPreparer.TEXT_DATA_TYPE);
            //queryStr.append(tableQueryPreparer.DATA_NOT_NULL);
            
            if(i < (fields.size() -1))
            	queryStr.append(tableQueryPreparer.COL_SEPARATOR);
			
            //System.out.println(fields.get(i));
            
        }
		queryStr.append(tableQueryPreparer.AFTER_END_FIELDS);
		//System.out.println(queryStr.toString());
		return(queryStr.toString());
	}
			
	//sqlite> SELECT * FROM TABLE MENUCARD where fieldName LIKE '%valur%'

		public String createSelectQuery(String tableName, String fieldName, String value)
		{
			StringBuilder queryStr = new StringBuilder();

			// select from table

			queryStr.append("SELECT " + "*" + "FROM "+tableName + "WHERE "+ fieldName + "LIKE "				
			+tableQueryPreparer.STR_QUOTE+"%"+value+"%"	+tableQueryPreparer.STR_QUOTE);

			return(queryStr.toString());
		}
		
}

