package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;

import static com.example.drew.wheresmystuff.controllers.User.getCurrentUser;

public class HomeScreenActivity extends AppCompatActivity {
    TextView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        userList = (TextView)findViewById(R.id.listOfUsers);
        userList.setText(getCurrentUser().getName());
    }

    protected void onLogoutPressed(View view) {
        Intent logout = new Intent(this, WelcomeActivity.class);
        User.setCurrentUser(null);
        startActivity(logout);
    }




}
