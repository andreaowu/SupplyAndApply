package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
		
		//===================Just to test out CurrentJobActivity==========================================
		//Intent i = new Intent(getApplicationContext(), CurrentJobActivity.class);
        //startActivity(i);
        //=============================================================================
		
        Button addJobButton = (Button) findViewById(R.id.addJobButton);
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddJobActivity.class);
                startActivityForResult(i, 1);
            }
        });

		try {
			ArrayList<ArrayList<Job>> all = (ArrayList) getLastNonConfigurationInstance();
			current = all.get(0);
			past = all.get(1);
		} catch (NullPointerException e) {
			current = new ArrayList<Job>();
			past = new ArrayList<Job>();
		}
        
		lv_current = (ListView) findViewById(R.id.current);
		lv_past = (ListView) findViewById(R.id.past);
		view = new View(this);
		
		// create the TabHost that will contain the Tabs
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        
        tab1 = tabHost.newTabSpec("Current");
        tab2 = tabHost.newTabSpec("Past");

		setTabs();
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Job newJob = (Job) data.getExtras().get(Constants.JOB);
                current.add(newJob);
                updateTabs();
                tabHost.setCurrentTab(1);
                tabHost.setCurrentTab(0);
            }
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jobs_list, menu);
		return true;
	}

    public void updateTabs() {
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

        // Add the tabs to the TabHost to display.
        tabHost.setCurrentTab(0);
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
        
        // Add the tabs to the TabHost to display.
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
	
	@Override
    public Object onRetainNonConfigurationInstance() {
		// Add the current and past job lists to this new list so upon closing and reopening this application,
		// we can still get access to the jobs already made
		ArrayList<ArrayList<Job>> all = new ArrayList<ArrayList<Job>>();
		all.add(current);
		all.add(past);
        return all;
    }

}
