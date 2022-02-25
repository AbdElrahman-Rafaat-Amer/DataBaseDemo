package com.abdo.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseAdapter {

    static DataBaseHelper helper;
    public DataBaseAdapter(Context context) {
        helper = new DataBaseHelper(context);
    }

    public long insert(DTO dto){
        long id;
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DataBaseHelper.MESSAGE, dto.getMessage());
        row.put(DataBaseHelper.NUMBER, dto.getNumber());
        id = sqLiteDatabase.insert(DataBaseHelper.TABLE_NAME, null, row);
        return id;
    }

    public DTO read(String number){
        DTO dto = new DTO();
        String sql = "SELECT " + DataBaseHelper.MESSAGE + " , " + DataBaseHelper.NUMBER +  " FROM " + DataBaseHelper.TABLE_NAME +
                " where " + DataBaseHelper.NUMBER + " = " + number;
        System.out.println("SQL = " + sql);
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        
        while (cursor.moveToNext()){
            dto.setMessage(cursor.getString(0));
            dto.setNumber(cursor.getString(1));
        }

        Log.i("TAG", "Message in read : " + dto.getMessage());
        Log.i("TAG", "Number in read  : " + dto.getNumber());
        return dto;
    }

    static class DataBaseHelper extends SQLiteOpenHelper{
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "MESSAGE_MOBILE_DATABASE";

        private static final String TABLE_NAME = "INFORMATION";
        private static final String ID = "id";
        private static final String MESSAGE = "message";
        private static final String NUMBER = "number";

        private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MESSAGE + " TEXT, " + NUMBER + " TEXT);";

        public DataBaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
