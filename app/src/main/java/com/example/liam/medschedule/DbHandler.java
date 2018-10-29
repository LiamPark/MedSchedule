package com.example.liam.medschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medicine.db";
    private static final String TABLE_MEDICINE = "medicine";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_medicineName = "medicine_name";
    private static final String COLUMN_Dose = "dose";
    private static final String COLUMN_Unit = "unit";
    private static final String COLUMN_medicineDesc = "medicine_desc";
    private static final String COLUMN_date = "date";

    private static final String TAG = "DbHandler";

    //pass database information along to superclass

    public DbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create medicine table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DbHandler","create DB");
        System.out.println("create DB");

        String query= "CREATE TABLE " + TABLE_MEDICINE + "( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_medicineName + " TEXT, " +
                COLUMN_Dose + " INTEGER, " +
                COLUMN_Unit + " TEXT, " +
                COLUMN_medicineDesc + " TEXT, " +
                COLUMN_date + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
    }

    //adding new medicine detail
    public void addMedicine(medicines m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_medicineName, m.getMedicine_name());
        values.put(COLUMN_Dose, m.getDose());
        values.put(COLUMN_Unit, m.getUnit());
        values.put(COLUMN_medicineDesc, m.getMedicine_desc());
        values.put(COLUMN_date, m.getDate());
        long newRodId = db.insert(TABLE_MEDICINE,null,values);
        Log.i(TAG,String.valueOf(newRodId));
        db.close();
    }

    //get all medicine details
    public ArrayList<HashMap<String,String>> GetMedicines(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> medicineList = new ArrayList<>();
        String query = "SELECT id, medicine_name, dose, unit, medicine_desc, date FROM " + TABLE_MEDICINE;
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            HashMap<String,String> medicine = new HashMap<>();
            medicine.put("id",cursor.getString((cursor.getColumnIndex(COLUMN_ID))));
            medicine.put("medicine_name",cursor.getString(cursor.getColumnIndex(COLUMN_medicineName)));
            medicine.put("dose",cursor.getString(cursor.getColumnIndex(COLUMN_Dose)));
            medicine.put("unit",cursor.getString(cursor.getColumnIndex(COLUMN_Unit)));
            medicine.put("medicine_desc",cursor.getString(cursor.getColumnIndex(COLUMN_medicineDesc)));
            medicine.put("date",cursor.getString(cursor.getColumnIndex(COLUMN_date)));
            medicineList.add(medicine);
        }
        return medicineList;
    }

    //get medicines by speicfic date chosen.
    public ArrayList<HashMap<String,String>> GetMedicinesByDate(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String,String>> medicineList = new ArrayList<>();
        String query = "SELECT id, medicine_name, dose, unit, medicine_desc FROM " + TABLE_MEDICINE +
                " WHERE " + COLUMN_date + " = '" + date + "';";
        Log.i("GetMedicinesByDate",query);
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            HashMap<String,String> medicine = new HashMap<>();
            medicine.put("id",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            medicine.put("medicine_name",cursor.getString(cursor.getColumnIndex(COLUMN_medicineName)));
            medicine.put("dose",cursor.getString(cursor.getColumnIndex(COLUMN_Dose)));
            medicine.put("unit",cursor.getString(cursor.getColumnIndex(COLUMN_Unit)));
            medicine.put("medicine_desc",cursor.getString(cursor.getColumnIndex(COLUMN_medicineDesc)));
            medicineList.add(medicine);
        }
        return medicineList;
    }

    //delete medicine with the speicic id.
    public void DeleteMedicine(int medicineid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, COLUMN_ID + "= ?", new String[]{String.valueOf(medicineid)});
        db.close();
    }

    //update medicine with id.
    public int UpdateMedicine(medicines m, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_medicineName, m.getMedicine_name());
        values.put(COLUMN_Dose, m.getDose());
        values.put(COLUMN_Unit, m.getUnit());
        values.put(COLUMN_medicineDesc, m.getMedicine_desc());
        values.put(COLUMN_date,m.getDate());
        int count = db.update(TABLE_MEDICINE, values,
                COLUMN_ID + "= ?", new String[]{String.valueOf(id)});
        System.out.println(count);
        return count;
    }
}