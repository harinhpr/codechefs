package com.nexussms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class codeChefsDataSource {
	
	  // Database fields
	  private SQLiteDatabase database;
	  private codeChefsDataTableHelper dbHelper;

	  private String tableName;
	  
	  private Context fContext;
	  
	  public codeChefsDataSource(String tableNm, Context context) {
		    dbHelper = new codeChefsDataTableHelper(context);
		    fContext = context;
		    tableName = tableNm;
		  }


	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public String displayAll()
	  
	  {
	  return(dbHelper.displayAll(tableName));
	  }
	  
	  public String[] getTableData(ArrayList<ArrayList<String>> rows) {
		return(dbHelper.getTableData(tableName, rows));  
	  }
	  
}

