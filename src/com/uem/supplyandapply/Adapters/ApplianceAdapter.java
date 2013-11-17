package com.uem.supplyandapply.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.uem.supplyandapply.ApplianceStateContainer;
import com.uem.supplyandapply.R;

import java.util.List;

/**
 * User: ItsTexter
 * Date: 11/15/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class ApplianceAdapter extends ArrayAdapter<ApplianceStateContainer> {

    private List<ApplianceStateContainer> applianceStateContainers;

    public ApplianceAdapter(Context context, int resource, List<ApplianceStateContainer> applianceStateContainers) {
        super(context, resource, applianceStateContainers);
        this.applianceStateContainers = applianceStateContainers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ApplianceStateContainer currentContainer = applianceStateContainers.get(position);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.appliance_list_row, null, false);
        }

        ImageView imageView = (ImageView) v.findViewById(R.id.appliance_image);
        imageView.setImageResource(currentContainer.getAppliance().getDrawableResource());

        final EditText countText = (EditText) v.findViewById(R.id.appliance_count);
        countText.setText(currentContainer.getCount());
        countText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                currentContainer.setCount(Integer.parseInt(editable.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
        });


        TextView increment = (TextView) v.findViewById(R.id.increment_button);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = currentContainer.getCount() + 1;
                currentContainer.setCount(newCount);
                countText.setText(newCount);
            }
        });

        TextView decrement = (TextView) v.findViewById(R.id.decrement_button);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCount = currentContainer.getCount() - 1;
                currentContainer.setCount(newCount);
                countText.setText(newCount);
            }
        });

        return v;
    }
}
