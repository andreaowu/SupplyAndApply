<<<<<<< HEAD
package com.uem.supplyandapply;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import com.uem.supplyandapply.Adapters.ApplianceAdapter;

import java.util.ArrayList;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 3:45 PM
 */
public class AddJobActivity extends Activity {

    private ApplianceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_appliances_layout);
        adapter = new ApplianceAdapter(getApplicationContext(), 0, getDefaultApplianceList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<ApplianceStateContainer> getDefaultApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
        appliances.add(new ApplianceStateContainer(new Appliance("Shower", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Toilet", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Sink", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Bathtub", R.drawable.ic_launcher), 0));
        return appliances;
    }
}
=======
package com.uem.supplyandapply;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import com.uem.supplyandapply.Adapters.ApplianceAdapter;

import java.util.ArrayList;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 3:45 PM
 */
public class AddJobActivity extends Activity {

    private ApplianceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job_appliances_layout);
        adapter = new ApplianceAdapter(getApplicationContext(), 0, getDefaultApplianceList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<ApplianceStateContainer> getDefaultApplianceList() {
        ArrayList<ApplianceStateContainer> appliances = new ArrayList<ApplianceStateContainer>();
        appliances.add(new ApplianceStateContainer(new Appliance("Shower", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Toilet", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Sink", R.drawable.ic_launcher), 0));
        appliances.add(new ApplianceStateContainer(new Appliance("Bathtub", R.drawable.ic_launcher), 0));
        return appliances;
    }
}
>>>>>>> 89a75485543702736fd99a652fb78dc740df05a8
