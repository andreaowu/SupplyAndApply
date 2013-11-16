package com.uem.supplyandapply;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class JobsListActivity extends Activity {
	
	// List of all the current jobs
	private ArrayList<Job> current;
	// List of all the past jobs
	private ArrayList<Job> past;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobs_list_layout);
		
		current = new ArrayList<Job>();
		past = new ArrayList<Job>();
		
		setTabs();
		allJobs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jobs_list, menu);
		return true;
	}
	
	public void allJobs() {
		
	}
	
	/*
	 * Sets up the "Current" and "Past" tabs in the all-jobs screen.
	 * 
	 */
	public void setTabs() {
		// create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        TabSpec tab1 = tabHost.newTabSpec("Current");
        TabSpec tab2 = tabHost.newTabSpec("Past");

       // Set the Tab name and Activity
       // that will be opened when particular Tab will be selected
        tab1.setIndicator("Current");
        
        tab2.setIndicator("Past");
        
        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
	}

}
