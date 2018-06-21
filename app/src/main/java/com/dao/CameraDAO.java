package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

import com.model.Agents;
import com.model.CameraModel;
import com.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shumazahamedjunaidi on 10/6/17.
 */

public class CameraDAO extends SQLiteOpenHelper{

    public  CameraDAO(Context context){
        super(context,"CameraManage",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create Table Photos(id INTEGER PRIMARY KEY AUTOINCREMENT, profileImg BLOB, name TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Photos";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    public void dbinsert(byte[] imageProfile, String str){
        SQLiteDatabase db = getWritableDatabase();
       // db.execSQL("delete from Photos");
        ContentValues data = new ContentValues();
        data.put("profileImg",imageProfile);
        data.put("name",str);

       db.insert("Photos",null,data);

    }

    public List<CameraModel> readAgent(){
        SQLiteDatabase db = getReadableDatabase();
        // String[] columns ={"profileImg"};//colums name that you select
        // Cursor findEntry = db.query("Agents", columns, null,null, null, null, null);
        String sql = "Select * from Photos";
        Cursor findEntry = db.rawQuery(sql,null);
        List<CameraModel> dataList = new ArrayList<CameraModel>();

        while ((findEntry.moveToNext())){
            CameraModel data = new CameraModel();
            data.setId(findEntry.getInt(0));
            data.setImage(findEntry.getBlob(1));
            data.setName(findEntry.getString(findEntry.getColumnIndex("name")));
            dataList.add(data);
        }
        findEntry.close();
        return dataList;
    }
}
