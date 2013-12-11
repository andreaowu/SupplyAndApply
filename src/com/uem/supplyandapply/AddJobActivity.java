package com.uem.supplyandapply;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.uem.supplyandapply.Adapters.ApplianceAdapter;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 3:45 PM
 */
public class AddJobActivity extends Activity {

    private ApplianceAdapter applianceAdapter;
    private ListView listViewApplist;
    private ArrayList<ApplianceStateContainer> applianceList;
    private EditText addressText;
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applianceList = getDefaultApplianceList();
        System.out.println("appliance list count on create: " + applianceList.size());
        doAll();
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SUPANDAPPREFS, Context.MODE_PRIVATE);
        boolean seenJobsPage = sharedPreferences.getBoolean(Constants.SEENADDJOB, false);
        if (!seenJobsPage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You can edit the number of appliances needed to be fixed in " +
            		"this job. If any types of appliances are not included, regard them as " +
            		"Custom Appliances. You can change the details of each appliance later.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putBoolean(Constants.SEENADDJOB, true);
                            editor.commit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    
    protected void doAll() {
    	System.out.println("appliance list count doAll(): " + applianceList.size());
    	setContentView(R.layout.add_job_appliances_layout);
        applianceAdapter = new ApplianceAdapter(getApplicationContext(), 0, applianceList);

        listViewApplist = (ListView) findViewById(R.id.application_list);
        listViewApplist.setAdapter(applianceAdapter);

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


        View footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.add_application_layout, null, false);

        Button addApplianceButton = (Button) footerView.findViewById(R.id.addAppliance_button);
        addApplianceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.add_new_appliance_dialog);
            	AlertDialog.Builder builder = new AlertDialog.Builder(AddJobActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = AddJobActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                View popup = inflater.inflate(R.layout.add_new_appliance_dialog, null);
                builder.setView(popup);

                final EditText editApplianceNum = (EditText) popup.findViewById(R.id.count_broken);
                final EditText editApplianceName = (EditText) popup.findViewById(R.id.edit_app_name);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setContentView(R.layout.add_new_appliance_dialog);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
                        String appName = editApplianceName.getText().toString();
                        Editable editable = editApplianceNum.getText();
                        int appNum = 0;
                        String appNumStr = "";
                        if (editable != null) {
                            appNumStr = editable.toString();
                        }
                        if (!appNumStr.equals("")) {
                            appNum = Integer.parseInt(appNumStr);
                        }

                        ApplianceStateContainer applianceStateContainer =
                                new ApplianceStateContainer(new Appliance(appName, R.drawable.question), appNum);
                        applianceList.add(applianceStateContainer);
                        applianceAdapter.notifyDataSetChanged();
		                dialog.dismiss();
					}
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        listViewApplist.addFooterView(footerView);
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
        for (ApplianceStateContainer applianceStateContainer : appliances) {
            Appliance appliance = applianceStateContainer.getAppliance();
            String applianceName = appliance.getName();
            ArrayList<SupplyPart> supplyParts = new ArrayList<SupplyPart>();
            if (applianceName.equals("Toilet")) {
            	supplyParts.add(new SupplyPart(6, "washers"));
            	supplyParts.add(new SupplyPart(3, "5-cm pipes"));
            } else if (applianceName.equals("Sink")) {
            	supplyParts.add(new SupplyPart(8, "2-cm screws"));
            } else if (applianceName.equals("Shower")) {
            	supplyParts.add(new SupplyPart(1, "showerhead"));
            	supplyParts.add(new SupplyPart(2, "4-inch tubes"));
            }
            appliance.setPartsList(supplyParts);
            applianceStateContainer.setAppliance(appliance);
        }
        return appliances;
    }
}
