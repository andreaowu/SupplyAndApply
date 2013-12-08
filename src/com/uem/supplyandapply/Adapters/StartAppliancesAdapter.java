package com.uem.supplyandapply.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.uem.supplyandapply.Appliance;
import com.uem.supplyandapply.R;

import java.util.List;

/**
 * User: ItsTexter
 * Date: 12/7/13
 */
public class StartAppliancesAdapter extends ArrayAdapter<Appliance> {

    private List<Appliance> appliances;

    public StartAppliancesAdapter(Context context, int resource, List<Appliance> appliances) {
        super(context, resource, appliances);
        this.appliances = appliances;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final Appliance currentAppliance = appliances.get(position);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.start_activity_row, null, false);
        }

        TextView textView = (TextView) v.findViewById(R.id.appliance_name);
        textView.setText(currentAppliance.getName());

        return v;
    }

    public List<Appliance> getAppliances() {
        return appliances;
    }

    public void setAppliances(List<Appliance> appliances) {
        this.appliances = appliances;
    }
}
