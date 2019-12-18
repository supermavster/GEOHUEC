package com.cali.geohole.model;

import android.content.Context;

import com.cali.geohole.R;

public class SQL {

    public static String LOGIN ;
    public static String INSERT_HOLE;
    // Config
    public static String DATABASE_NAME = "BD1";
    // User
    public static String DB_USER;
    private String id;
    private String cc;

    // Hole
    public static String DB_HOLE;
    public static String parametersHole = "idHueco INTEGER NOT NULL AUTO_INCREMENT, idUser INTEGER, latitud DOUBLE, longitud DOUBLE, fecha DATE, foto STRING, alto STRING, ancho STRING, profundo STRING, direccion STRING";
    // SQLs
    public static String CREATE_DB;
    public static String DELETE_DB;
    public static String INSERT_USER;

    public SQL(Context context) {
        // Database
        DB_USER = context.getString(R.string.table_name);
        this.id = context.getString(R.string.table_column_id);
        this.cc = context.getString(R.string.table_column_cc);
        // Hole
        DB_HOLE = "huco";

        // SQLS
        CREATE_DB = "CREATE TABLE IF NOT EXISTS " + DB_USER + "(idUser INTEGER NOT NULL AUTO_INCREMENT, " + this.id + " STRING, " + this.cc + " STRING);";
        CREATE_DB += "CREATE TABLE IF NOT EXISTS " + DB_HOLE + "(" + this.parametersHole + ");";

        DELETE_DB = "DELETE FROM " + DB_USER + ";";
        DELETE_DB += "DELETE FROM " + DB_HOLE + ";";

        // Select
        LOGIN = "select * from " + DB_USER + " where " + this.id + " = ? and " + this.cc + " = ? limit 1";

        // Hole
        INSERT_HOLE = "";
    }

    public String setInsertUser(String placa, String cc) {
        INSERT_USER = "INSERT INTO " + DB_USER + " (NULL, " + this.id + "," + this.cc + ") VALUES('" + placa + "', '" + cc + "');";
        return INSERT_USER;
    }

    public String setInsertHole(Hole hole) {
        INSERT_USER = "INSERT INTO " + DB_HOLE + " (idHueco, latitud, longitud, fecha, foto, alto, ancho, profundo, direccion) VALUES(NULL, '" + hole.getLatitude() +"', '" + hole.getLongitude() +"', date('now'), '" + hole.getPhoto().getPath() +"', '" + hole.getHeight() +"', '" + hole.getWidth() +"', '" + hole.getLength() +"', '" + hole.getAddress() + "');";
        return INSERT_USER;
    }
}

