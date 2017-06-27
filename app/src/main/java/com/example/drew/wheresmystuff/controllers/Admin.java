package com.example.drew.wheresmystuff.controllers;

/**
 * Created by kvict on 6/19/2017.
 */

public class Admin extends User {

    /**
     * The Admin of the system
     * @param name the admin's name
     * @param email the admin's email
     * @param password the admin's password
     * @param accountLocked the admin's account status
     */
    public Admin(String name, String email, String password, boolean accountLocked){
        super(name, email, password, accountLocked);
    }

    /**
     * Adds new user to the system
     * @param user the user to be added
     *
     */
    public void addNewUser(User user) {

}

    /**
     * Deletes user from the system
     * @param user the user to be deleted
     */
    public void deleteUser(User user) {

}

    /**
     * Changes the user's locked status
     * @param user the user
     * @param accountLocked user's new status
     */
    public void changeAccountStatus(User user, boolean accountLocked) {

}

}
