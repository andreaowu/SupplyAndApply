package com.uem.supplyandapply;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ApplianceDetailActivity extends Activity {
	Spinner spinner = (Spinner) findViewById(R.id.progress_spinner);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appliance_detail);
		
	
		final Appliance appliance = (Appliance) getIntent().getSerializableExtra(Constants.APPLIANCE);
		
		//progress dropdown
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.progress_select, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		if (appliance.getProgress() != null){
			spinner.setPrompt(appliance.getProgress().toString());
		}
		else{
			spinner.setPrompt("Not Started");
		}
		//image of appliance
//		ImageView image = (ImageView)findViewById(R.id.image_of_appliance);
//		
//		Boolean fileFound = true;
//		int d = 0;
//		d = appliance.getDrawableResource();
//		
//		if(d!=0){
//			image.setImageResource(appliance.getDrawableResource());
//		}
//
//		
//		if (appliance.getDrawableResource() != 0){
//			
//		}
		
		//the name of appliance
		TextView name = (TextView) findViewById(R.id.name_of_appliance); 
		if (appliance.getName()!= null){
			name.setText(appliance.getName());
		}
		
		
		//putting in the parts estimation
		
		//issue
		TextView issues = (TextView) findViewById(R.id.issues_textbox); 
		if (appliance.getIssues() != null){
			name.setText(appliance.getIssues());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appliance_detail, menu);
		return true;
	}


}
