package com.cali.geohole.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cali.geohole.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Init Variables
    DataBase dataBase = null;
    Navigation navigation;

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // switch (item.getItemId()) {
                // case R.id.navigation_home:
                //     viewPager.setCurrentItem(0);
//                     return true;
                //              case R.id.navigation_dashboard:
                //  viewPager.setCurrentItem(1);
//                     return true;
                //              case R.id.navigation_notifications:
                //  viewPager.setCurrentItem(2);
                //  return true;
                // }
            return false;
        }
    };


    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    //  navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    //  navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 2:
                    //  navigation.setSelectedItemId(R.id.navigation_notifications);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }


    }
}