package com.cali.geohole.view;

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

public class Finish extends Fragment {

    // Object User
    User user;

    // Elements View
    Button btnExit;
    Button btnNewData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        // Get Data B
        this.user = (User) getActivity().getIntent().getSerializableExtra("user");
        // Search IDs
        btnExit = view.findViewById(R.id.btnExit);
        btnNewData = view.findViewById(R.id.btnNewData);
        // Make actions
        actions();
        return view;
    }

    private void actions(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
                // Init Maps
                Intent viewMap = new Intent(getContext(), Login.class);
                // Start new view
                startActivity(viewMap);
            }
        });

        btnNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
                // Init Maps
                Intent viewMap = new Intent(getContext(), MapsActivity.class);
                // Send data A
                viewMap.putExtra("user", user);
                // Start new view
                startActivity(viewMap);
            }
            });
    }

    private void saveData(){
        // Init DB
        DataBase dataBase = new DataBase(this.getContext());
        dataBase.insertHole(user);
    }
}
