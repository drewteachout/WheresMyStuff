package com.example.drew.wheresmystuff;

import com.example.drew.wheresmystuff.controllers.GoogleMapViewHandler;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.User;
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

    /**
     * Drew Teachout - ateachout3
     * JUnit to check that model classes adhere to the model view controller
     */
    @Test
    public void test_model_mvc_adherence() {
        // List of users
        User[] userList = new User[100];
        // Creates new users
        for (int i = 0; i < 100; i++) {
            userList[i] = new User("User" + (i + 1), "email" + i + "@test.com", "password", false);
        }
        // Seems redundant but testing that model classes are entirely dependent from the controller
        for (int i = 0; i < 100; i++) {
            // Test email works
            assertEquals(userList[i].getName(), "User" + (i + 1));
            // Test email works
            assertEquals(userList[i].getEmail(), "email" + i + "@test.com");
            // Test password works
            assertEquals(userList[i].getPassword(), "password");
        }

        // List of itemReports
        ItemReport[] itemReports = new ItemReport[100];

        // Creates list of item reports
        for (int i = 0; i < 100; i++) {
            itemReports[i] = new ItemReport("item" + (i + 1), "description" + i, i, 100 - i, "Keepsake", "An A on this project");
        }
        // Seems redundant but testing that model classes are entirely dependent from the controller
        for (int i = 0; i < 100; i++) {
            // Test itemName works
            assertEquals(itemReports[i].getItemName(), "item" + (i + 1));
            // Test description works
            assertEquals(itemReports[i].getItemDescription(), "description" + i);
            // Test latitude works
            assertEquals(itemReports[i].getLatitude(), (double) i, 0);
            // Test longitude works
            assertEquals(itemReports[i].getLongitude(), (double) (100 - i), 0);
            // Test description
            assertEquals(itemReports[i].getReward(), "An A on this project");
        }
        // all tests passed
    }
}