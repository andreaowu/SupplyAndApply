package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class JobsListActivity extends Activity {
	
	// List of all the current jobs
	private ArrayList<Job> current;
	// List of all the past jobs
	private ArrayList<Job> past;

	ListView lv_current;
	ListView lv_past;
	View view;
	
	TabSpec tab1;
	TabSpec tab2;
	TabHost tabHost;
	
	ArrayAdapter<String> arrayAdapter_current;
	ArrayAdapter<String> arrayAdapter_past;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobs_list_layout);
		
		current = new ArrayList<Job>();
		// For testing purposes only
		// Create new Customer and new Job for current
		Customer c1 = new Customer("Andrea", "2461 Hilgard Ave");
		Job j1 = new Job(c1, new HashMap<String, ArrayList<String>>(), new HashMap<String, Integer>());
		current.add(j1);
		
		past = new ArrayList<Job>();
		// Create new Customer and new Job for current
		Customer c2 = new Customer("Carina", "43634 Euclid Dr");
		Job j2 = new Job(c2, new HashMap<String, ArrayList<String>>(), new HashMap<String, Integer>());
		past.add(j2);
		
		lv_current = (ListView) findViewById(R.id.current);
		lv_past = (ListView) findViewById(R.id.past);
		view = new View(this);
		
		// create the TabHost that will contain the Tabs
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        tab1 = tabHost.newTabSpec("Current");
        tab2 = tabHost.newTabSpec("Past");
		
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
	 */
	public void setTabs() {
        
        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Current");
        
        // Change jobs to strings of name and address to display
        ArrayList<String> currentString = new ArrayList<String>();
        for (int i = 0; i < current.size(); i++) {
        	currentString.add(current.get(i).getDisplay());
        }
        
        arrayAdapter_current = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currentString);
        lv_current.setAdapter(arrayAdapter_current);
        tab1.setContent(R.id.current);
        
        // Change jobs to strings of name and address to display
        ArrayList<String> pastString = new ArrayList<String>();
        for (int i = 0; i < past.size(); i++) {
        	pastString.add(past.get(i).getDisplay());
        }
        tab2.setIndicator("Past");
        arrayAdapter_past = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pastString);
        lv_past.setAdapter(arrayAdapter_past);
        tab2.setContent(R.id.past);
        
        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.setCurrentTab(0);
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
        	
			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("Current")) {
			        tabHost.setCurrentTab(0);
				} else {
			        tabHost.setCurrentTab(1);
				}
			}
        });
	}

}
