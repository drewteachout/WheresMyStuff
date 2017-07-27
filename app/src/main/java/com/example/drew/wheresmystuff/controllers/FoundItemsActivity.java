package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.FoundItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;
import com.example.drew.wheresmystuff.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FoundItemsActivity extends AppCompatActivity {
    private User reporter;
    private EditText mItemName;
    private EditText mItemDescription;
    private EditText mLatitude;
    private EditText mLongitude;
    private Spinner item_category_spinner;
    private EditText mReward;
    private AlertDialog.Builder mBuilder;
    private Button mSubmitButton;
    private Button mCancelButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_items);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mItemName = (EditText) findViewById(R.id.itemName);
        mItemDescription = (EditText) findViewById(R.id.itemDescription);
        mLatitude = (EditText) findViewById(R.id.itemLatitude);
        mLongitude = (EditText) findViewById(R.id.itemLongitude);
        mReward = (EditText) findViewById(R.id.itemReward);
        mSubmitButton = (Button) findViewById(R.id.foundItemSubmitButton);
        mCancelButton = (Button) findViewById(R.id.foundItemCancelButton);
        mBuilder = new AlertDialog.Builder(this);
        item_category_spinner = (Spinner) findViewById(R.id.itemCategorySpinner);

        /**
         * Cancels report and redirects back to Home Screen without saving
         */
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoundItemsActivity.this, "Report Canceled",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(i);
            }
        });

        /**
         * Submits report if valid, displays error message if invalid
         */
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!submissionAttempt()) {
                    mBuilder.setTitle("Error missing inputs");
                    mBuilder.setMessage("All fields must have value ");
                    mBuilder.show();
                } else {
                    if (submissionAttempt()) {
                        //reporter = User.getCurrentUser();
                        double latitude = Double.parseDouble(mLatitude.getText().toString());
                        double longitude = Double.parseDouble(mLongitude.getText().toString());

                        FoundItemReport report = new FoundItemReport(mItemName.getText().toString(), mItemDescription.getText().toString(),
                                latitude, longitude, String.valueOf(item_category_spinner.getSelectedItem()),
                                mReward.getText().toString());
                        mDatabase.child("founditems").child(report.getItemName()).setValue(report);
                        Toast.makeText(FoundItemsActivity.this, "Report Submitted",
                                Toast.LENGTH_SHORT).show();
                        Intent x = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(x);
                    }
                }
            }
        });

    }

    /**
     * Checks that all entries have a value
     * @return if the submission is valid
     */
    private boolean submissionAttempt() {
        if (mItemName.getText().toString().equals("") ||
                mItemDescription.getText().toString().equals("") ||
                mLatitude.getText().toString().equals("") ||
                mLongitude.getText().toString().equals("") ||
                mReward.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
