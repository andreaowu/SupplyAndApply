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
    private ArrayList<ApplianceStateContainer> applianceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parts_estimation_layout);

        Intent i = getIntent();
        applianceList = (ArrayList<ApplianceStateContainer>) i.getExtras().get(Constants.APPLIANCE_LIST);
        address = i.getExtras().getString(Constants.ADDRESS);

        adapter = new SupplyPartsAdapter(getApplicationContext(), 0, getPartsListFromAppliances(applianceList));

        listView = (ListView) findViewById(R.id.application_list);
        listView.setAdapter(adapter);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, ApplianceStateContainer> applianceStateContainerHashMap
                        = new HashMap<String, ApplianceStateContainer>();
                for (ApplianceStateContainer applianceStateContainer : applianceList) {
                    applianceStateContainerHashMap.put(
                            applianceStateContainer.getAppliance().getName(), applianceStateContainer);
                }

                HashMap<String, Integer> integerHashMap = new HashMap<String, Integer>();

                Job newJob = new Job(new Customer("Name", address), applianceStateContainerHashMap, integerHashMap);

                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.JOB, newJob);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private ArrayList<SupplyPart> getPartsListFromAppliances(ArrayList<ApplianceStateContainer> applianceList) {
        ArrayList<SupplyPart> supplyPartsList = new ArrayList<SupplyPart>();
        for (ApplianceStateContainer stateContainer : applianceList) {
            supplyPartsList.addAll(stateContainer.getPartsList());
        }

        return supplyPartsList;
    }

}
