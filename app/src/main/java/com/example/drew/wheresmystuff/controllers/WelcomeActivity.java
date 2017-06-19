package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.drew.wheresmystuff.R;

public class WelcomeActivity extends AppCompatActivity {
    Button registrationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        registrationButton = (Button) findViewById(R.id.btnRegister);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registationActivity = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(registationActivity);
            }
        });
    }


    protected void onLoginPressed(View view) {
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }
}
