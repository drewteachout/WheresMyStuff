package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.drew.wheresmystuff.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;

public class GoogleMapReportItemLocationActivity extends AppCompatActivity{

    GoogleMapViewFragment mapViewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        //android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        //mapViewFragment = (GoogleMapViewFragment) fragmentManager.findFragmentById(R.id.reportItemFragment);
        GoogleMapViewFragment fragment = new GoogleMapViewFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.create_report_mapfragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fragments have already been started
        mapViewFragment.resetListeners(
                new GoogleMap.OnCameraMoveListener(){
                    @Override
                    public void onCameraMove() {
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
                            address = mapViewFragment.getAddressAtLatLng(latLng);
                            if(address != null) {
                                title = address.getAddressLine(0);
                                subtitle = address.getAddressLine(1);
                            }
                        } catch (IOException ignored) {}
                        mapViewFragment.addMarker("click",title,subtitle,latLng);
                    }
                },
                new GoogleMap.OnMarkerClickListener(){
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = getIntent();
                        intent.putExtra("location",marker.getPosition());
                        setResult(RESULT_OK, intent);
                        return true;
                    }
                }
        );
    }
}
