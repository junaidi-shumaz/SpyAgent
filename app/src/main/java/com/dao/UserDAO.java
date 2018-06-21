package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import com.model.User;
/**
 * Created by shumazahamedjunaidi on 8/6/17.
 */

public class UserDAO extends SQLiteOpenHelper{

    public  UserDAO(Context context){
            super(context,"UserManage",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "Create Table Users(id INTEGER PRIMARY KEY AUTOINCREMENT, userid TEXT NOT NULL, password TEXT NOT NULL)";
            sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Users";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    public void dbinsert(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues userdata = new ContentValues();
        //userdata.put("userid",user.getUserEmail());
        //userdata.put("password",user.getPassword());
        db.insert("Users",null,userdata);

    }

    public boolean validate(User user){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns ={"userid"};//colums name that you select
        Cursor findEntry = db.query("Users", columns, "userid=? and password=?", new String[] { /*user.getUserEmail(), user.getPassword()*/ }, null, null, null);
        //String sql = "Select * from Users where userid="+user.getUserEmail()+" and password="+user.getPassword();
        //Cursor c = db.rawQuery(sql,null);
        if(findEntry.getCount()>0){
            findEntry.close();
            return true;
        }
        findEntry.close();
        return false;
    }
}
