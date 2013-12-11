package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
 
public class ApplianceListActivity extends Activity {
 
    private ApplianceListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ApplianceStateContainer applianceContainer;
    private HashMap<String, Appliance> applianceHashMap;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_list);

        applianceContainer = (ApplianceStateContainer) getIntent().getSerializableExtra(Constants.APPCONTAINER);
        applianceHashMap = new HashMap<String, Appliance>();
        ArrayList<Appliance> appliances = applianceContainer.getAppliances();
        for (Appliance appliance : appliances) {
            applianceHashMap.put(appliance.getHiddenId(), appliance);
        }

        Button doneButton = (Button) findViewById(R.id.done_button);
        
        //Done Button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                ArrayList<Appliance> toReturnAppliances = new ArrayList<Appliance>();
                toReturnAppliances.addAll(applianceHashMap.values());
                applianceContainer.setAppliances(toReturnAppliances);
                returnIntent.putExtra(Constants.APPCONTAINER, applianceContainer);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        
        // preparing list data
        prepareListData();
 
        listAdapter = new ApplianceListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        expListView.expandGroup(2);
        
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
               return false;
            }
        });
        
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                
            }
        });
 
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
 
            @Override
            public void onGroupCollapse(int groupPosition) {
 
            }
        });
 
        // Listview on child click listener
        //This is where it starts a new intent
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getApplicationContext(), ApplianceDetailActivity.class);

                ArrayList <Appliance> app = new ArrayList<Appliance>();
                for (Appliance appliance : applianceHashMap.values()) {
                    app.add(appliance);
                }

                String clickedName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                for (Appliance a: app){
                    String appName = a.getName() + ": " + a.getLocation();
                    if (appName.equals(clickedName)){
                        intent.putExtra(Constants.APPLIANCE, (a));
                    }
                }
                startActivityForResult(intent, 1);
                return false;
            }
        });
        
        
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.SUPANDAPPREFS, Context.MODE_PRIVATE);
        boolean seenApplianceList = sharedPreferences.getBoolean(Constants.SEENAPPLIST, false);
        if (!seenApplianceList) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Here are all the appliances of this type. You can customize " +
            		"an individual appliance by clicking on it.")
                    .setCancelable(false)
                    .setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        	SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putBoolean(Constants.SEENAPPLIST, true);
                            editor.commit();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Appliance returnedAppliance = (Appliance) data.getExtras().get(Constants.APPLIANCE);
                String hiddenId = returnedAppliance.getHiddenId();
                Appliance oldAppliance = applianceHashMap.get(hiddenId);
                if (oldAppliance != null) {
                    applianceHashMap.remove(hiddenId);
                    applianceHashMap.put(hiddenId, returnedAppliance);
                }

                prepareListData();
                updateOnClickAndAdapter();
            }
        }
    }

    /*
         * Preparing the list data
         */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Not Started");
        listDataHeader.add("In Progress");
        listDataHeader.add("Finished");
 
        // Adding child data - we need to pull this from the 
        List<String> notStarted = new ArrayList<String>();
        //notStarted.add("3rd Floor Stall 3");

        List<String> inProgress= new ArrayList<String>();
        //inProgress.add("2nd Floor Stalls 1");
 
        List<String> finished = new ArrayList<String>();
        //finished.add("4th Floor Stall 2");

    	ArrayList <Appliance> app = new ArrayList<Appliance>();
        for (Appliance appliance : applianceHashMap.values()) {
            app.add(appliance);
        }

    	for (Appliance a : app){
            String appName = a.getName() + ": " + a.getLocation();
    		if (a.getProgress().equals(Progress.NOT_STARTED)){
    			notStarted.add(appName);
    		}
    		else if (a.getProgress().equals(Progress.IN_PROGRESS)){
    			inProgress.add(appName);
    		}
    		else{
    			finished.add(appName);
    		}
    	}
      
        listDataChild.put(listDataHeader.get(0), notStarted);
        listDataChild.put(listDataHeader.get(1), inProgress);
        listDataChild.put(listDataHeader.get(2), finished);
    }

    private void updateOnClickAndAdapter() {
        listAdapter = new ApplianceListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        expListView.expandGroup(2);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        //This is where it starts a new intent
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(getApplicationContext(), ApplianceDetailActivity.class);

                ArrayList <Appliance> app = new ArrayList<Appliance>();
                for (Appliance appliance : applianceHashMap.values()) {
                    app.add(appliance);
                }

                String clickedName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                for (Appliance a: app){
                    String appName = a.getName() + ": " + a.getLocation();
                    if (appName.equals(clickedName)){
                        intent.putExtra(Constants.APPLIANCE, (a));
                    }
                }
                startActivityForResult(intent, 1);
                return false;
            }
        });
    }


}