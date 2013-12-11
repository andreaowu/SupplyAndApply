package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import com.uem.supplyandapply.Adapters.SupplyPartsAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewPartsActivity extends Activity {
	private SupplyPartsAdapter adapter;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_parts_layout);
		
		Job job = (Job) getIntent().getSerializableExtra(Constants.JOB);
		
		HashMap<String, ApplianceStateContainer> broken = job.getBroken();
		
		HashMap<String, SupplyPart> counts = new HashMap<String, SupplyPart>();
		
		for (ApplianceStateContainer applianceGroup : broken.values()) {
			ArrayList<SupplyPart> groupParts = applianceGroup.getUnfinishedPartsList();
			for (SupplyPart supplyPart : groupParts) {
				if (counts.containsKey(supplyPart.getName())) {
					SupplyPart foundPart = counts.get(supplyPart.getName());
					int oldCount = foundPart.getCount();
					foundPart.setCount(oldCount + supplyPart.getCount());
				} else {
					counts.put(supplyPart.getName(), new SupplyPart(supplyPart.getCount(), supplyPart.getName()));
				}
			}
		}
		
		ArrayList<SupplyPartComparable> result = new ArrayList<SupplyPartComparable>();
		ArrayList<SupplyPart> resultList = new ArrayList<SupplyPart>();
    	for (SupplyPart v : counts.values()) {
    		result.add(new SupplyPartComparable(v.getCount(), v.getName()));
    	}
    	Collections.sort(result);
    	for (SupplyPartComparable spc : result) {
    		resultList.add(new SupplyPart(spc.getCount(), spc.getName()));
    	}
    	
    	
    	adapter = new SupplyPartsAdapter(getApplicationContext(), 0, resultList);
    	listView = (ListView) findViewById(R.id.application_list);
        listView.setAdapter(adapter);
        Button okayButton = (Button) findViewById(R.id.okay);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SUPANDAPPREFS, Context.MODE_PRIVATE);
        boolean seenJobsPage = sharedPreferences.getBoolean(Constants.SEENVIEWPART, false);
        if (!seenJobsPage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Here are the parts left. You can gauge your supply with these.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        	SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putBoolean(Constants.SEENVIEWPART, true);
                            editor.commit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
		
        
	}
}