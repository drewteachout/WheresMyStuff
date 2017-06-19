package com.example.drew.wheresmystuff.controllers;

/**
 * Created by kvict on 6/19/2017.
 */

public class Admin extends User {

    public Admin(String name, String email, String password, boolean accountLocked){
        super(name, email, password, accountLocked);
    }

public void addNewUser(User user, boolean accountLocked) {

}

public void deleteUser(User user) {

}
//Do i even need this??
public void changeAccountStatus(User user, boolean accountLocked) {

}

}
