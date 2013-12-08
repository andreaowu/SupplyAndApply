package com.uem.supplyandapply.Adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uem.supplyandapply.ApplianceStateContainer;
import com.uem.supplyandapply.R;

public class CurrentJobAdapter extends ArrayAdapter<ApplianceStateContainer> {
	
	private List<ApplianceStateContainer> applianceStateContainers;
	private Context context;
	private Intent intent;
	
	public CurrentJobAdapter(Context context, int resource, List<ApplianceStateContainer> applianceStateContainers, Intent intent) {
		super(context, resource, applianceStateContainers);
		this.applianceStateContainers = applianceStateContainers;
		this.context = context;
		
		this.intent = intent;
		this.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        TextView textView = (TextView) v.findViewById(R.id.appliance_name);
        textView.setText(currentContainer.getAppliance().getName());
        
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	intent.putExtra("ApplianceContainer", currentContainer);
                context.startActivity(intent);
            }
        });
        
        TextView ratio = (TextView) v.findViewById(R.id.ratio_textView);
        String ratioText = Integer.toString(currentContainer.getCount() - currentContainer.getNotFinished());
        ratioText += "/";
        ratioText += Integer.toString(currentContainer.getCount());
        ratio.setText(ratioText);
        
        return v;
	}
}