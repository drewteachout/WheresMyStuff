package com.example.drew.wheresmystuff.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;

import java.util.ArrayList;

public class FoundItemAdapter extends ArrayAdapter<FoundItemReport> implements Filterable {

    public Context mContext;
    public ArrayList<FoundItemReport> mData;
    public ArrayList<FoundItemReport> mOrig;

    public FoundItemAdapter (ArrayList<FoundItemReport> data, int resource, Context context) {
        super(context, resource, data);
        this.mContext = context;
        this.mData = data;
    }

    public class ItemHolder {
        TextView name;
        TextView type;
        TextView location;
        TextView reward;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<FoundItemReport> results = new ArrayList<>();
                if (mOrig == null) {
                    mOrig = mData;
                }
                if (constraint != null) {
                    if (mOrig != null && mOrig.size() > 0) {
                        for (final FoundItemReport item : mOrig) {
                            if (item.getItemName().toLowerCase().contains(constraint.toString())) {
                                results.add(item);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (ArrayList<FoundItemReport>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public FoundItemReport getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoundItemReport item = mData.get(position);
        FoundItemAdapter.ItemHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
            holder = new FoundItemAdapter.ItemHolder();
            holder.name = (TextView) convertView.findViewById(R.id.adapterName);
            holder.type = (TextView) convertView.findViewById(R.id.adapterType);
            holder.location = (TextView) convertView.findViewById(R.id.adapterLocation);
            holder.reward = (TextView) convertView.findViewById(R.id.adapterReward);
            convertView.setTag(holder);
        } else {
            holder = (FoundItemAdapter.ItemHolder) convertView.getTag();
        }

        holder.name.setText("Name: " + item.getItemName());
        holder.type.setText("Type: " + item.getCategory());
        holder.location.setText("Location: " + item.getLongitude() + ", " + item.getLatitude());
        holder.reward.setText("Reward: " + item.getReward());

        return convertView;
    }
}
