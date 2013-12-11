package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.widget.*;

import com.uem.supplyandapply.Adapters.CurrentJobAdapter;

public class CurrentJobActivity extends Activity {
	
	private CurrentJobAdapter adapter;
	private GridView gridView;
	private ArrayList<ApplianceStateContainer> applianceList;
	private HashMap<String, ApplianceStateContainer> broken;
	private ProgressBar progress;
	private int totalProgress, progressStatus;
    private Job job;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_job_layout);
		
		
		progress = (ProgressBar) findViewById(R.id.progress_bar);
		
		// Get job from the saved intent
		job = (Job) getIntent().getSerializableExtra(Constants.JOB);

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
		
        updateGridView();

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
                startActivityForResult(intent, 5);
			}
        	
        });
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SUPANDAPPREFS, Context.MODE_PRIVATE);
        boolean seenJobsPage = sharedPreferences.getBoolean(Constants.SEENCURRENTJOB, false);
        if (!seenJobsPage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This is the Job Screen. If you haven't started working on " +
            		"the job, press 'Start Job' to customize the details of appliances. The icons " +
            		"indicate the appliance groups in this job. Pressing on an icon takes you to a detailed " +
            		"view of the appliances in that group. Pressing 'View Part' to view how many parts " +
            		"are left to complete the job. You can delete this job by pressing the 'X' on the " +
            		"top right when you are done with it.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        	SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putBoolean(Constants.SEENCURRENTJOB, true);
                            editor.commit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        updateProgressBar();
	}

    private void updateGridView() {
        adapter = new CurrentJobAdapter(getApplicationContext(), 0, applianceList, new Intent(
                getApplicationContext(), ApplianceListActivity.class));

        gridView = (GridView) findViewById(R.id.appliances_gridView);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplianceStateContainer applianceStateContainer =
                        (ApplianceStateContainer) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ApplianceListActivity.class);
                intent.putExtra(Constants.APPCONTAINER, applianceStateContainer);
                startActivityForResult(intent, 4);
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
		} else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                ApplianceStateContainer applianceStateContainer =
                        (ApplianceStateContainer) data.getExtras().get(Constants.APPCONTAINER);
                HashMap<String, ApplianceStateContainer> map = job.getBroken();
                String appStatName = applianceStateContainer.getAppliance().getName();
                ApplianceStateContainer oldStateContainer = map.get(appStatName);
                if (oldStateContainer != null) {
                    map.remove(appStatName);
                    map.put(appStatName, applianceStateContainer);
                }
                broken = map;
                job.setBroken(map);

                //update needed map
                job.calculatePartsNeeded();

                //update progress bar and count
                updateProgressBar();

                applianceList = getApplianceList();
                updateGridView();
            }
        } else if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Job newJob = (Job) data.getExtras().get(Constants.JOB);
                job = newJob;
                broken = job.getBroken();
                applianceList = getApplianceList();
                updateGridView();
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

    private void updateProgressBar() {
        ArrayList<ApplianceStateContainer> applianceStateContainers = new ArrayList<ApplianceStateContainer>();
        applianceStateContainers.addAll(job.getBroken().values());

        int completed = 0;
        int total = 0;

        for (ApplianceStateContainer applianceContainer : applianceStateContainers) {
            for (Appliance appliance : applianceContainer.getAppliances()) {
                total += 1;
                if (appliance.getProgress().equals(Progress.COMPLETED)) {
                    completed += 1;
                }
            }
        }

        progress.setMax(total);
        progress.setProgress(completed);
    }

}