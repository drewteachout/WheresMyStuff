package com.example.drew.wheresmystuff.controllers;

import java.util.HashMap;

/**
 * Created by kvict on 6/19/2017.
 */

public class UserManager {


    public static final UserManager myUserManager = new UserManager();
    //single UserManager that holds all the users in the system

    public static User currentUser = null;
    //this indicates the User currently logged in to the system
    //if no one is logged in it holds null

    private final HashMap<String, com.example.drew.wheresmystuff.controllers.User> users = new HashMap<>();

    /**
     * Checks to see if the UserManager contains a user with this email
     * @param email the email being checked
     * @return true if it contains this user, false if not
     */
    public boolean containsKey(String email) {
        return users.containsKey(email);
    }

    /**
     * Gets the User in the UserManager with this email
     * @param email the email for the user we are trying to retrieve
     * @return the User with the given email, or null if there is no person with this email
     * in this UserManager
     */
    public com.example.drew.wheresmystuff.controllers.User getUser(String email) {
        return users.get(email);
    }

    /**
     * Puts the given person into this UserManager
     * @param email the User's email
     * @param user the User object being put in
     */
    public void putUser(String email, com.example.drew.wheresmystuff.controllers.User user) {
        users.put(email, user);
    }

    /**
     * Checks to see if the given password is valid for the user with the given email
     * @param email the email of the user trying to log in
     * @param password the password we are checking
     * @return true if the password is correct
     */
    public boolean validatePassword(String email, String password) {
        if (!users.containsKey(email)) {
            return false;
        }
        return users.get(email).getPassword().equals(password);
    }

}
