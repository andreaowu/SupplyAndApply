package com.uem.supplyandapply;

import java.util.ArrayList;

import com.uem.supplyandapply.Adapters.CurrentJobAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ListView;

public class CurrentJobActivity extends Activity {
	private CurrentJobAdapter adapter;
	private GridView gridView;
	private Job job;
	private ArrayList<ApplianceStateContainer> applianceList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("Asdasd");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_job_layout);
		//-----------Magic zone of getting job from the bundle-----//
		//Intent i = getIntent();
		//job = (Job) i.getExtras().get(Constants.JOB);
		
		applianceList = getDefaultApplianceList();
		
		adapter = new CurrentJobAdapter(getApplicationContext(), 0, applianceList);
		
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
