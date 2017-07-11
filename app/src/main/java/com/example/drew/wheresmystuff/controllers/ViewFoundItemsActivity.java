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
import com.example.drew.wheresmystuff.model.FoundItemAdapter;
import com.example.drew.wheresmystuff.model.FoundItemReport;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFoundItemsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static FoundItemAdapter mAdapter;
    private ListView mListView;
    private SearchView mSearchView;
    private FloatingActionButton mNewFoundItemButton;
    private ArrayList<FoundItemReport> reports = new ArrayList<>();

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_found_items);

        mDatabase = FirebaseDatabase.getInstance().getReference("founditems");

        mListView = (ListView) findViewById(R.id.itemsList);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mNewFoundItemButton = (FloatingActionButton) findViewById(R.id.newFoundItemButton);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    FoundItemReport item = new FoundItemReport(snap.child("itemName").getValue().toString(), snap.child("itemDescription").getValue().toString(),
                            snap.child("latitude").getValue(Double.class), snap.child("longitude").getValue(Double.class), snap.child("category").getValue().toString(),
                            snap.child("reward").getValue().toString());
                    reports.add(item);
                    //Log.d("tag" ,dataSnapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new FoundItemAdapter(reports, R.layout.row_item, getApplicationContext());
        mListView.setAdapter(mAdapter);

        mListView.setTextFilterEnabled(true);
        mNewFoundItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), FoundItemsActivity.class);
                startActivity(add);
            }
        });


        setupSearchView();
    }

    private void onAddPressed(View view) {
        startActivity(new Intent(this, ViewFoundItemsActivity.class));
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
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
