package com.example.drew.wheresmystuff.model;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by drewt on 6/29/2017.
 */

public class ItemAdapter extends ArrayAdapter<ItemReport> implements Filterable {

    /*public ItemAdapter (Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }*/
    public Context mContext;
    public ArrayList<ItemReport> mData;
    public ArrayList<ItemReport> mOrig;

    public ItemAdapter (ArrayList<ItemReport> data, int resource, Context context) {
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
                final ArrayList<ItemReport> results = new ArrayList<>();
                if (mOrig == null) {
                    mOrig = mData;
                }
                if (constraint != null) {
                    if (mOrig != null && mOrig.size() > 0) {
                        for (final ItemReport item : mOrig) {
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
                mData = (ArrayList<ItemReport>) results.values;
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
    public ItemReport getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemReport item = mData.get(position);
        ItemHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
            holder = new ItemHolder();
            holder.name = (TextView) convertView.findViewById(R.id.adapterName);
            holder.type = (TextView) convertView.findViewById(R.id.adapterType);
            holder.location = (TextView) convertView.findViewById(R.id.adapterLocation);
            holder.reward = (TextView) convertView.findViewById(R.id.adapterReward);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }

        holder.name.setText("Name: " + item.getItemName());
        holder.type.setText("Type: " + item.getCategory());
        holder.location.setText("Location: " + item.getLongitude() + ", " + item.getLatitude());
        holder.reward.setText("Reward: " + item.getReward());

        return convertView;
    }
    /*public View getView(int position, View convertView, ViewGroup parent) {
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
    }*/
}
