package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;
import com.example.drew.wheresmystuff.model.User;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Locale;


public class ReportLostItemActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 10000;

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

    private Button openMapButton;
    private GoogleMapViewFragment mapViewFragment;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser cUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lost_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();

        //mapViewFragment = findViewById(R.id.mapViewFragment);

        mItemName = (EditText) findViewById(R.id.itemName);
        mItemDescription = (EditText) findViewById(R.id.itemDescription);
        mLatitude = (EditText) findViewById(R.id.itemLatitude);
        mLongitude = (EditText) findViewById(R.id.itemLongitude);
        mReward = (EditText) findViewById(R.id.itemReward);
        mSubmitButton = (Button) findViewById(R.id.lostItemReportSubmitButton);
        mCancelButton = (Button) findViewById(R.id.lostItemReportCancelButton);
        openMapButton = (Button) findViewById(R.id.openmapbutton);
        mBuilder = new AlertDialog.Builder(this);
        item_category_spinner = (Spinner) findViewById(R.id.itemCategorySpinner);

        /**
         * Cancels report and redirects back to Home Screen without saving
         */
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReportLostItemActivity.this, "Report Canceled",
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

                        ItemReport report = new ItemReport(mItemName.getText().toString(), mItemDescription.getText().toString(),
                                latitude, longitude, String.valueOf(item_category_spinner.getSelectedItem()),
                                mReward.getText().toString());
                        mDatabase.child("lostitems").child(report.getItemName()).setValue(report);
                        Toast.makeText(ReportLostItemActivity.this, "Report Submitted",
                                Toast.LENGTH_SHORT).show();
                        Intent x = new Intent(getApplicationContext(), HomeScreenActivity.class);
                        startActivity(x);
                    }
                }
            }
        });
        /*
        Google Map prep and integration.  Unsure about where to hook this
         */
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(getApplicationContext(), GoogleMapReportItemLocationActivity.class);
                startActivityForResult(x, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            LatLng location = data.getParcelableExtra("location");
            mLatitude.setText(String.format(Locale.getDefault(),"%f", location.latitude));
            mLongitude.setText(String.format(Locale.getDefault(),"%f", location.longitude));
        }
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
