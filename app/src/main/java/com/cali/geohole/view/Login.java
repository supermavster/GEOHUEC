package com.cali.geohole.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cali.geohole.R;
import com.cali.geohole.controller.DataBase;
import com.cali.geohole.model.User;

public class Login extends Fragment {
    DataBase DB;
    User user = null;
    private Button btnLogin;
    private EditText txtPlaca;
    private EditText txtCC;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        txtPlaca = view.findViewById(R.id.txtPlaque);
        txtCC = view.findViewById(R.id.txtPassword);
        DB = new DataBase(this.getContext());
        validateLogin();
        //verifyUser();
        return view;
    }

    public void validateLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Data elements
                String placa = txtPlaca.getText().toString();
                String cc = txtCC.getText().toString();
                user = new User(placa, cc);
                user = new User("656", "31713800");

                Boolean checkDatabase = DB.getUser(placa, cc);
                 if(checkDatabase){
                     Intent viewMap= new Intent(getContext(), MapsActivity.class);
                     startActivity(viewMap);
                }
            }
        });
    }


}
