package com.uem.supplyandapply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.uem.supplyandapply.Adapters.StartAppliancesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: ItsTexter
 * Date: 12/7/13
 */
public class StartJobActivity extends Activity {

    private ListView listView;
    private Job job;
    private ArrayList<ApplianceStateContainer> applianceStateContainers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_job_layout);

        job = (Job) getIntent().getSerializableExtra(Constants.JOB);
        HashMap<String, ApplianceStateContainer> map = job.getBroken();
        ArrayList<Appliance> appliances = new ArrayList<Appliance>();
        for (ApplianceStateContainer applianceStateContainer : map.values()) {
            appliances.addAll(applianceStateContainer.getAppliances());
        }

        listView = (ListView) findViewById(R.id.appliance_list);

        listView.setAdapter(new StartAppliancesAdapter(getApplicationContext(), 0, appliances));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Appliance appliance = (Appliance) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ApplianceDetailActivity.class);
                intent.putExtra(Constants.APPLIANCE, appliance);

                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Appliance appliance = (Appliance) data.getExtras().get(Constants.APPLIANCE);
                StartAppliancesAdapter appliancesAdapter = (StartAppliancesAdapter) listView.getAdapter();
                List<Appliance> appliances = appliancesAdapter.getAppliances();
                appliances.remove(appliance);
                appliancesAdapter.notifyDataSetChanged();
            }
        }
    }
}
