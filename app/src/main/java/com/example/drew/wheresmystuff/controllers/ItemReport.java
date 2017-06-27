package com.example.drew.wheresmystuff.controllers;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ULocale;

import java.util.Date;

/**
 * Created by kvict on 6/22/2017.
 */

public class ItemReport {
    private User reporter;
    private String itemName;
    private String itemDescription;
    private double latitude;
    private double longitude;
    private boolean itemFound;
    private String category;
    private String reward;
    private boolean lost;
    private String date;
    private int reportID;

    /**
     *
     * @param itemName the item's name
     * @param itemDescription the item's description
     * @param latitude the item's latitude
     * @param longitude the item's longitude
     * @param category the item's category
     * @param reward the reward offere for the item
     */
    public ItemReport(String itemName, String itemDescription, double latitude, double longitude,
                      String category, String reward) {
        this.itemName = itemName;
        this.reporter = User.getCurrentUser();
        this.itemDescription = itemDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.itemFound = false;
        this.category = category;
        this.reward = reward;
        this.lost = true;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        this.date = df.format(c.getTime());
        reportID = itemName.hashCode() + itemDescription.hashCode() + category.hashCode() + reward.hashCode();

    }

    /**
     *
     * @return the item report's reporter
     */
    public User getReporter() {return reporter;}

    /**
     *
     * @return the item's name
     */
    public String getItemName() {return itemName;}

    /**
     *
     * @return the item's description
     */
    public String getItemDescription() {return itemDescription;}

    /**
     *
     * @return the item's latitude
     */
    public double getLatitude() {return latitude;}

    /**
     *
     * @return the item's longitude
     */
    public double getLongitude() {return longitude;}

    /**
     *
     * @return whether or not the item was found
     */
    public boolean getItemStatus() {return itemFound;}

    /**
     *
     * @return the item's category
     */
    public String getCategory() {return category;}

    /**
     *
     * @return the report's date of submission
     */
    public String getDate() {return date;}

    /**
     *
     * @return the report's id
     */
    public int getReportID() {return reportID;}

    @Override
    public String toString() {
        String latEnding = (latitude > 0) ? "° North, " : "° South, ";
        String longEnding = (longitude > 0) ? "° East" : "° West";

        return category + ": " + itemName
                + "\nLocation: " + Double.toString(latitude) + latEnding + Double.toString(longitude)
                + longEnding
                + "\nDescription: " + itemDescription
                + "\nSubmitted by " + reporter.getEmail();
    }
}
