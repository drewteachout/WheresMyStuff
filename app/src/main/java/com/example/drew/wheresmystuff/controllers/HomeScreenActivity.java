package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;

import static com.example.drew.wheresmystuff.controllers.User.getCurrentUser;

public class HomeScreenActivity extends AppCompatActivity {
    TextView currentUser;
    private Button newLostItem;
    private Button viewItemReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        currentUser = (TextView)findViewById(R.id.currentUser);
        currentUser.setText("WELCOME " + getCurrentUser().getName());

        newLostItem = (Button) findViewById(R.id.submitLostItemReport);
        newLostItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LostItemReportActivity.class);
                startActivity(i);
            }
        });
        viewItemReports = (Button) findViewById(R.id.viewItemReportsButton);
        viewItemReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), viewItemReports.class);
                startActivity(i);

            }
        });
    }

    protected void onLogoutPressed(View view) {
        Intent logout = new Intent(this, WelcomeActivity.class);
        User.setCurrentUser(null);
        startActivity(logout);
    }




}
