package com.uem.supplyandapply;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private ApplianceAdapter applianceAdapter;
    private AddPartsAdapter partsAdapter;
    private ListView appList;
    private ListView partsList;
    private ArrayList<ApplianceStateContainer> applianceList;
    private EditText addressText;
    private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applianceList = getDefaultApplianceList();
        System.out.println("appliance list count on create: " + applianceList.size());
        doAll();
    }
    
    protected void doAll() {
    	System.out.println("appliance list count doAll(): " + applianceList.size());
    	setContentView(R.layout.add_job_appliances_layout);
        applianceAdapter = new ApplianceAdapter(getApplicationContext(), 0, applianceList);
        partsAdapter = new AddPartsAdapter(getApplicationContext(), 0);

        appList = (ListView) findViewById(R.id.application_list);
        appList.setAdapter(applianceAdapter);

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
                setContentView(R.layout.add_new_appliance_dialog);
            	AlertDialog.Builder builder = new AlertDialog.Builder(AddJobActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = AddJobActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.add_new_appliance_dialog, null));
                
                final AlertDialog alertDialog = builder.create();
                alertDialog.setContentView(R.layout.add_new_appliance_dialog);
                
                partsList = (ListView) findViewById(R.id.parts_list);
                partsList.setAdapter(partsAdapter);
                
                Button addParts = (Button) alertDialog.findViewById(R.id.addParts_button);
                addParts.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						final EditText partName = (EditText) findViewById(R.id.add_part_name);
						partName.addTextChangedListener(new TextWatcher() {
				            
				        	@Override
				            public void afterTextChanged(Editable editable) {
				                String name = editable.toString();
				                partsAdapter.setName(name);
				            }

							@Override
							public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}

							@Override
							public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}
				        });
						
						final EditText numberBroken = (EditText) findViewById(R.id.add_part_number);;
		                numberBroken.addTextChangedListener(new TextWatcher() {
				            
				        	@Override
				            public void afterTextChanged(Editable editable) {
				                String number = editable.toString();
				                partsAdapter.setName(number);
				            }

							@Override
							public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}

							@Override
							public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}
				        });
						
		                ((ApplianceAdapter) partsList.getAdapter()).notifyDataSetChanged();
					}
                	
                });
                
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final EditText partName = (EditText) findViewById(R.id.appliance_name);
						partName.addTextChangedListener(new TextWatcher() {
				            
				        	@Override
				            public void afterTextChanged(Editable editable) {
				                String name = editable.toString();
				                final Appliance app = new Appliance(name, R.drawable.question);
				                
				                final EditText numberBroken = (EditText) findViewById(R.id.count_broken);
				                numberBroken.addTextChangedListener(new TextWatcher() {
						            
						        	@Override
						            public void afterTextChanged(Editable editable) {
						                String number = editable.toString();
						                ApplianceStateContainer appCont = new ApplianceStateContainer(app, Integer.parseInt(number));
						                System.out.println("appliance list count before: " + applianceList.size());
						                applianceList.add(appCont);
						                System.out.println("appliance list count after: " + applianceList.size());
						            }

									@Override
									public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
									}

									@Override
									public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
									}
						        });
				            }

							@Override
							public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}

							@Override
							public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
							}
				        });
						
		                ((ApplianceAdapter) appList.getAdapter()).notifyDataSetChanged();
		                dialog.dismiss();
		                doAll();
					}
				});
				
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doAll();
                    }
                });
                builder.show();
            }
        });

        appList.addFooterView(footerView);
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
