package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.text.TextUtils;
import android.widget.ListView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemAdapter;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;

import java.util.ArrayList;

public class LostItemsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static ItemAdapter mAdapter;
    private ListView mListView;
    private SearchView mSearchView;
    private FloatingActionButton mNewLostItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_items);

        mListView = (ListView) findViewById(R.id.itemsList);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mNewLostItemButton = (FloatingActionButton) findViewById(R.id.newLostItemButton);

        ArrayList<ItemReport> reports = ItemReportManager.myItemReports.getAllItemsList();

        mAdapter = new ItemAdapter(reports, R.layout.row_item, getApplicationContext());
        mListView.setAdapter(mAdapter);

        mListView.setTextFilterEnabled(true);
        mNewLostItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), ReportLostItemActivity.class);
                startActivity(add);
            }
        });


        setupSearchView();
    }

    private void onAddPressed(View view) {
        startActivity(new Intent(this, ReportLostItemActivity.class));
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        //mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search By Name Here");
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
           mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
