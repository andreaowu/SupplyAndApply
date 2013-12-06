package com.uem.supplyandapply.Adapters;

import java.util.List;


import com.uem.supplyandapply.ApplianceStateContainer;
import com.uem.supplyandapply.Job;
import com.uem.supplyandapply.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentJobAdapter extends ArrayAdapter<ApplianceStateContainer> {
	
	private List<ApplianceStateContainer> applianceStateContainers;
	
	public CurrentJobAdapter(Context context, int resource, List<ApplianceStateContainer> applianceStateContainers) {
		super(context, resource, applianceStateContainers);
		this.applianceStateContainers = applianceStateContainers;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
        final ApplianceStateContainer currentContainer = applianceStateContainers.get(position);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.current_job_grid_item, null, false);
        }
        
        ImageButton imageButton = (ImageButton) v.findViewById(R.id.appliance_imageButton);
        imageButton.setImageResource(currentContainer.getAppliance().getDrawableResource());
        /*
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hand over to Hina
                
            }
        });
        */
        TextView ratio = (TextView) v.findViewById(R.id.ratio_textView);
        String ratioText = Integer.toString(currentContainer.getNotFinished());
        ratioText += "/";
        ratioText += Integer.toString(currentContainer.getCount());
        ratio.setText(ratioText);
        
        return v;
	}
}