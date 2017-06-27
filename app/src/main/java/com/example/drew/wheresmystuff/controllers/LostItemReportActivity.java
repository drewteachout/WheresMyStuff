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


public class LostItemReportActivity extends AppCompatActivity {
    private User reporter;
    private EditText ETitemName;
    private EditText ETitemDescription;
    private EditText ETlatitude;
    private EditText ETlongitude;
    private Spinner item_category_spinner;
    private EditText ETreward;
    private AlertDialog.Builder mBuilder;
    private Button submitButton;
    private Button cancelButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item_report);

        ETitemName = (EditText) findViewById(R.id.itemName);
        ETitemDescription = (EditText) findViewById(R.id.itemDescription);
        ETlatitude = (EditText) findViewById(R.id.itemLatitude);
        ETlongitude = (EditText) findViewById(R.id.itemLongitude);
        ETreward = (EditText) findViewById(R.id.itemReward);
        submitButton = (Button) findViewById(R.id.lostItemReportSubmitButton);
        cancelButton = (Button) findViewById(R.id.lostItemReportCancelButton);
        mBuilder = new AlertDialog.Builder(this);
        item_category_spinner = (Spinner) findViewById(R.id.itemCategorySpinner);

        /**
         * Cancels report and redirects back to Home Screen without saving
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LostItemReportActivity.this, "Report Canceled",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(i);
            }
        });
        /**
         * Submits report if valid, displays error message if invalid
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!submissionAttempt()) {
                    mBuilder.setTitle("Error missing inputs");
                    mBuilder.setMessage("All fields must have value ");
                    mBuilder.show();

                } else {
                    if (submissionAttempt()) {
                        reporter = User.getCurrentUser();
                        double latitude = Double.parseDouble(ETlatitude.getText().toString());
                        double longitude = Double.parseDouble(ETlongitude.getText().toString());

                        ItemReport report = new ItemReport(ETitemName.getText().toString(),ETitemDescription.getText().toString(),
                               latitude, longitude, String.valueOf(item_category_spinner.getSelectedItem()),
                                ETreward.getText().toString());
                        ItemReportManager.myItemReports.addReport(report);
                        Toast.makeText(LostItemReportActivity.this, "Report Submitted",
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
        if (ETitemName.getText().toString().equals("") ||
                ETitemDescription.getText().toString().equals("") ||
                ETlatitude.getText().toString().equals("") ||
                ETlongitude.getText().toString().equals("") ||
                ETreward.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
