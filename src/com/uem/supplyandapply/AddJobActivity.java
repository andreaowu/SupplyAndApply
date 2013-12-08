package com.uem.supplyandapply;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.uem.supplyandapply.Adapters.AddPartsAdapter;
import com.uem.supplyandapply.Adapters.ApplianceAdapter;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 3:45 PM
 */
public class AddJobActivity extends Activity {

    private ApplianceAdapter adapter_appliance;
    private AddPartsAdapter adapter_parts;
    private ListView listView_applist;
    private ListView listView_partsList;
    private ArrayList<ApplianceStateContainer> applianceList;
    private EditText addressText;
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_appliances_layout);
        applianceList = getDefaultApplianceList();
        adapter_appliance = new ApplianceAdapter(getApplicationContext(), 0, applianceList);
        adapter_parts = new AddPartsAdapter(getApplicationContext(), 0);

        listView_applist = (ListView) findViewById(R.id.application_list);
        listView_applist.setAdapter(adapter_appliance);

        nameText = (EditText) findViewById(R.id.input_name);
        addressText = (EditText) findViewById(R.id.input_address);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ApplianceStateContainer> applianceStateContainers = new ArrayList<ApplianceStateContainer>();
                for (ApplianceStateContainer applianceStateContainer : applianceList) {
                    if (applianceStateContainer.getCount() != 0) {
                        applianceStateContainer.generateAppliances();
                    	applianceStateContainers.add(applianceStateContainer);
                    }
                }

                String name = nameText.getText().toString();
                String address = addressText.getText().toString();

                Intent i = new Intent(getApplicationContext(), PartsEstimationActivity.class);
                i.putExtra(Constants.APPLIANCE_LIST, applianceStateContainers);
                i.putExtra(Constants.ADDRESS, address);
                i.putExtra(Constants.NAME, name);
                startActivityForResult(i, 1);
            }
        });
        
        
        Button addApplianceButton = (Button) findViewById(R.id.addAppliance_button);
        addApplianceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	//listView_partsList = (ListView) findViewById(R.id.parts_list);
                //listView_partsList.setAdapter(adapter_parts);
            	
                setContentView(R.layout.add_new_appliance_dialog);
            	AlertDialog.Builder builder = new AlertDialog.Builder(AddJobActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = AddJobActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.add_new_appliance_dialog, null));
                
                final AlertDialog alertDialog = builder.create();
                
                Button addParts = (Button) findViewById(R.id.addParts_button);
                addParts.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						AlertDialog.Builder builderPart = new AlertDialog.Builder(AddJobActivity.this);
		                // Get the layout inflater
		                LayoutInflater inflater = AddJobActivity.this.getLayoutInflater();

		                // Inflate and set the layout for the dialog
		                // Pass null as the parent view because its going in the dialog layout
		                builderPart.setView(inflater.inflate(R.layout.add_part, null));
		                final EditText addPartName = (EditText) findViewById(R.id.part_name);
		                final EditText addPartCount = (EditText) findViewById(R.id.part_number);
		                Button ok = (Button) findViewById(R.id.addPart_ok_button);
		                Button cancel = (Button) findViewById(R.id.addPart_cancel_button);
		                
		                final AlertDialog alertDialog = builderPart.create();
		                ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								adapter_parts.setName(addPartName.toString());
								adapter_parts.setName(addPartCount.toString());
								
							}
							
		                });
						
		                cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								alertDialog.dismiss();
							}
		                	
		                });

		                builderPart.show();
					}
                	
                });
                
                Button ok = (Button) findViewById(R.id.addNewAppliance_ok_button);
                Button cancel = (Button) findViewById(R.id.addNewAppliance_cancel_button);
                
                ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText partName = (EditText) findViewById(R.id.appliance_name);
		                final EditText numberBroken = (EditText) findViewById(R.id.count_broken);
		                Appliance app = new Appliance(partName.toString(), R.drawable.question);
		                
						
					}
					
                });
				
                cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alertDialog.dismiss();
					}
                	
                });
                
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent returnIntent = new Intent();
                Job newJob = (Job) data.getExtras().get(Constants.NEW_JOB);
                returnIntent.putExtra(Constants.NEW_JOB, newJob);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
    }

    private ArrayList<ApplianceStateContainer> getDefaultApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
        appliances.add(new ApplianceStateContainer(new Appliance("Shower", R.drawable.showerhead), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Toilet", R.drawable.toilet), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Sink", R.drawable.sink), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Custom", R.drawable.question), 0));
        for (ApplianceStateContainer applianceStateContainer : appliances) {
            Appliance appliance = applianceStateContainer.getAppliance();
            String applianceName = appliance.getName();
            ArrayList<SupplyPart> supplyParts = new ArrayList<SupplyPart>();
            supplyParts.add(new SupplyPart(1, applianceName + " Part1"));
            supplyParts.add(new SupplyPart(2, applianceName + " Part2"));
            appliance.setPartsList(supplyParts);
            applianceStateContainer.setAppliance(appliance);
        }
        return appliances;
    }
}
