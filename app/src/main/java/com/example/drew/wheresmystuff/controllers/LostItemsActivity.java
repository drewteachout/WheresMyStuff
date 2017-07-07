package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;

import java.util.ArrayList;
import java.util.Collection;

public class LostItemsActivity extends AppCompatActivity {

    private static final String TAG = "LostItemsActivity";

    private DatabaseReference mDatabase = mDatabase = FirebaseDatabase.getInstance().getReference();

    //private ArrayList<ItemReport> reports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_reports);

        final TextView report_list = (TextView)findViewById(R.id.viewItemReports);
        /**
         * Dsiplays all submitted item reports
         */

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    report_list.append("\n" + snap.getValue().toString());
                    //Log.d("tag" ,dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /**
         * Takes user back to home screen
         */
        Button mReturnToMain = (Button) findViewById(R.id.returnToMain);
        mReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
                startActivity(i);

            }
        });
    }
}
