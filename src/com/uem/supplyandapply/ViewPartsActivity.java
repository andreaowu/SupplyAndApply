package com.uem.supplyandapply;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewPartsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_parts_layout);
		
		Job job = (Job) getIntent().getSerializableExtra(Constants.JOB);
		
		HashMap<String, ApplianceStateContainer> broken = job.getBroken();
		
        for (Map.Entry<String, ApplianceStateContainer> entry : broken.entrySet()) {
                String key = entry.getKey();
                TextView partsName = (TextView) findViewById(R.id.parts_name);
                TextView partsNumber = (TextView) findViewById(R.id.parts_number);
                partsName.setText(key);
                partsNumber.setText(entry.getValue().getPartsList().toString()); // change this
        }
        
	}
}