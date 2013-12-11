package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.EditText;
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
	private TrackedPartsAdapter adapter;
	private ListView listView;
    private Job job;
	private ArrayList<TrackedPart> trackedParts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_parts_layout);
		
		job = (Job) getIntent().getSerializableExtra(Constants.JOB);

        HashMap<String, SupplyPart> broughtMap = job.getSupplyPartsBrought();
        HashMap<String, SupplyPart> neededMap = job.getSupplyPartsNeeded();

        HashMap<String, TrackedPart> trackedPartHashMap = new HashMap<String, TrackedPart>();

        for (SupplyPart broughtPart : broughtMap.values()) {
            String name = broughtPart.getName();
            SupplyPart neededPart = neededMap.get(name);
            if (neededPart == null) {
                trackedPartHashMap.put(name, new TrackedPart(name, broughtPart.getCount(), 0));
            } else {
                trackedPartHashMap.put(name, new TrackedPart(name, broughtPart.getCount(), neededPart.getCount()));
            }
        }
        for (SupplyPart neededPart : neededMap.values()) {
            String name = neededPart.getName();
            TrackedPart trackedPart = trackedPartHashMap.get(name);
            if (trackedPart == null) {
                trackedPartHashMap.put(name, new TrackedPart(name, 0, neededPart.getCount()));
            }
        }

        trackedParts = new ArrayList<TrackedPart>();
        trackedParts.addAll(trackedPartHashMap.values());

        adapter = new TrackedPartsAdapter(getApplicationContext(), 0, trackedParts);

        listView = (ListView) findViewById(R.id.parts_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPartsActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = ViewPartsActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                View popup = inflater.inflate(R.layout.add_new_tracked_part, null);
                builder.setView(popup);

                final EditText editBrought = (EditText) popup.findViewById(R.id.count_brought);
                final EditText editNeeded = (EditText) popup.findViewById(R.id.count_needed);
                final EditText editPartName = (EditText) popup.findViewById(R.id.edit_part_name);

                final TrackedPart trackedPart = trackedParts.get(i);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setContentView(R.layout.add_new_tracked_part);

                editBrought.setText(String.valueOf(trackedPart.getBrought()));
                editNeeded.setText(String.valueOf(trackedPart.getNeeded()));
                editPartName.setText(trackedPart.getName());

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String partName = editPartName.getText().toString();
                        Editable editableNeeded = editNeeded.getText();
                        int numNeeded = 0;
                        String appNumStr = "";
                        if (editableNeeded != null) {
                            appNumStr = editableNeeded.toString();
                        }
                        if (!appNumStr.equals("")) {
                            numNeeded = Integer.parseInt(appNumStr);
                        }
                        Editable editableBrought = editBrought.getText();
                        int numBrought = 0;
                        appNumStr = "";
                        if (editableBrought != null) {
                            appNumStr = editableBrought.toString();
                        }
                        if (!appNumStr.equals("")) {
                            numBrought = Integer.parseInt(appNumStr);
                        }

                        trackedPart.setName(partName);
                        trackedPart.setBrought(numBrought);
                        trackedPart.setNeeded(numNeeded);

                        adapter.notifyDataSetChanged();
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

        View footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.add_part_layout, null, false);

        Button addPartButton = (Button) footerView.findViewById(R.id.add_part_button);

        addPartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPartsActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = ViewPartsActivity.this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout

                View popup = inflater.inflate(R.layout.add_new_tracked_part, null);
                builder.setView(popup);

                final EditText editBrought = (EditText) popup.findViewById(R.id.count_brought);
                final EditText editNeeded = (EditText) popup.findViewById(R.id.count_needed);
                final EditText editPartName = (EditText) popup.findViewById(R.id.edit_part_name);

                final AlertDialog alertDialog = builder.create();
                alertDialog.setContentView(R.layout.add_new_tracked_part);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String partName = editPartName.getText().toString();
                        Editable editableNeeded = editNeeded.getText();
                        int numNeeded = 0;
                        String appNumStr = "";
                        if (editableNeeded != null) {
                            appNumStr = editableNeeded.toString();
                        }
                        if (!appNumStr.equals("")) {
                            numNeeded = Integer.parseInt(appNumStr);
                        }
                        Editable editableBrought = editBrought.getText();
                        int numBrought = 0;
                        appNumStr = "";
                        if (editableBrought != null) {
                            appNumStr = editableBrought.toString();
                        }
                        if (!appNumStr.equals("")) {
                            numBrought = Integer.parseInt(appNumStr);
                        }

                        TrackedPart trackedPart = new TrackedPart(partName, numBrought, numNeeded);

                        trackedParts.add(trackedPart);
                        adapter.notifyDataSetChanged();
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