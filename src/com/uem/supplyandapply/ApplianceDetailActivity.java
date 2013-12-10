package com.uem.supplyandapply;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ApplianceDetailActivity extends Activity {
	Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appliance_detail);
		
	
		final Appliance appliance = (Appliance) getIntent().getSerializableExtra(Constants.APPLIANCE);
		
		//progress dropdown
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.progress_select, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.progress_spinner);
        spinner.setAdapter(adapter);
		if (appliance.getProgress() != null){
			spinner.setPrompt(appliance.getProgress().toString());
			//appliance.setProgress()
		}
		else{
			spinner.setPrompt("Not Started");
		}
		
		//image of appliance
		ImageView image = (ImageView)findViewById(R.id.image_of_appliance);
		
		Boolean fileFound = true;
		int d = 0;
		d = appliance.getDrawableResource();
		
		if(d!=0){
			image.setImageResource(appliance.getDrawableResource());
		}

		Button apply = (Button) findViewById(R.id.next_button);
        
        //Apply Button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CurrentJobActivity.class);
                startActivity(i);
            }
        });
        
		//the name of appliance
		TextView name = (TextView) findViewById(R.id.name_of_appliance); 
		if (appliance.getName()!= null){
			name.setText(appliance.getName());
			appliance.setName(name.getText().toString());
		}
	
		
		//putting in the parts estimation
		
		//issue
		TextView issues = (TextView) findViewById(R.id.issues_textbox); 
		if (appliance.getIssues() != null){
			name.setText(appliance.getIssues());
			appliance.setIssues(issues.getText());
			
		}
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean seenJobsPage = sharedPreferences.getBoolean(Constants.SEENJOBSPAGE, false);
        if (!seenJobsPage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Here you can customize the type, status, and parts of this appliance. " +
            		"You can also leave a notes to describe any issues about this appliance.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPreferences.edit().putBoolean(Constants.SEENJOBSPAGE, true);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appliance_detail, menu);
		return true;
	}


}
