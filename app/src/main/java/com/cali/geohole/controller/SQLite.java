package com.cali.geohole.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import com.cali.geohole.R;
import com.cali.geohole.model.SQL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLite extends AsyncTask<Void, Void, Void> {

    ProgressDialog progressDialog;
    private Context context;
    private String HttpJSonURL;
    private String FinalJSonResult;
    private SQLiteDatabase sqLiteDatabase;
    private SQL queries;

    public SQLite(Context context, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.sqLiteDatabase = sqLiteDatabase;
        this.HttpJSonURL = this.context.getString(R.string.api);
        // SQL
        this.queries = new SQL(this.context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setTitle(R.string.dialog_title);
        String message = this.context.getResources().getString(R.string.dialog_message);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    // LLena la base de datos con el JSon
    @Override
    protected Void doInBackground(Void... arg0) {

        HttpServiceClass httpServiceClass = new HttpServiceClass(HttpJSonURL);
        try {
            httpServiceClass.ExecutePostRequest();
            if (httpServiceClass.getResponseCode() == 200) {
                FinalJSonResult = httpServiceClass.getResponse();
                if (FinalJSonResult != null) {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(FinalJSonResult);
                        JSONObject jsonObject;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            String placa = jsonObject.getString("placa");
                            String cc = jsonObject.getString("cedula");
                            String SQLiteDataBaseQueryHolder = this.queries.setInsertUser(placa, cc);
                            sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        this.sqLiteDatabase.close();
        progressDialog.dismiss();
        Toast.makeText(this.context, R.string.dialog_end, Toast.LENGTH_LONG).show();

    }

}

