package com.cali.geohole.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cali.geohole.R;

public class DataBase extends SQLiteOpenHelper {

    // Varaibles & Const

    private String TableName = "";
    private String TableColumnID = "";
    private String TableColumn1 = "";
    private String DatabaseName = "";
    static String DATABASE_NAME = "BD1";


    Context context;
    SQLiteDatabase sqLiteDatabase;

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        initVariables();
        // super(context, DatabaseName, null, 1);
        init();
    }

    public void init(){
        SQLiteDataBaseBuild();
        SQLiteTableBuild();
        DeletePreviousData();
        new SQLite(this.context, sqLiteDatabase).execute();
    }

    public void initVariables() {
        this.TableName = this.context.getString(R.string.table_name);
        this.TableColumnID = this.context.getString(R.string.table_column_id);
        this.TableColumn1 = this.context.getString(R.string.table_column_1);
        this.DatabaseName = this.context.getString(R.string.database_name);
    }


    public void SQLiteDataBaseBuild() {
        // Creador de la base de sqlite
        sqLiteDatabase = this.context.openOrCreateDatabase(this.DatabaseName, Context.MODE_PRIVATE, null);
    }


    public void SQLiteTableBuild() {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + this.TableName + "(" + this.TableColumnID + " STRING, " + this.TableColumn1 + " STRING);");
        // Creador table de la base de datos
    }

    public void DeletePreviousData() {

        sqLiteDatabase.execSQL("DELETE FROM " + this.TableName + "");

    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + this.TableName + " (" + this.TableColumnID + " STRING PRIMARY KEY, " + this.TableColumn1 + " STRING)";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.TableName);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}

