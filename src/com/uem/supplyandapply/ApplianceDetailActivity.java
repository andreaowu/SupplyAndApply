package com.uem.supplyandapply;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.uem.supplyandapply.Adapters.SupplyPartsAdapter;

import java.util.ArrayList;

public class ApplianceDetailActivity extends Activity {
	private Spinner spinner;
    private Appliance appliance;
    private ArrayList<SupplyPart> parts;
    private ListView listView;
    private SupplyPartsAdapter supplyPartsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appliance_detail);
		
	
		appliance = (Appliance) getIntent().getSerializableExtra(Constants.APPLIANCE);
		
		//image of appliance
		ImageView image = (ImageView)findViewById(R.id.image_of_appliance);
		
		int d = 0;
		d = appliance.getDrawableResource();
		
		if(d != 0){
			image.setImageResource(appliance.getDrawableResource());
		}

		Button apply = (Button) findViewById(R.id.next_button);
        final EditText applianceName = (EditText) findViewById(R.id.appliance_name);
        final EditText applianceLocation = (EditText) findViewById(R.id.appliance_location);
        final EditText applianceIssues = (EditText) findViewById(R.id.issues_textbox);

        //progress dropdown
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.progress_select, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) findViewById(R.id.progress_spinner);
        spinner.setAdapter(spinnerAdapter);

        if (appliance.getProgress() != null){
            Progress appProgress = appliance.getProgress();
            if (appProgress.equals(Progress.COMPLETED)) {
                spinner.setSelection(2);
            } else if (appProgress.equals(Progress.IN_PROGRESS)) {
                spinner.setSelection(1);
            } else {
                spinner.setSelection(0);
            }
        } else{
            spinner.setSelection(0);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2) {
                    appliance.setProgress(Progress.COMPLETED);
                } else if (i == 1) {
                    appliance.setProgress(Progress.IN_PROGRESS);
                } else {
                    appliance.setProgress(Progress.NOT_STARTED);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Apply Button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.APPLIANCE, appliance);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        
		//the name of appliance
		if (appliance.getName() != null){
			applianceName.setText(appliance.getName());
		}
        applianceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String appName = applianceName.getText().toString();
                appliance.setName(appName);
            }
        });
		if (appliance.getLocation() != null || !appliance.getLocation().equals("")) {
            applianceLocation.setText(appliance.getLocation());
        }
        applianceLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String appLoc = applianceLocation.getText().toString();
                appliance.setLocation(appLoc);
            }
        });
        if (appliance.getIssues() != null || !appliance.getIssues().equals("")) {
            applianceIssues.setText(appliance.getIssues());
        }
        applianceIssues.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String appIssues = applianceIssues.getText().toString();
                appliance.setIssues(appIssues);
            }
        });

		//putting in the parts estimation
        parts = appliance.getPartsList();
        supplyPartsAdapter = new SupplyPartsAdapter(getApplicationContext(), 0, parts);
        listView = (ListView) findViewById(R.id.parts_list);
        listView.setAdapter(supplyPartsAdapter);

        View footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.add_part_layout, null, false);

        Button addPartButton = (Button) footerView.findViewById(R.id.add_part_button);
        addPartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.add_new_appliance_dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(ApplianceDetailActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = ApplianceDetailActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                View popup = inflater.inflate(R.layout.add_new_part_dialog, null);
                builder.setView(popup);

                final EditText editPartNum = (EditText) popup.findViewById(R.id.count_needed);
                final EditText editPartName = (EditText) popup.findViewById(R.id.edit_part_name);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setContentView(R.layout.add_new_part_dialog);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String partName = editPartName.getText().toString();
                        Editable editable = editPartNum.getText();
                        int partNum = 0;
                        String appNumStr = "";
                        if (editable != null) {
                            appNumStr = editable.toString();
                        }
                        if (!appNumStr.equals("")) {
                            partNum = Integer.parseInt(appNumStr);
                        }

                        SupplyPart newPart = new SupplyPart(partNum, partName);
                        parts.add(newPart);
                        appliance.setPartsList(parts);
                        supplyPartsAdapter.notifyDataSetChanged();
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

        listView.addFooterView(footerView);

		final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SUPANDAPPREFS, Context.MODE_PRIVATE);
        boolean seenAppDetail = sharedPreferences.getBoolean(Constants.SEENAPPDETAIL, false);
        if (!seenAppDetail) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Here you can customize the type, status, and parts of this appliance. " +
            		"You can also leave a notes to describe any issues about this appliance.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sharedPreferences.edit().putBoolean(Constants.SEENAPPDETAIL, true);
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
