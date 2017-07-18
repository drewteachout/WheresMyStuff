package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.drew.wheresmystuff.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;

public class GoogleMapReportItemLocationActivity extends AppCompatActivity{

    private GoogleMapViewHandler mapViewHandler;
    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        mapViewHandler = new GoogleMapViewHandler(getApplicationContext(),
                new GoogleMap.OnCameraMoveStartedListener(){
                    @Override
                    public void onCameraMoveStarted(int i) {
                        //lost items aren't reacting to camera being moved, no point interacting with it
                    }
                },
                new GoogleMap.OnMapClickListener(){
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //latlng refers to the lat,lng pair of where the screen was clicked
                        Address address;
                        String subtitle = "Unable to fetch address";
                        String title = latLng.latitude + ", " + latLng.longitude;
                        try {
                            //attempt to reverse geo-code the address
                            address = mapViewHandler.getAddressAtLatLng(latLng);
                            if(address != null) {
                                title = address.getAddressLine(0);
                                subtitle = address.getAddressLine(1);
                            }
                        } catch (IOException ignored) {}
                        mapViewHandler.removeMarker("click");
                        mapViewHandler.addMarker("click",title,subtitle,latLng);
                        mapViewHandler.mapMarkers.get("click").showInfoWindow();
                    }
                },
                new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = getIntent();
                        intent.putExtra("location",marker.getPosition());
                        setResult(RESULT_OK, intent);
                        finish();
                        return true;
                    }
                }
        );
        /*
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mapViewFragment = (GoogleMapViewFragment) fragmentManager.findFragmentById(R.id.reportItemFragment);
        GoogleMapViewFragment fragment = new GoogleMapViewFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.create_report_mapfragment_container, fragment)
                .addToBackStack(null)
                .commit();
        */
        setContentView(R.layout.activity_googlemap_report_item);
        mapFragment = SupportMapFragment.newInstance();
        // Then we add it using a FragmentTransaction.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.create_report_mapfragment_container, mapFragment, "mapCreateReports");
        fragmentTransaction.commit();
        mapFragment.getMapAsync(mapViewHandler);
    }
}
