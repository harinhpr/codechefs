package com.nexussms;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.*;
import android.graphics.Color;
import android.content.Context;
import android.graphics.Typeface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class MainActivity extends Activity {

	/*private EditText phoneNumber;
	private EditText smsBody;
	private Button smsManagerBtn;
	private Button smsSendToBtn;
	private Button smsViewBtn;*/
	
	private Button strServBtn;
	private Button stopServBtn;
	private Button viewOrderBtn;
	private Button viewMenuCardBtn;
	private Button viewHistoryBtn;
	private Button viewCustomerBtn;
	private TableLayout tableLayout;
	
	private enum codeChefTableID { MENU_CARD, ORDER_ITEM, CLIENT_INFO, TEXT_CHAT_HISTORY};
	private static final String[] tableNames = { "menu_card_items", "ordered_items", "client_info", "text_chat_history" };
	//private static final String[] tableNames = { "menu_card_items", "ordered_items", "client_info" };
	
	private ArrayList<codeChefsDataSource> codeChefData = new ArrayList<codeChefsDataSource>();
	
    private TableRow tableRow;
    private TextView itemText,itemValue;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		strServBtn = (Button) findViewById(R.id.startServiceBtn);
		stopServBtn = (Button) findViewById(R.id.stopServiceBtn);
		viewOrderBtn = (Button) findViewById(R.id.viewOrderBtn);
		viewMenuCardBtn = (Button) findViewById(R.id.viewMenuCardBtn);
		viewHistoryBtn = (Button) findViewById(R.id.viewHistoryBtn);
		viewCustomerBtn = (Button) findViewById(R.id.viewCustomerBtn);
		
		strServBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 starService();
			 }
		});

		stopServBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 stopService();
			 }
		});

		viewOrderBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 viewOrder();
			 }
		});
		
		viewMenuCardBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 viewMenuCard();
			 }
		});
		
		viewHistoryBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 viewHistory();
			 }
		});
		
		viewCustomerBtn.setOnClickListener(new OnClickListener() {
			 public void onClick(View view) {
				 viewCustomer();
			 }
		});
		
	}

	protected void starService() {
		// TODO Auto-generated method stub
		try {
			System.out.println("in a StartService Menthod");
			
			for(int i=0; i < tableNames.length; i++) {
			// menu card table 
				codeChefData.add(new codeChefsDataSource(tableNames[i], getApplicationContext()));
				codeChefData.get(i).open();
			}
			
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}		
	
	protected void stopService() {
		// TODO Auto-generated method stub
		String DATABASE_NAME = "menu_items.db";
		try {
			// Get the default instance of the SmsManager
			System.out.println("in a stopService methos");
			
//			for(int i=0; i < tableNames.length; i++) {
//			codeChefData.get(i).close();
//			}
			
			Context fContext = getApplicationContext();
			fContext.deleteDatabase(DATABASE_NAME);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}		
	
	protected void viewOrder() {
		// TODO Auto-generated method stub
		try {
			// Get the default instance of the SmsManager
			System.out.println("in a viewOrder method");
			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();;
			String cols[] = codeChefData.get(codeChefTableID.ORDER_ITEM.ordinal()).getTableData(rows);
	 
			 addHeaders(cols);
			 addRows(rows);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}
	
	protected void viewMenuCard() {
		// TODO Auto-generated method stub
		try {
			// Get the default instance of the SmsManager
			System.out.println("in a viewMenuCard method");
			
			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();;
			String cols[] = codeChefData.get(codeChefTableID.MENU_CARD.ordinal()).getTableData(rows);
	 
			 addHeaders(cols);
			 addRows(rows);
			
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}
	
	protected void viewHistory() {
		// TODO Auto-generated method stub
		try {
			// Get the default instance of the SmsManager
			System.out.println("in a viewHistory method");
			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();;
			String cols[] = codeChefData.get(codeChefTableID.TEXT_CHAT_HISTORY.ordinal()).getTableData(rows);
	 
			 addHeaders(cols);
			 addRows(rows);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}
	
	protected void viewCustomer() {
		// TODO Auto-generated method stub
		try {
			// Get the default instance of the SmsManager
			System.out.println("in a viewCustomer method");
/*			ArrayList<String> menuHeader= new ArrayList<String>();
			menuHeader.add("Mobile#");
			menuHeader.add("Order#");
			menuHeader.add("Status");		
*/		
		
			// each row as array list
			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();;
			String cols[] = codeChefData.get(codeChefTableID.CLIENT_INFO.ordinal()).getTableData(rows);
	 
			 addHeaders(cols);
			 addRows(rows);
		
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(),"Your sms has failed...",
					Toast.LENGTH_LONG).show();
			ex.printStackTrace();
		}		
	}
	
/*	
	  public void addHeaders(ArrayList<String> tableHeader){
			
		  tableLayout = (TableLayout) findViewById(R.id.tableView);
		  		  
		  int childCount = tableLayout.getChildCount();
		  for(int i=0; i < childCount; i++)
			  tableLayout.removeView(tableLayout.getChildAt(i));

	       
	        tableRow = new TableRow(this);
	        tableRow.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        for(String in: tableHeader)
	        {
	        	
	            TextView itemText = new TextView(this);
	            itemText.setText(in);
	            itemText.setTextColor(Color.BLUE);
	            itemText.setTextSize(10);
	            itemText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
	            itemText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	//            itemText.setPadding(0, 0, 0, 0);
	            itemText.setPaddingRelative(2, 2, 2, 2);
	            tableRow.addView(itemText);  // Adding textView to tablerow.
	        }
	        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	 
	    }
*/	  
	
	
	  public void addHeaders(String[] tableHeader){
			
		  tableLayout = (TableLayout) findViewById(R.id.tableView);
		  		  
		  int childCount = tableLayout.getChildCount();
		  for(int i=0; i < childCount; i++)
			  tableLayout.removeView(tableLayout.getChildAt(i));

	         /** Create a TableRow dynamically **/
	        tableRow = new TableRow(this);
	        tableRow.setScrollBarStyle(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL);
	        tableRow.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	        
	        for(String in: tableHeader)
	        {
	        	/** Creating a TextView to add to the row **/
	            TextView itemText = new TextView(this);
	            itemText.setText(in);
	            itemText.setTextColor(Color.BLUE);
	            
	            itemText.setTextSize(8);
	            itemText.setGravity(Gravity.CENTER);
	            itemText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
	            itemText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
	//            itemText.setPadding(0, 0, 0, 0);
	            itemText.setPaddingRelative(2, 2, 2, 2);
	            tableRow.addView(itemText);  // Adding textView to tablerow.
	        }
	        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
	 
	    }
	  
	  public void addRows(ArrayList<ArrayList<String>> tableRows){
			
        /* Create a TableRow dynamically 
	        tableRow = new TableRow(this);
	        tableRow.setLayoutParams(new LayoutParams(
	                LayoutParams.MATCH_PARENT,
	                LayoutParams.WRAP_CONTENT));
*/	                
	        for (int i = 0; i < tableRows.size(); i++)
	        {
	            tableRow = new TableRow(this);
	            tableRow.setScrollBarStyle(Gravity.FILL_HORIZONTAL|Gravity.FILL_VERTICAL);
		        tableRow.setLayoutParams(new LayoutParams(
		                LayoutParams.MATCH_PARENT,
		                LayoutParams.WRAP_CONTENT));
		        
	        	ArrayList<String> rows;
	        	rows = tableRows.get(i);
	            for(String in: rows)
		        {
		        	/** Creating a TextView to add to the row **/
		            TextView itemText = new TextView(this);
		            itemText.setText(in);
		            itemText.setTextColor(Color.GREEN);
		            itemText.setTextSize(8);
		            itemText.setGravity(Gravity.LEFT);
		            itemText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		            itemText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		//            itemText.setPadding(0, 0, 0, 0);
		            itemText.setPaddingRelative(2, 2, 2, 2);
		            tableRow.addView(itemText);  // Adding textView to tablerow.
		        }
		        tableLayout.addView(tableRow, new TableLayout.LayoutParams(
		                LayoutParams.MATCH_PARENT,
		                LayoutParams.WRAP_CONTENT));
	    }
	}
}

