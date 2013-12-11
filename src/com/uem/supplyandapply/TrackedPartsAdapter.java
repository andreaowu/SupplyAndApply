package com.uem.supplyandapply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * User: ItsTexter
 */
public class TrackedPartsAdapter extends ArrayAdapter<TrackedPart> {

    private List<TrackedPart> trackedParts;

    public TrackedPartsAdapter(Context context, int resource, List<TrackedPart> trackedParts) {
        super(context, resource, trackedParts);
        this.trackedParts = trackedParts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final TrackedPart currentPart = trackedParts.get(position);
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.parts_list_row, null, false);
        }

        TextView partsName = (TextView) v.findViewById(R.id.parts_name);
        TextView partsNumber = (TextView) v.findViewById(R.id.parts_number);

        partsName.setText(currentPart.getName());
        partsNumber.setText(String.valueOf(currentPart.getBrought() + " / " + currentPart.getNeeded()));

        return v;
    }
}