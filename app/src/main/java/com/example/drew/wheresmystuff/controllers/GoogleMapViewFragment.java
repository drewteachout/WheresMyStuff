package com.example.drew.wheresmystuff.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GoogleMapViewFragment extends SupportMapFragment implements OnMapReadyCallback {

    public boolean mapReady = false;

    private GoogleMap map;
    private Geocoder geocoder;
    private HashMap<String, Marker> mapMarkers;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mapMarkers = new HashMap<>();
        this.map = googleMap;
        this.geocoder = new Geocoder(getContext());
        //default location setter
        //check if we have permission to get location
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //query system for all past location accessors
            LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getAllProviders();
            //iterate over accessors until we get a location.
            Location loc = null;
            int i = 0;
            while (loc == null && (i < providers.size())) {
                loc = lm.getLastKnownLocation(providers.get(i++));
            }
            if(loc != null) {
                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude())));
            }
        }
    }

    public void addListeners(GoogleMap.OnCameraMoveListener cml, GoogleMap.OnMapClickListener mcl, GoogleMap.OnMarkerClickListener mkcl) {
        map.setOnCameraMoveListener(cml);
        map.setOnMapClickListener(mcl);
        map.setOnMarkerClickListener(mkcl);
    }

    public void addMarker(String id, String title, String subtitle, LatLng latLng){
        //map.addMarker returns the marker, which is placed into the marker map
        mapMarkers.put(id, map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle))
        );
    }

    public void removeMarker(String id) {
        mapMarkers.get(id).remove();
    }

    public void setViewLocation(double lat, double lng){
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
    }

    public Address getAddressAtLatLng(LatLng latlng) throws IOException {
        return geocoder.getFromLocation(latlng.latitude,latlng.longitude,1).get(0);
    }
}