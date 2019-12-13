package com.cali.geohole.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cali.geohole.R;

public class Login extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button button1 = view.findViewById(R.id.btnLogin);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                assert getFragmentManager() != null;
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.finish, new Finish(), "NewFragmentTag");
                ft.commit();
            }


        });
        return view;
    }


}
