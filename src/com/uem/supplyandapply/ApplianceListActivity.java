package com.uem.supplyandapply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;
 
public class ApplianceListActivity extends Activity {
 
    ApplianceListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_list);
        Button addPartButton = (Button) findViewById(R.id.add_part_btn);
        
        //Apply Button
        addPartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddPart.class);
                startActivityForResult(i, 1);
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
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                        .show();
                
                Intent intent = new Intent(getApplicationContext(), ApplianceDetailActivity.class);
                ApplianceStateContainer app_con = (ApplianceStateContainer) getIntent().getSerializableExtra("ApplianceContainer");
            	ArrayList <Appliance> app = app_con.getAppliances();
            	
            	for (Appliance a: app){
                	
                	if (a.getName().equals(listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition))){
                		intent.putExtra(Constants.APPLIANCE, ((Appliance)a));
                	}
                }
                startActivityForResult(intent, 1);
                return false;
            }
        });
        
       
        
    }
    

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
    	final ArrayList<ApplianceStateContainer> app_con_list;
    	
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
        
        
        ApplianceStateContainer app_con = (ApplianceStateContainer) getIntent().getSerializableExtra("ApplianceContainer");
    	ArrayList <Appliance> app = app_con.getAppliances();
    	
    	for (Appliance a : app){
    		if (a.getProgress().equals("NOT_STARTED")){
    			notStarted.add(a.getName());
    		}
    		if (a.getProgress().equals("IN_PROGRESS")){
    			inProgress.add(a.getName());
    		}
    		else{
    			finished.add(a.getName());
    		}
    	}
      
        

        listDataChild.put(listDataHeader.get(0), notStarted); 
        listDataChild.put(listDataHeader.get(1), inProgress);
        listDataChild.put(listDataHeader.get(2), finished);
    }
    
     
}