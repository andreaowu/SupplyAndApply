package com.uem.supplyandapply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.uem.supplyandapply.Adapters.SupplyPartsAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: ItsTexter
 */
public class PartsEstimationActivity extends Activity {

    private SupplyPartsAdapter adapter;
    private ListView listView;
    private String address;
    private String name;
    private ArrayList<ApplianceStateContainer> applianceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_estimation_layout);

        Intent i = getIntent();
        applianceList = (ArrayList<ApplianceStateContainer>) i.getExtras().get(Constants.APPLIANCE_LIST);
        address = i.getExtras().getString(Constants.ADDRESS);
        name = i.getExtras().getString(Constants.NAME);

        adapter = new SupplyPartsAdapter(getApplicationContext(), 0, getPartsListFromAppliances(applianceList));

        listView = (ListView) findViewById(R.id.application_list);
        listView.setAdapter(adapter);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, ApplianceStateContainer> applianceStateContainerHashMap = new HashMap<String, ApplianceStateContainer>();
                for (ApplianceStateContainer applianceStateContainer : applianceList) {
                    applianceStateContainerHashMap.put(applianceStateContainer.getAppliance().getName(), applianceStateContainer);
                }

                Job newJob = new Job(new Customer(name, address), applianceStateContainerHashMap);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.NEW_JOB, newJob);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private ArrayList<SupplyPart> getPartsListFromAppliances(ArrayList<ApplianceStateContainer> applianceList) {
        ArrayList<SupplyPart> supplyPartsList = new ArrayList<SupplyPart>();
        for (ApplianceStateContainer stateContainer : applianceList) {
            supplyPartsList.addAll(stateContainer.getInitialPartsList());
        }
        return supplyPartsList;
    }

}
