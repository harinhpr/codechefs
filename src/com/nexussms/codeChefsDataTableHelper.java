package com.nexussms;

import java.io.File;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.nexussms.R;

import android.content.Context;
import android.content.ContentValues;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class codeChefsDataTableHelper extends SQLiteOpenHelper {

//	private final String TAG_FOOD = "food";
	private static final String DATABASE_NAME = "menu_items.db";
//	private static final String menu_card_table_name = "menu_card_items";
//	private static final String ordered_items_table_name = "ordered_items";

	private static final int [] xmlResID = {R.xml.menu_card_items, R.xml.order_items, R.xml.client_info, R.xml.text_conversation_history };
	private static final String[] tableNames = { "menu_card_items", "ordered_items", "client_info", "text_chat_history" };
	private static final String[] tagNames = { "food", "order", "client", "text_chat" };
	
	private static final int DATABASE_VERSION = 1;

	// private static menuCardItemTable menu_card_table = null;
	private final Context fContext;

	codeChefsDataTableHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		fContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		for(int i=0; i < codeChefsDataTableHelper.xmlResID.length; i++) {
		
			// create table
		createCodeChefsTable(db, xmlResID[i] , tableNames[i], tagNames[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		for(int i=0; i < codeChefsDataTableHelper.xmlResID.length; i++) {
		Log.w(codeChefsDataTableHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + tableNames[i]);
		}
		onCreate(db);

	}
	
	private void insertToDatabase(String tableName, ArrayList<String> fields) {
		
	}
	
	public String[] getTableData(String tableName, ArrayList<ArrayList<String>> rows) {
	
//		rows = new ArrayList<ArrayList<String>>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor allrows  = db.rawQuery("SELECT * FROM "+  tableName, null);
		
		String [] cols = allrows.getColumnNames();
		
		allrows.moveToFirst();
		do {
			
			ArrayList<String> oneRow = new ArrayList<String>();
			for(int i =0; i < cols.length; i++)			{
				oneRow.add(allrows.getString(allrows.getColumnIndex(cols[i])));
			}
			rows.add(oneRow);
			} while(allrows.moveToNext());
		return(cols);
	}
	
	public String displayAll(String tableName)
	{
		ArrayList<String> values = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		        
		Cursor allrows  = db.rawQuery("SELECT * FROM "+  tableName, null);
		allrows.moveToFirst();
		while(allrows.moveToNext()){
			String [] cols = allrows.getColumnNames();
			StringBuilder tableAsString = new StringBuilder();
			for(int i =0; i < cols.length; i++)
			{
				tableAsString.append(cols[i] + ":");
				tableAsString.append(allrows.getString(allrows.getColumnIndex(cols[i])));
				tableAsString.append(" ");
			}
		      values.add(tableAsString.toString()); 
		}
		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 //               android.R.layout.simple_list_item_1, values);
		 //       setListAdapter(adapter);
		return(values.toString());
	}
	
	
	private ArrayList<String> getFieldList() {
		ArrayList<String> fields = new ArrayList<String>();

		fields.add("name");
		fields.add("price");
		fields.add("description");
		fields.add("calories");

		return (fields);
	}
	
	private void createCodeChefsTable(SQLiteDatabase db, int xmlFildResID, String tableName, String tagName) {
		
		boolean bCreated = false;
		tableQueryPreparer tQuery = new tableQueryPreparer();

		//		Log.w("createMenuCardTable", Environment.getDataDirectory().toString());

		try {
			Resources res = fContext.getResources();
			XmlResourceParser _xml = res.getXml(xmlFildResID);
			try
			{
				//Check for end of document
			int eventType = _xml.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Search for record tags
				if ((eventType == XmlPullParser.START_TAG)
						&& (_xml.getName().equals(tagName))) {

					ContentValues _Values = new ContentValues();

					ArrayList<String> fields = new ArrayList<String>();
					
					for (int iAttrs = 0; iAttrs < _xml.getAttributeCount(); iAttrs++) {

						// Record tag found, now get name of field, values
						String _fieldName = _xml.getAttributeName(iAttrs);

						String _fieldValue = _xml.getAttributeValue(null, _fieldName);

						_Values.put(_fieldName, _fieldValue);
						
						if(bCreated == false)
						{
							fields.add(_fieldName);
						}
					}

					// if attrs present
					if(_Values.size() > 0)
					{
						// create first time
						if(bCreated == false)
						{
							db.execSQL(tQuery.createTableQuery(tableName, fields));
							bCreated = true;
						}
						
						// add rows
						db.insert(tableName, "ABCDEF", _Values);
					}
				}
				eventType = _xml.next();
			}
		}
		// Catch errors
		catch (XmlPullParserException e) {
			Log.e(codeChefsDataTableHelper.class.getName(), e.getMessage(), e);
		} finally {
			// Close the xml file
			_xml.close();
		}
	}
		 catch (Exception e) {
				Log.e(codeChefsDataTableHelper.class.getName(), e.getMessage(), e);

			}
	}
}

