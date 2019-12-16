package com.cali.geohole.view;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cali.geohole.R;
import com.cali.geohole.controller.DataBase;
import com.cali.geohole.model.User;

public class Login extends Fragment {
    DataBase DB;
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
                User user = new User(placa, cc);
                new GetUser(user).execute();
                // Boolean checkDatabase = DB.getUser(placa, cc);
            }
        });
    }

    private class GetUser extends AsyncTask<Void, Void, Cursor> {

        private User user = null;

        GetUser(User user) {
            this.user = user;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Cursor checkUser = DB.searchUser(user.getPlaca(), user.getCedula());
            return checkUser;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToFirst()) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new Finish());
                ft.commit();
            } else {
                Toast.makeText(getActivity(),
                        "No se encuentra el usuario", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
