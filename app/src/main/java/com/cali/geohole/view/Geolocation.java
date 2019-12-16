package com.cali.geohole.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.cali.geohole.MapsActivity;
import com.cali.geohole.R;
import com.cali.geohole.model.User;


public class Geolocation extends Fragment {

    User user = null;

    Button btnGeolocation;
    View googleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get Data
        Bundle args = getArguments();
        if (args != null) {
            user = (User) args.getSerializable("user");
        }
        String placa = user.getPlaca();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_geolocation, container, false);
        btnGeolocation = view.findViewById(R.id.btnGeolocation);
        View googleMap = view.findViewById(R.id.map);

        nextFragment();
        return view;
    }

    private void nextFragment() {
        btnGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Data elements
                MapsActivity mapita = new MapsActivity();
                // Boolean checkDatabase = DB.getUser(placa, cc);
            }
        });
    }
}
