package com.hivluver.facebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class DataBaseFacebookAccount extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "fbaccounts";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "accounts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


    public DataBaseFacebookAccount(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL); "

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insertsAccounts(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
    public void deleteAccount(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Cuenta borrada!", Toast.LENGTH_SHORT).show();

    }
    public List<ModelAccounts> getAllActividades(String filter) {
        String query;
        if(filter.equals("")){
            query = "SELECT  * FROM " + TABLE_NAME + "ORDER BY " + filter + " DESC";
        }else{

            query = "SELECT  * FROM " + TABLE_NAME +" ORDER BY " + filter + " DESC";
        }

        List<ModelAccounts> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ModelAccounts person;

        if (cursor.moveToFirst()) {
            do {
                person = new ModelAccounts();
                person.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                person.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                personLinkedList.add(person);
            } while (cursor.moveToNext());
            db.close();
            cursor.close();
        }


        return personLinkedList;
    }

}
