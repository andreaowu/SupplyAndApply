package com.uem.supplyandapply;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ApplianceDetailActivity extends Activity {
	Spinner spinner = (Spinner) findViewById(R.id.progress_spinner);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appliance_detail);
		
		
		//final Appliance = (Appliance) getIntent().getSerializableExtra(Constants.JOB);
		
		//progress dropdown
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.progress_select, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		//the name of appliance
		TextView name = (TextView) findViewById(R.id.name_of_appliance); 
		//name.setText(c.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appliance_detail, menu);
		return true;
	}


}
