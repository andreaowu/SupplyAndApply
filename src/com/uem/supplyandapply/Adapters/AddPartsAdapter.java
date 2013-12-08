package com.uem.supplyandapply.Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.uem.supplyandapply.R;
import com.uem.supplyandapply.SupplyPart;

public class AddPartsAdapter extends ArrayAdapter<SupplyPart> {

	String name;
	String number;
	
    public AddPartsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.add_new_appliance_dialog_row, null, false);
        }

        EditText partName = (EditText) v.findViewById(R.id.add_part_name);
        EditText numberBroken = (EditText) v.findViewById(R.id.add_part_number);

        partName.setText(name);
        numberBroken.setText(number);

        return v;
    }

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
    
	
}


