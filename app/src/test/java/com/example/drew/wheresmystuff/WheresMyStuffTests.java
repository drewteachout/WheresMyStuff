package com.example.drew.wheresmystuff;

import com.example.drew.wheresmystuff.controllers.GoogleMapViewHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WheresMyStuffTests {

    /**
     * Martin Prammer - mprammer3
     * JUnit test to check GoogleMap handler's marker storage and listener triggers.
     */
    @Test
    public void test_GoogleMap_handler() {
        //final boolean array so we can pass values in and out of listeners
        final boolean[] listenerTriggerOutputs = new boolean[3];
        listenerTriggerOutputs[0] = false;
        listenerTriggerOutputs[1] = false;
        listenerTriggerOutputs[2] = false;
        //map handler responds to callbacks, passing instantiation with this means that we aren't interfering with intent prep.
        GoogleMapViewHandler handler = new GoogleMapViewHandler(null
            , new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int i) {
                    listenerTriggerOutputs[0] = !listenerTriggerOutputs[0];
                }
            }, new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    listenerTriggerOutputs[1] = !listenerTriggerOutputs[1];
                }
            }, new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    listenerTriggerOutputs[2] = !listenerTriggerOutputs[2];
                    return true;
                }
            }
        );
        ConcurrentHashMap<String,Marker> markers = handler.getRawMapMarkers();
        //no stray markers on init
        assertNotNull(markers);
        assertEquals(0, markers.size());
        //test we don't error out with this
        handler.removeMarker("nonexistant marker");
        assertEquals(0, handler.getRawMapMarkers().size());
        //can't instantiate bunk markers since googlemap  API classes are locked.
        //test listener triggers by firing fake events
        handler.getCml().onCameraMoveStarted(0);
        handler.getMcl().onMapClick(null);
        handler.getMkcl().onMarkerClick(null);
        assertArrayEquals(new boolean[]{true, true, true}, listenerTriggerOutputs);
        //all tests passed
    }
}