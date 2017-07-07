package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.Admin;
import com.example.drew.wheresmystuff.model.User;
import com.example.drew.wheresmystuff.model.UserManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private EditText ETname;
    private EditText ETemail;
    private EditText ETpassword;
    private Spinner userStatusSpinner;
    private Button submitButton;
    private Button cancelButton;
    private AlertDialog.Builder mBuilder;

    private FirebaseAuth mAuth;
    private String TAG = "registrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        ETname = (EditText) findViewById(R.id.registrationName);
        ETemail = (EditText) findViewById(R.id.registrationEmail);
        ETpassword = (EditText) findViewById(R.id.registrationPassword);
        submitButton = (Button) findViewById(R.id.registrationSubmitButton);
        cancelButton = (Button) findViewById(R.id.registrationCancelButton);
        mBuilder = new AlertDialog.Builder(this);

        // Populates the spinner with User and Admin options
        userStatusSpinner = (Spinner) findViewById(R.id.registrationStatusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userStatusSpinner.setAdapter(adapter);

        // Sets click action for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancel = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(cancel);
            }
        });

        // Sets click action for submit button, goes to Home Screen
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(ETemail.getText().toString(), ETpassword.getText().toString())
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    UserProfileChangeRequest dName = new UserProfileChangeRequest.Builder()
//                                            .setDisplayName(ETname.toString())
//                                            .build();
//                                    user.updateProfile(dName);
                                    Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(RegistrationActivity.this, R.string.auth_failed,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                }
            });
    }

    // Checks if registration was valid or not
    private boolean registrationAttempt() {
        return !(validEmail(ETemail.getText().toString()) && (ETpassword.getText().toString() != null)
                && (ETname.getText().toString() != null));
    }

    //Checks validity of email
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
