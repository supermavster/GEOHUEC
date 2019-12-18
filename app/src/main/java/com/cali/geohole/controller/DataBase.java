package com.cali.geohole.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cali.geohole.model.Hole;
import com.cali.geohole.model.SQL;
import com.cali.geohole.model.User;

import java.util.ArrayList;

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
        sqLiteDatabase.execSQL(SQL.CREATE_DB_HOLE);

        // Creador table de la base de datos
    }

    public void DeletePreviousData() {

        sqLiteDatabase.execSQL(SQL.DELETE_DB);
        sqLiteDatabase.execSQL(SQL.DELETE_DB_HOLE);

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

    public int getUserID(String placa, String cc) {
        Integer uid = 0;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(SQL.LOGIN, new String[]{placa, cc});
        if (cursor.moveToNext()) {
            uid = cursor.getInt(cursor.getColumnIndex("idUser"));
        }
        cursor.close();
        bd.close();
        return uid;
    }


    public boolean insertHole(User user) {
        boolean aux = false;
        ArrayList<Hole> holes = user.getHoles();
        int sizeHole = holes.size() - 1;
        Hole hole = holes.get(sizeHole);

        SQLiteDatabase bd = getReadableDatabase();
        // '" + userId + "', '" +< hole.getLatitude() +"', '" + hole.getLongitude() +"', date('now'), '" + hole.getPhoto() +"', '" + hole.getHeight() +"', '" + hole.getWidth() +"', '" + hole.getLength() +"', '" + hole.getAddress() + "'
          Cursor cursor = getReadableDatabase().rawQuery(SQL.INSERT_HOLE, new String[]{ user.getUserId().toString(),  hole.getLatitude().toString(), hole.getLongitude().toString(), "date('now')", hole.getPhoto(), hole.getHeight().toString(), hole.getWidth().toString(), hole.getLength().toString(), hole.getAddress()  });
        if (cursor.moveToNext()) {
            aux = true;
        }
        cursor.close();
        bd.close();

        return aux;
    }
}

