package com.cali.geohole.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cali.geohole.model.SQL;

public class DataBase extends SQLiteOpenHelper {

    // Varaibles & Const

    static String DATABASE_NAME = "BD1";


    Context context;
    SQLiteDatabase sqLiteDatabase;
    SQLite SQLite;
    SQL queries;


    public DataBase(Context context){
        super(context, DATABASE_NAME, null, 1);
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
        sqLiteDatabase = this.context.openOrCreateDatabase(SQL.DB, Context.MODE_PRIVATE, null);
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

    public Cursor searchUser(String placa) {
        Cursor c = getReadableDatabase().rawQuery("select * from usuario where placa = ?", new String[] { placa });
        return  c;
    }

    public boolean getUser(String placa, String cc) {
        boolean aux=false;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery("select * from usuario where placa = ? and cc = ?", new String[]{placa, cc});
        if (cursor.moveToNext()) {
            aux=true;
        }
        cursor.close();
        bd.close();
        return aux;
    }
}

