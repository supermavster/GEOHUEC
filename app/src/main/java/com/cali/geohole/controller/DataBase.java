package com.cali.geohole.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cali.geohole.model.SQL;

public class DataBase extends SQLiteOpenHelper {

    // Varaibles & Const
    Context context;
    SQLiteDatabase sqLiteDatabase;
    SQLite SQLite;
    SQL queries;


    public DataBase(Context context){
        super(context, SQL.DATABASE_NAME, null, 1);
        this.context = context;
        initVariables();
        init();
    }

    public void init(){
        SQLiteDataBaseBuild();
        SQLiteTableBuild();
        DeletePreviousData();
        // Make Connection SQL
        this.SQLite = new SQLite(this.context, sqLiteDatabase);
        this.SQLite.execute();
    }

    public void initVariables() {
        this.queries = new SQL(this.context);
    }


    public void SQLiteDataBaseBuild() {
        // Creador de la base de sqlite
        sqLiteDatabase = this.context.openOrCreateDatabase(SQL.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }


    public void SQLiteTableBuild() {

        sqLiteDatabase.execSQL(SQL.CREATE_DB);
        // Creador table de la base de datos
    }

    public void DeletePreviousData() {

        sqLiteDatabase.execSQL(SQL.DELETE_DB);

    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL.CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL.DELETE_DB);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public Cursor searchUser(String placa, String cc) {
        Cursor cursor = getReadableDatabase().rawQuery(SQL.LOGIN, new String[]{placa, cc});
        return cursor;
    }

    public boolean getUser(String placa, String cc) {
        boolean aux=false;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(SQL.LOGIN, new String[]{placa, cc});
        if (cursor.moveToNext()) {
            aux = true;
        }
        cursor.close();
        bd.close();
        return aux;
    }
}

