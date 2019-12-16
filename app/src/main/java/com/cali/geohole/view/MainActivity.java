package com.cali.geohole.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cali.geohole.R;
import com.cali.geohole.controller.DataBase;

public class MainActivity extends AppCompatActivity {

    // Init Variables
    DataBase dataBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // SplashScreen
        setTheme(R.style.AppTheme);
        // Show Content
        setContentView(R.layout.activity_main);
        // Init DB
        this.dataBase = new DataBase(this);
        // Init Process (All Actions in the tab MAIN)
    }
}