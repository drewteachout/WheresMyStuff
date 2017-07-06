package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.User;

import static com.example.drew.wheresmystuff.model.User.getCurrentUser;

public class HomeScreenActivity extends AppCompatActivity {
    TextView mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mCurrentUser = (TextView)findViewById(R.id.currentUser);
        mCurrentUser.setText("Welcome " + getCurrentUser().getName());
    }

    // Click method to perform logout function
    protected void onLogoutPressed(View view) {
        Intent logout = new Intent(this, WelcomeActivity.class);
        User.setCurrentUser(null);
        startActivity(logout);
    }

    // Click method to got to new Lost Item
    protected void onLostItemPressed(View view) {
        Intent lostItemPressed = new Intent(this, ReportLostItemActivity.class);
        startActivity(lostItemPressed);
    }

    // Click method to view lost items
    protected void onViewItemReportsPressed(View view) {
        Intent viewLostItems = new Intent(this, LostItemsActivity.class);
        startActivity(viewLostItems);
    }

    // Click method to view found items screen
    protected void onFoundItemsPressed(View view) {
        Intent foundItemsScreen = new Intent(getApplicationContext(), ViewFoundItemsActivity.class);
        startActivity(foundItemsScreen);
    }
}
