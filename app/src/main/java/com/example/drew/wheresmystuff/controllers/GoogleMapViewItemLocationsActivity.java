package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

public class GoogleMapViewItemLocationsActivity extends AppCompatActivity{

    private SupportMapFragment mapFragment;
    private GoogleMapViewHandler mapViewHandler;

    private ArrayList<ItemReport> reports;
    private HashMap<String, ItemReport> reportMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        Bundle args = getIntent().getExtras();
        reports = (ArrayList<ItemReport>) args.getSerializable("reports");
        mapViewHandler = new GoogleMapViewHandler(getApplicationContext(),
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
                        for(Marker aMarker : mapViewHandler.mapMarkers.values()) {
                            aMarker.hideInfoWindow();
                        }
                        marker.showInfoWindow();
                        return true;
                    }
                }
        );
        mapViewHandler.onMapReadyExecutions.add(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < reports.size(); i++) {
                    ItemReport aReport = reports.get(i);
                    if(aReport != null) {
                        mapViewHandler.addMarker("rep" + i, aReport.getItemName(), aReport.getCategory(), new LatLng(aReport.getLatitude(), aReport.getLongitude()));
                        reportMap.put("rep" + i,aReport);
                    }
                }
            }
        });
        /*
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mapViewFragment = (GoogleMapViewFragment) fragmentManager.findFragmentById(R.id.viewReportFragment);

        GoogleMapViewFragment fragment = new GoogleMapViewFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.view_reports_mapfragment_container, fragment)
                .addToBackStack(null)
                .commit();
        */
        setContentView(R.layout.activity_googlemap_view_reports);
        mapFragment = (SupportMapFragment) SupportMapFragment.newInstance();
        // Then we add it using a FragmentTransaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view_reports_mapfragment_container, mapFragment, "mapViewReports");
        fragmentTransaction.commit();
        mapFragment.getMapAsync(mapViewHandler);
    }
}
