package com.example.drew.wheresmystuff.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by kvict on 6/26/2017.
 */

public class ItemReportManager {
    public static final ItemReportManager myItemReports = new ItemReportManager();

    private final HashMap<Integer, ItemReport> itemReports = new HashMap<>();
    private final ArrayList<ItemReport> itemReportsList = new ArrayList<ItemReport>();

    private final HashMap<Integer, FoundItemReport> foundItemReports = new HashMap<>();
    private final ArrayList<FoundItemReport> foundItemReportsList = new ArrayList<FoundItemReport>();

    /**
     * adds lost report to this itemReportManager
     * @param itemReport the report to be added
     */
    public void addReport(ItemReport itemReport) {
        if (itemReport != null) {
            itemReportsList.add(itemReport);
        }
        itemReports.put(itemReport.getReportID(), itemReport);
    }

    /**
     * adds found report to this itemReportManager
     * @param foundItemReport the report to be added
     */
    public void addFoundReport(FoundItemReport foundItemReport) {
        if (foundItemReport != null) {
            foundItemReportsList.add(foundItemReport);
        }
        foundItemReports.put(foundItemReport.getReportID(), foundItemReport);
    }

    /**
     * gets the report with the given ID
     * @param reportID the id of the report you are trying to get
     * @return the item report with the given ID, or null if no such report exists
     */
    public  ItemReport getReport(int reportID) {
        return itemReports.get(reportID);
    }

    /**
     * Gets all the reports in this ReportManager
     * @return a collection of all the reports
     */
    public Collection<ItemReport> getAllReports() {
        return itemReports.values();
    }

    /**
     * Gets all the reports in this ReportManager but in an ArrayList
     * @return an ArrayList of all the reports
     */
    public ArrayList<ItemReport> getAllItemsList() { return itemReportsList; }

    /**
     * gets the found report with the given ID
     * @param reportID the id of the report you are trying to get
     * @return the item report with the given ID, or null if no such report exists
     */
    public  FoundItemReport getFoundReport(int reportID) {return foundItemReports.get(reportID);
    }

    /**
     * Gets all the found reports in this ReportManager
     * @return a collection of all the reports
     */
    public Collection<FoundItemReport> getAllFoundReports() {
        return foundItemReports.values();
    }

    /**
     * Gets all the found reports in this ReportManager but in an ArrayList
     * @return an ArrayList of all the reports
     */
    public ArrayList<FoundItemReport> getAllFoundItemsList() { return foundItemReportsList; }

}
