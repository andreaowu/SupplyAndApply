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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uem.supplyandapply.Adapters.CurrentJobAdapter;

public class CurrentJobActivity extends Activity {
	
	private CurrentJobAdapter adapter;
	private GridView gridView;
	private ArrayList<ApplianceStateContainer> applianceList;
	private HashMap<String, ApplianceStateContainer> broken;
	private ProgressBar progress;
	private int totalProgress, progressStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_job_layout);
		
		
		progress = (ProgressBar) findViewById(R.id.progress_bar);
		
		// Get job from the saved intent
		final Job job = (Job) getIntent().getSerializableExtra(Constants.JOB);

        Button startJob = (Button) findViewById(R.id.startJob_button);
        if (!job.isJobStarted()) {
            startJob.setVisibility(View.VISIBLE);
            startJob.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), StartJobActivity.class);
                    intent.putExtra(Constants.JOB, job);
                    //Code of 2 is for Start Job
                    startActivityForResult(intent, 2);
                }
            });
        }

		// Display correct customer information
		Customer c = job.getC();
		TextView name = (TextView) findViewById(R.id.customer_textView); 
	    name.setText(c.getName());
	    TextView address = (TextView) findViewById(R.id.address_textview); 
	    address.setText(c.getAddress());
	    broken = job.getBroken();
	    final String cName = c.getName();
	    
		applianceList = getApplianceList();
		totalProgress = getTotalProgress(applianceList);
		progressStatus =  getProgressStatus(applianceList);
		
		progress.setMax(totalProgress);
		progress.setProgress(totalProgress - progressStatus);
		
		adapter = new CurrentJobAdapter(getApplicationContext(), 0, applianceList, new Intent(getApplicationContext(), ApplianceListActivity.class));
		
		gridView = (GridView) findViewById(R.id.appliances_gridView);
        gridView.setAdapter(adapter);
        
        TextView removeJob = (TextView) findViewById(R.id.removeJob);
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
		                startActivityForResult(intent, 1);
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
        
        Button viewParts = (Button) findViewById(R.id.viewParts_button);
        viewParts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ViewPartsActivity.class);
                intent.putExtra(Constants.JOB, job);
                startActivityForResult(intent, 1);
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_job, menu);
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            	applianceList = getApplianceList();
        		totalProgress = getTotalProgress(applianceList);
        		progressStatus =  getProgressStatus(applianceList);
        		
        		progress.setMax(totalProgress);
        		progress.setProgress(totalProgress - progressStatus);
            }
		}
	}
	
	private ArrayList<ApplianceStateContainer> getApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
		for (Map.Entry<String, ApplianceStateContainer> entry : broken.entrySet()) {
			appliances.add(entry.getValue());
		}
       
        return appliances;
    }
	private int getTotalProgress(ArrayList<ApplianceStateContainer> appliances) {
		int result = 0;
		for (ApplianceStateContainer applianceGroup : appliances) {
			result += applianceGroup.getCount();
		}
		return result;
	}
	private int getProgressStatus(ArrayList<ApplianceStateContainer> appliances) {
		int result = 0;
		for (ApplianceStateContainer applianceGroup : appliances) {
			result += applianceGroup.getNotFinished();
		}
		return result;
	}

}