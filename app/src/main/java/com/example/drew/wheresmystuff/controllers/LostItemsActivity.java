package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemAdapter;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;

import java.util.ArrayList;

public class LostItemsActivity extends AppCompatActivity {

    private static ItemAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_reports);

        mListView = (ListView) findViewById(R.id.itemsList);

        ArrayList<ItemReport> reports = ItemReportManager.myItemReports.getAllItemsList();

        mAdapter = new ItemAdapter(reports, R.layout.row_item, getApplicationContext());

        mListView.setAdapter(mAdapter);
        //TextView report_list = (TextView)findViewById(R.id.viewItemReports);
        /**
         * Displays all submitted item reports
         */

        /*for(ItemReport report : reports) {
            report_list.append(report.toString());
            report_list.append("\n");

        }*/
    }
}
