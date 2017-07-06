package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;

import java.util.Collection;

public class ViewFoundItemsActivity extends AppCompatActivity {

    private Button mSearchButton;
    private Button mAddButton;
    private EditText mItemName;
    private TextView mNoResultsText;
    private AlertDialog.Builder mSearchBuilder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_found_items);
        mSearchButton = (Button) findViewById(R.id.searchButton);
        mAddButton = (Button) findViewById(R.id.addButton);
        mItemName = (EditText) findViewById(R.id.itemSearchBox);
        mNoResultsText = (TextView) findViewById(R.id.noResultsText);

        mNoResultsText.setVisibility(View.INVISIBLE);

        final TextView report_list = (TextView) findViewById(R.id.viewFoundItems);
        final TextView mResultsList = (TextView) findViewById(R.id.viewFoundItems);

        mResultsList.setVisibility(View.INVISIBLE);
        /**
         * Dsiplays all submitted item reports
         */

        final Collection<ItemReport> reports = ItemReportManager.myItemReports.getAllReports();
        for(ItemReport report : reports) {
            report_list.append(report.toString());
            report_list.append("\n");
            report_list.append("\n");

        }

        // Sets click action for add button
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoundItem = new Intent(getApplicationContext(), FoundItemsActivity.class);
                startActivity(addFoundItem);
            }
        });

        // Sets click action for search button
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ItemReport report : reports) {
                    if (mItemName.getText().toString().equals(report.getItemName())) {
                        mResultsList.append(report.toString());
                        mResultsList.append("\n");
                        mResultsList.append("\n");
                    }
                }
                if (!mResultsList.getText().toString().equals("")) {
                    report_list.setVisibility(View.INVISIBLE);
                    mResultsList.setVisibility(View.VISIBLE);

                } else {
                    report_list.setVisibility(View.INVISIBLE);
                    mNoResultsText.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void doNothing() {
        // Insert code here
    }
}
