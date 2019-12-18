package com.cali.geohole.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.cali.geohole.R;
import com.cali.geohole.controller.DataBase;
import com.cali.geohole.model.User;

public class Finish extends Activity {

    // Object User
    User user;

    // Elements View
    Button btnExit;
    Button btnNewData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_finish);
        this.user = (User) getIntent().getSerializableExtra("user");
        // Search IDs
        btnExit = findViewById(R.id.btnExit);
        btnNewData = findViewById(R.id.btnNewData);
        // Make actions
        actions();
    }


    private void actions(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
                finishAffinity();

            }
        });

        btnNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
                // Init Maps
                Intent viewMap = new Intent(getApplicationContext(), MapsActivity.class);
                // Send data A
                viewMap.putExtra("user", user);
                // Start new view
                startActivity(viewMap);
            }
            });
    }

    private void saveData(){
        // Init DB
        DataBase dataBase = new DataBase(getApplicationContext());
        dataBase.insertHole(user);
    }
}
