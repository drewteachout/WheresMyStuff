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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("WeakerAccess") //Weaker access is correct, but we need to make this public for the JUnit tests.
public class GoogleMapViewHandler implements OnMapReadyCallback {

    private GoogleMap map = null;
    private Geocoder geocoder;
    ConcurrentHashMap<String, Marker> mapMarkers;

    private final Context parentContext;
    private final GoogleMap.OnCameraMoveStartedListener cml;
    private final GoogleMap.OnMapClickListener mcl;
    private final GoogleMap.OnMarkerClickListener mkcl;

    final ArrayList<Runnable> onMapReadyExecutions = new ArrayList<>();

    /**
     * Builds the google map event handler header from given listenerss.
     * @param parentContext Activity context of the parent for location services
     * @param cml when a camera move start happens
     * @param mcl when the map is clicked
     * @param mkcl when a marker is clicked (overrides map click)
     */
    public GoogleMapViewHandler(Context parentContext,
                                GoogleMap.OnCameraMoveStartedListener cml,
                                GoogleMap.OnMapClickListener mcl,
                                GoogleMap.OnMarkerClickListener mkcl) {
        this.parentContext = parentContext;
        this.cml = cml;
        this.mcl = mcl;
        this.mkcl = mkcl;
        mapMarkers = new ConcurrentHashMap<>();
    }

    /**
     * Called by the google API when the map is finished, as labeled by the callback interface.
     * @param googleMap System google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        if(!onMapReadyExecutions.isEmpty()) {
            for(Runnable aTerribleIdea : onMapReadyExecutions) {
                aTerribleIdea.run();
            }
        }
        this.geocoder = new Geocoder(parentContext);
        //add listeners
        map.setOnCameraMoveStartedListener(cml);
        map.setOnMapClickListener(mcl);
        map.setOnMarkerClickListener(mkcl);
        //default location setter
        //check if we have permission to get location
        if (ActivityCompat.checkSelfPermission(parentContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(parentContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //query system for all past location accessors
            LocationManager lm = (LocationManager) parentContext.getSystemService(Context.LOCATION_SERVICE);
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

    /**
     * Adds a marker to the map, building the args into a MarkerOptions unit.
     * @param id unique string of the marker for removal.
     * @param title
     * @param subtitle
     * @param latLng
     */
    public void addMarker(String id, String title, String subtitle, LatLng latLng){
        //map.addMarker returns the marker, which is placed into the marker map
        Marker temp = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(subtitle)
        );
        mapMarkers.put(id, temp);
        temp.setVisible(true);
    }

    /**
     * Removes a marker from the map by String ID
     * @param id unique string id
     */
    public void removeMarker(String id) {
        Marker marker = mapMarkers.get(id);
        if(marker != null) {
            marker.remove();
        }
    }

    /**
     * Moves the map
     * @param lat
     * @param lng
     */
    public void setViewLocation(double lat, double lng){
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
    }

    /**
     * Queries the reverse geocoder
     * @param latlng
     * @return
     * @throws IOException
     */
    Address getAddressAtLatLng(LatLng latlng) throws IOException {
        List<Address> out = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);
        if(out != null && !out.isEmpty()){
            return out.get(0);
        }
        return null;
    }

    /**
     * returns the raw map instance, use sparingly
     * @return
     */
    public GoogleMap getRawMap() {
        return map;
    }

    /**
     * returns the raw map marker collection, use sparingly
     * @return
     */
    public ConcurrentHashMap<String,Marker> getRawMapMarkers(){
        return mapMarkers;
    }

    public GoogleMap.OnCameraMoveStartedListener getCml() {
        return cml;
    }

    public GoogleMap.OnMapClickListener getMcl() {
        return mcl;
    }

    public GoogleMap.OnMarkerClickListener getMkcl() {
        return mkcl;
    }
}