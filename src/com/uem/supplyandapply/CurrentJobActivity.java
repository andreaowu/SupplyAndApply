package com.uem.supplyandapply;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import com.uem.supplyandapply.Adapters.CurrentJobAdapter;

public class CurrentJobActivity extends Activity {
	
	private CurrentJobAdapter adapter;
	private GridView gridView;
	private Job job;
	private ArrayList<ApplianceStateContainer> applianceList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_job_layout);
		
		// Get job from the saved intent
		Job job = (Job) getIntent().getSerializableExtra("Job");

		// Display correct customer information
		Customer c = job.getC();
		TextView name = (TextView) findViewById(R.id.customer_textView); 
	    name.setText(c.getName());
	    TextView address = (TextView) findViewById(R.id.address_textview); 
	    address.setText(c.getAddress());
		
		applianceList = getDefaultApplianceList();
		
		adapter = new CurrentJobAdapter(getApplicationContext(), 0, applianceList, new Intent(getApplicationContext(), ApplianceListActivity.class));
		
		gridView = (GridView) findViewById(R.id.appliances_gridView);
        gridView.setAdapter(adapter);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_job, menu);
		return true;
	}
	private ArrayList<ApplianceStateContainer> getDefaultApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
        appliances.add(new ApplianceStateContainer(new Appliance("Shower", R.drawable.showerhead), 5));
        appliances.add(new ApplianceStateContainer(new Appliance("Toilet", R.drawable.toilet), 6));
        appliances.add(new ApplianceStateContainer(new Appliance("Sink", R.drawable.sink), 7));
       
        return appliances;
    }

}