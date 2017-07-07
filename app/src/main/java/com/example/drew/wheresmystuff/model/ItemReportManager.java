package com.example.drew.wheresmystuff.model;

import android.content.ClipData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by kvict on 6/26/2017.
 */

public class ItemReportManager {
    public static final ItemReportManager myItemReports = new ItemReportManager();

    private final HashMap<Integer, ItemReport> itemReports = new HashMap<>();
    public static ItemReport itemReport;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    /**
     * adds the report to this itemReportManager
     * @param itemReport the report to be added
     */
    public void addReport(ItemReport itemReport) {
        mDatabase.child("items").child(itemReport.getItemName()).setValue(itemReport);
    }

    /**
     * gets the report with the given ID
     * @param reportID the id of the report you are trying to get
     * @return the item report with the given ID, or null if no such report exists
     */
    public ItemReport getReport(String reportID) {
        return itemReports.get(reportID);
    }

    /**
     * Gets all the reports in this ReportManager
     * @return a collection of all the reports
     */
    public Collection<ItemReport> getAllReports() {
        return null;
    }
}
