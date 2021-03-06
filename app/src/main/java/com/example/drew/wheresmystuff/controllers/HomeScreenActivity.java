package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.User;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.drew.wheresmystuff.model.User.getCurrentUser;

public class HomeScreenActivity extends AppCompatActivity {
    TextView mCurrentUser;

    private FirebaseAuth mAuth;
    private FirebaseUser cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();

        //mCurrentUser = (TextView)findViewById(R.id.currentUser);
        //mCurrentUser.setText("Welcome " + cUser);
    }

    // Click method to perform logout function
    protected void onLogoutPressed(View view) {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        Intent logout = new Intent(this, WelcomeActivity.class);
        startActivity(logout);
    }

    // Click method to submit to new Lost Item
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
    protected void onViewFoundItemsPressed(View view) {
        Intent viewFoundItems = new Intent(getApplicationContext(), ViewFoundItemsActivity.class);
        startActivity(viewFoundItems);
    }

    // Click method to submit a new Found Item
    protected void onFoundItemsPressed(View view) {
        Intent submitFoundItem = new Intent(getApplicationContext(), FoundItemsActivity.class);
        startActivity(submitFoundItem);
    }
    
}
