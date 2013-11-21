package com.uem.supplyandapply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.uem.supplyandapply.Adapters.ApplianceAdapter;

import java.util.ArrayList;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 3:45 PM
 */
public class AddJobActivity extends Activity {

    private ApplianceAdapter adapter;
    private ListView listView;
    private ArrayList<ApplianceStateContainer> applianceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_appliances_layout);
        applianceList = getDefaultApplianceList();
        adapter = new ApplianceAdapter(getApplicationContext(), 0, applianceList);

        listView = (ListView) findViewById(R.id.application_list);
        listView.setAdapter(adapter);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PartsEstimationActivity.class);
                i.putExtra(Constants.APPLIANCE_LIST, applianceList);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
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
