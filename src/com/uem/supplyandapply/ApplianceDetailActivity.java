package com.uem.supplyandapply;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Spinner;

public class ApplianceDetailActivity extends Activity {
	Spinner spinner = (Spinner) findViewById(R.id.progress_spinner);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appliance_detail);
		//addListenerOnSpinnerItemSelection();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appliance_detail, menu);
		return true;
	}
//	public void addListenerOnSpinnerItemSelection() {
//		progressSelect = (Spinner) findViewById(R.id.progress_spinner);
//		progressSelect.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//	  }
//	
//	
//	// Create an ArrayAdapter using the string array and a default spinner layout
//	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//	        R.array.planets_array, android.R.layout.simple_spinner_item);
//	// Specify the layout to use when the list of choices appears
//	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	// Apply the adapter to the spinner
//	spinner.setAdapter(adapter);
//	 

}
