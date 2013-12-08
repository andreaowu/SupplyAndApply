package com.uem.supplyandapply.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.uem.supplyandapply.R;
import com.uem.supplyandapply.SupplyPart;

import java.util.List;

/**
 * User: ItsTexter
 */
public class SupplyPartsAdapter extends ArrayAdapter<SupplyPart> {

    private List<SupplyPart> supplyParts;

    public SupplyPartsAdapter(Context context, int resource, List<SupplyPart> supplyParts) {
        super(context, resource, supplyParts);
        this.supplyParts = supplyParts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final SupplyPart currentContainer = supplyParts.get(position);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.parts_list_row, null, false);
        }

        TextView partsName = (TextView) v.findViewById(R.id.parts_name);
        TextView partsNumber = (TextView) v.findViewById(R.id.parts_number);

        partsName.setText(currentContainer.getName());
        partsNumber.setText(String.valueOf(currentContainer.getCount()));

        return v;
    }
}
