package com.kivitool.theweatherchannel2020.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    Context context;

    private static String DbName = "EMPLOYEES";
    private static int Version = 7;


    private static String TB_Tablename = "DB_Tablename";
    private static String CL_Id = "CL_Id";
    private static String CL_Name = "DB_Name";


    public DatabaseHandler(Context context) {
        super(context, DbName, null, Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_Tablename + "(" +
                CL_Id + " INTEGER PRIMARY KEY ," +
                CL_Name + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_Tablename);
        onCreate(db);

    }

    public long InsertColumn(String CP_Name) {

        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CL_Name, CP_Name);

        long res = sql.insert(TB_Tablename, null, contentValues);

        return res;
    }


    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TB_Tablename;
        Cursor data = db.rawQuery(query, null);

        return data;
    }


    public void removeLocationDataListItem(int id) {

//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TB_Tablename, CL_Id, null);
//        db.delete(TB_Tablename, CL_Name, null);
//        db.close();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(TB_Tablename, "CL_Id=" + id, null);
        sqLiteDatabase.close();


    }

    public boolean checkIsRowExists(String dbName) {

        String[] columns = {CL_Id};

        SQLiteDatabase db = this.getReadableDatabase();

        String select = CL_Name + "=?";

        String[] selectControl = {dbName};


        Cursor cursor = db.query(TB_Tablename, columns, select, selectControl, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();


        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void updateDatabaseLocationName(String locationName) {

        String[] columns = {CL_Id};

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CL_Name, locationName);

        String select=CL_Name + "=?";

        database.update(TB_Tablename, values, select, columns);

//        if (result==-1){
//            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(context, "Successfully to Update", Toast.LENGTH_SHORT).show();
//        }

        database.close();

    }


}
