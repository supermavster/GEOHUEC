package com.cali.geohole.model;

import android.content.Context;

import com.cali.geohole.R;

public class SQL {

    // Config Base
    public static String DATABASE_NAME = "BD1";
    public static String DB;
    private String id;
    private String cc;

    // SQLs
    public static String CREATE_DB;
    public static String DELETE_DB;
    public static String INSERT_USER;

    public SQL(Context context) {
        // Database
        DB = context.getString(R.string.table_name);
        this.id = context.getString(R.string.table_column_id);
        this.cc = context.getString(R.string.table_column_cc);
        // SQLS
        CREATE_DB = "CREATE TABLE IF NOT EXISTS " + DB + "(" + this.id + " STRING, " + this.cc + " STRING);";
        DELETE_DB = "DELETE FROM " + DB;
    }

    public String setInsertUser(String placa, String cc) {
        INSERT_USER = "INSERT INTO " + DB + " (placa,cc) VALUES('" + placa + "', '" + cc + "');";
        return INSERT_USER;
    }
}

