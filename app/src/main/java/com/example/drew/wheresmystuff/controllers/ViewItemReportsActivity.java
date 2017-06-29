package com.example.drew.wheresmystuff.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.drew.wheresmystuff.R;
import com.example.drew.wheresmystuff.model.ItemReport;
import com.example.drew.wheresmystuff.model.ItemReportManager;

import java.util.Collection;

public class ViewItemReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_reports);
        TextView report_list = (TextView)findViewById(R.id.viewItemReports);
        /**
         * Dsiplays all submitted item reports
         */
        Collection<ItemReport> reports = ItemReportManager.myItemReports.getAllReports();
        for(ItemReport report : reports) {
            report_list.append(report.toString());
            report_list.append("\n");

        }
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

    private void doNothing() {
        // Insert code here
    }
}
