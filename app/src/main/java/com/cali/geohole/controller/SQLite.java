package com.cali.geohole.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cali.geohole.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLite extends AsyncTask<Void, Void, Void> {

    ProgressDialog progressDialog;
    private Context context;
    private String HttpJSonURL;
    private String TABLE_NAME;
    private String FinalJSonResult;
    private SQLiteDatabase sqLiteDatabase;

    public SQLite(Context context, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.sqLiteDatabase = sqLiteDatabase;
        this.HttpJSonURL = this.context.getString(R.string.api);
        this.TABLE_NAME = this.context.getString(R.string.table_name);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle("CARGANDO");
        progressDialog.setMessage("EN ESPERA");
        progressDialog.show();
    }

    // LLena la base de datos con el JSon
    @Override
    protected Void doInBackground(Void... arg0) {


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        this.sqLiteDatabase.close();
        progressDialog.dismiss();
        Toast.makeText(this.context, "SINCRONIZADO", Toast.LENGTH_LONG).show();

    }
}

