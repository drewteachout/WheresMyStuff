package com.example.drew.wheresmystuff.model;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;

import java.util.ArrayList;

/**
 * Created by drewt on 6/29/2017.
 */

public class ItemAdapter extends ArrayAdapter<ItemReport> {

    public ItemAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ItemAdapter (ArrayList<ItemReport> data, int resource, Context context) {
        super(context, resource, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;

        if (result == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.row_item, null);
        }

        ItemReport item = getItem(position);

        if (item != null) {
            TextView txtName = (TextView) result.findViewById(R.id.adapterName);
            TextView txtCategory = (TextView) result.findViewById(R.id.adapterType);
            TextView txtLocation = (TextView) result.findViewById(R.id.adapterLocation);
            TextView txtReward = (TextView) result.findViewById(R.id.adapterReward);
            // Set values of fields
            txtName.setText("Name: " + item.getItemName());
            txtCategory.setText("Type: " + item.getCategory());
            txtLocation.setText("Location: " + item.getLongitude() + ", " + item.getLatitude());
            txtReward.setText("Reward: " + item.getReward());
        }

        return result;
    }
}
