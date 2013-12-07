package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.uem.supplyandapply.Adapters.CurrentJobAdapter;

public class CurrentJobActivity extends Activity {
	
	private CurrentJobAdapter adapter;
	private GridView gridView;
	private Job job;
	private ArrayList<ApplianceStateContainer> applianceList;
	private HashMap<String, ApplianceStateContainer> broken;
	
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
	    broken = job.getBroken();
	    final String cName = c.getName();
	    
		applianceList = getApplianceList();
		
		adapter = new CurrentJobAdapter(getApplicationContext(), 0, applianceList, new Intent(getApplicationContext(), ApplianceListActivity.class));
		
		gridView = (GridView) findViewById(R.id.appliances_gridView);
        gridView.setAdapter(adapter);
        
        Button removeJob = (Button) findViewById(R.id.removeJob_button);
        removeJob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert_box = new AlertDialog.Builder(CurrentJobActivity.this);
				alert_box.setMessage("Are you sure you want to remove this job?");
				alert_box.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				   
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
					   	Intent intent = new Intent(getApplicationContext(), JobsListActivity.class);
		                intent.putExtra(Constants.DELETE_JOB, cName);
		                startActivity(intent);
				   }
				  });
				alert_box.setNegativeButton("No", new DialogInterface.OnClickListener() {
				   
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
				    Toast.makeText(getApplicationContext(), "Job Not Removed", Toast.LENGTH_LONG).show();
				   }
				  });
				alert_box.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_job, menu);
		return true;
	}
	private ArrayList<ApplianceStateContainer> getApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
		for (Map.Entry<String, ApplianceStateContainer> entry : broken.entrySet()) {
			String key = entry.getKey();
			appliances.add(new ApplianceStateContainer(new Appliance(key, broken.get(key).getAppliance().getDrawableResource()), broken.get(key).getCount()));
		}
       
        return appliances;
    }

}