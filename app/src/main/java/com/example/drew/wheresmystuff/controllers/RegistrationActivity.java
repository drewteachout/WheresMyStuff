package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.drew.wheresmystuff.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText ETname;
    private EditText ETemail;
    private EditText ETpassword;
    private Spinner userStatusSpinner;
    private Button submitButton;
    private Button cancelButton;
    private AlertDialog.Builder mBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ETname = (EditText) findViewById(R.id.registrationName);
        ETemail = (EditText) findViewById(R.id.registrationEmail);
        ETpassword = (EditText) findViewById(R.id.registrationPassword);
        submitButton = (Button) findViewById(R.id.registrationSubmitButton);
        cancelButton = (Button) findViewById(R.id.registrationCancelButton);
        userStatusSpinner = (Spinner) findViewById(R.id.registrationStatusSpinner);
        mBuilder = new AlertDialog.Builder(this);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(getApplicationContext(),HomeScreenActivity.class);
                startActivity(cancel);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registrationAttempt()) {
                    mBuilder.setTitle("Error invalid inputs");
                    mBuilder.setMessage("Name, email, and password must be valid");
                    mBuilder.show();

                } else {
                    Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                    if (String.valueOf(userStatusSpinner.getSelectedItem()).equals("Admin")) {
                        Admin admin = new Admin(ETname.getText().toString(),
                                ETemail.getText().toString(), ETpassword.getText().toString(), false);
                        UserManager.myUserManager.putUser(ETemail.getText().toString(), admin);
                        User.setCurrentUser(admin);
                        startActivity(i);
                    } else {
                        User user = new Admin(ETname.getText().toString(),
                                ETemail.getText().toString(), ETpassword.getText().toString(), false);
                        UserManager.myUserManager.putUser(ETemail.getText().toString(), user);
                        User.setCurrentUser(user);
                        startActivity(i);
                    }

                }

            }
        });

    }

    private boolean registrationAttempt() {
        return !(validEmail(ETemail.getText().toString()) && (ETpassword.getText().toString() != null)
                && (ETname.getText().toString() != null));
    }

    private boolean validEmail(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
