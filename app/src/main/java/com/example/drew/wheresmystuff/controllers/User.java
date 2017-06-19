package com.example.drew.wheresmystuff.controllers;

/**
 * Created by kvict on 6/19/2017.
 */

public class User {

    private String name;
    private String email;
    private String password;
    private boolean accountLocked;
    private static User currentUser;



    //public User() {
      //  super();
    //}

    public User(String name, String email, String password, boolean accountLocked) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountLocked = accountLocked;
    }

    /**
     *
     * @return the User's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return the User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param name the User's name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     *
     * @param email the User's new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param password the User's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param locked the User's new account status
     */
    public void setAccountStatus(boolean locked) {
        accountLocked = locked;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public String toString() {
        return "Name: " + name + " " + "Email: " + email;
    }



}
