package com.example.drew.wheresmystuff.controllers;

import android.content.ClipData;
import android.content.Intent;
import android.location.Address;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.text.TextUtils;
import android.widget.ListView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.FoundItemReport;
import com.example.drew.wheresmystuff.model.ItemAdapter;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;
import com.example.drew.wheresmystuff.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class LostItemsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static ItemAdapter mAdapter;
    private ListView mListView;
    private SearchView mSearchView;
    private FloatingActionButton mNewLostItemButton;
    private ArrayList<ItemReport> reports = new ArrayList<>();

    private GoogleMapViewFragment mapViewFragment;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_items);

        mDatabase = FirebaseDatabase.getInstance().getReference("lostitems");

        mListView = (ListView) findViewById(R.id.itemsList);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mNewLostItemButton = (FloatingActionButton) findViewById(R.id.newLostItemButton);

        //ArrayList<ItemReport> reports = ItemReportManager.myItemReports.getAllItemsList();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    ItemReport item = new ItemReport(snap.child("itemName").getValue().toString(), snap.child("itemDescription").getValue().toString(),
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

        /*
        Google Map prep and integration.  Unsure about where to hook this
         */
        //just adding in all the reports as markers by hand.  Since we don't need to reference the ID yet, can just use garbage
        for(int i = 0; i < reports.size(); i++) {
            ItemReport aReport = reports.get(i);
            mapViewFragment.addMarker("rep" + i, aReport.getItemName(), aReport.getCategory(), new LatLng(aReport.getLatitude(), aReport.getLongitude()));
        }
        mapViewFragment.addListeners(
                new GoogleMap.OnCameraMoveListener(){
                    @Override
                    public void onCameraMove() {
                        //possibly only add in reports that are for the current locale to the map? or just a few or something, dunno.
                    }
                },
                new GoogleMap.OnMapClickListener(){
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //not really needed for this method
                    }
                },
                new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //display some text showing what marker was clicked?
                        return true;
                    }
                }
        );


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
