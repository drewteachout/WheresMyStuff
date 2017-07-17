package com.example.drew.wheresmystuff.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoogleMapViewHandler implements OnMapReadyCallback {

    private GoogleMap map = null;
    private Geocoder geocoder;
    public HashMap<String, Marker> mapMarkers;

    private final Context parentConext;
    private final GoogleMap.OnCameraMoveListener cml;
    private final GoogleMap.OnMapClickListener mcl;
    private final GoogleMap.OnMarkerClickListener mkcl;

    public final ArrayList<Runnable> onMapReadyExecutions = new ArrayList<>();

    public GoogleMapViewHandler(Context parentConext,
                                GoogleMap.OnCameraMoveListener cml,
                                GoogleMap.OnMapClickListener mcl,
                                GoogleMap.OnMarkerClickListener mkcl) {
        this.parentConext = parentConext;
        this.cml = cml;
        this.mcl = mcl;
        this.mkcl = mkcl;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapMarkers = new HashMap<>();
        this.map = googleMap;
        if(!onMapReadyExecutions.isEmpty()) {
            for(Runnable aTerribleIdea : onMapReadyExecutions) {
                aTerribleIdea.run();
            }
        }
        this.geocoder = new Geocoder(parentConext);
        //add listeners
        map.setOnCameraMoveListener(cml);
        map.setOnMapClickListener(mcl);
        map.setOnMarkerClickListener(mkcl);
        //default location setter
        //check if we have permission to get location
        if (ActivityCompat.checkSelfPermission(parentConext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(parentConext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //query system for all past location accessors
            LocationManager lm = (LocationManager) parentConext.getSystemService(Context.LOCATION_SERVICE);
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

    public void addMarker(String id, String title, String subtitle, LatLng latLng){
        //map.addMarker returns the marker, which is placed into the marker map
        mapMarkers.put(id, map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle)));
    }

    public void removeMarker(String id) {
        Marker marker = mapMarkers.get(id);
        if(marker != null) {
            marker.remove();
        }
    }

    public void setViewLocation(double lat, double lng){
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
    }

    public Address getAddressAtLatLng(LatLng latlng) throws IOException {
        List<Address> out = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);
        if(out != null && !out.isEmpty()){
            return out.get(0);
        }
        return null;
    }
}