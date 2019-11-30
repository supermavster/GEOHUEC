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

public class SQLite  extends AsyncTask<Void, Void, Void> {

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

            HttpServiceClass httpServiceClass = new HttpServiceClass(this.HttpJSonURL);

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
                                String tempSubjectName = jsonObject.getString("placa");
                                String tempSubjectFullForm = jsonObject.getString("cedula");
                                String SQLiteDataBaseQueryHolder = "INSERT INTO " + this.TABLE_NAME + " (placa,cc) VALUES('" + tempSubjectName + "', '" + tempSubjectFullForm + "');";
                                this.sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(this.context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            this.sqLiteDatabase.close();
            progressDialog.dismiss();
            Toast.makeText(this.context, "SINCRONIZADO", Toast.LENGTH_LONG).show();

        }
    }

