package com.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.widget.Toast;

import com.example.shumazahamedjunaidi.agents.AddAgents;
import com.example.shumazahamedjunaidi.agents.FormHelper;
import com.example.shumazahamedjunaidi.agents.MissionActivity;
import com.model.Agents;
import com.model.CameraModel;
import com.model.SMSCLASS;
import com.model.User;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.model.Mission;
/**
 * Created by shumazahamedjunaidi on 10/6/17.
 */

public class AgentsDAO extends SQLiteOpenHelper{

    public  AgentsDAO(Context context){
        super(context,"AgentManage",null,8);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create Table Agents(id INTEGER PRIMARY KEY AUTOINCREMENT, profileImg BLOB, userName TEXT NOT NULL, level TEXT NOT NULL, website TEXT NOT NULL, country TEXT NOT NULL, phone TEXT NOT NULL, address TEXT NOT NULL, agency TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
        sql = "Create Table Missions(id INTEGER PRIMARY KEY AUTOINCREMENT, agentID TEXT NOT NULL, missionName TEXT NOT NULL, date TEXT NOT NULL, status TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
        sql = "Create Table Photos(id INTEGER PRIMARY KEY AUTOINCREMENT, profileImg BLOB)";
        sqLiteDatabase.execSQL(sql);
        sql = "Create Table SMS(id INTEGER, msg TEXT NOT NULL, number TEXT NOT NULL, status TEXT NOT NULL, name TEXT NOT NULL, time TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Agents";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS Missions";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS Photos";
        sqLiteDatabase.execSQL(sql);
        sql = "DROP TABLE IF EXISTS SMS";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }
    public ArrayList<String> smsMatchLookUp(String number){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns ={"id,userName"};//colums name that you select
        Cursor findEntry = db.query("Agents", columns, "phone=?", new String[] { number }, null, null, null);
        //String sql = "Select * from Users where userid="+user.getUserEmail()+" and password="+user.getPassword();
        //Cursor c = db.rawQuery(sql,null);
        ArrayList<String> result = new ArrayList<String>();
        if(findEntry.getCount()>0){
            findEntry.moveToNext();
            int res = findEntry.getInt(0);
            String name = findEntry.getString(1);
            result.add(String.valueOf(res));
            result.add(name);
            findEntry.close();
            return result;

        }
        findEntry.close();
        if(result.size() == 0){
            result.add("0");
            result.add(number);
        }
        return result;
    }


    public void dbinsertMSG(int id,String msg,String number,String status,String name,String time){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("id", String.valueOf(id));
        data.put("msg", msg);
        data.put("number", number);
        data.put("status", status);
        data.put("name", name);
        data.put("time", time);
        db.insert("SMS", null, data);

    }
    public List<SMSCLASS> readSMS() {
        SQLiteDatabase db = getReadableDatabase();
        ///String[] columns = {"id", "msg", "number", "status","name","time"};//colums name that you select
        ///Cursor findEntry = db.query("SMS", columns, null, null, null, null, null, null);
        Cursor findEntry = db.rawQuery("SELECT DISTINCT number,name,id,msg,status,time FROM SMS", null);
        List<SMSCLASS> smsList = new ArrayList<SMSCLASS>();
        if(findEntry.getCount() > 0) {
            while (findEntry.moveToNext()) {

                SMSCLASS sms = new SMSCLASS();
                sms.setNumber(findEntry.getString(0));
                sms.setName(findEntry.getString(1));
                sms.setId(findEntry.getInt(2));
                sms.setMsg(findEntry.getString(3));
                sms.setStatus(findEntry.getString(4));
                sms.setTime(findEntry.getString(5));

                smsList.add(sms);

            }
        }
        findEntry.close();
        return smsList;
    }
    public List<String> readComplteSMS(String number) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"msg"};//colums name that you select
        Cursor findEntry = db.query("SMS", columns, "number=?", new String[] { number }, null, null, null);
        ///Cursor findEntry = db.rawQuery("SELECT number,name,id,msg,status FROM SMS", null);
        List<String> smsList = new ArrayList<String>();
        if(findEntry.getCount() > 0) {
            while (findEntry.moveToNext()) {

                String sms = new String();
                sms = findEntry.getString(0);
                smsList.add(sms);

            }
        }
        findEntry.close();


        SQLiteDatabase dbw = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("status", "1");
        String[] args = new String[]{number};
        dbw.update("SMS", data, "number=?", args);
        dbw.close();
        return smsList;
    }

    public void dbinsert(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("profileImg", user.getImageView());
        data.put("userName", user.getUserName());
        data.put("level", user.getLevel());
        data.put("website", user.getWebsite());
        data.put("country", user.getCountry());
        data.put("phone", user.getPhone());
        data.put("address", user.getAddress());
        data.put("agency", user.getAddress());

        Log.d("AddAgents.recordId-",String.valueOf(AddAgents.recordId));

        Log.d("ContentValues -",String.valueOf(data));

        if(AddAgents.recordId!=0){

            String[] args = new String[]{String.valueOf(AddAgents.recordId)};
                db.update("Agents", data, "id=?" , args);

        }else {
                db.insert("Agents", null, data);
        }
    }

    public int getRecordID(User user){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns ={"id"};//colums name that you select
        Cursor findEntry = db.query("Agents", columns, "userName=? and level=?", new String[] { user.getUserName(), user.getLevel() }, null, null, null);
        //String sql = "Select * from Users where userid="+user.getUserEmail()+" and password="+user.getPassword();
        //Cursor c = db.rawQuery(sql,null);
        if(findEntry.getCount()>0){
            findEntry.moveToNext();
            int res = findEntry.getInt(0);
            Log.d("Res-",String.valueOf(res));

            findEntry.close();
            return res;

        }
        findEntry.close();
        return 0;
    }

    public List<User> readAgent(){
        SQLiteDatabase db = getReadableDatabase();
         String[] columns ={"profileImg","userName","level","website","country","phone","address","agency","id"};//colums name that you select
         Cursor findEntry = db.query("Agents", columns, null,null, null, null, null);
        //db.execSQL("DROP TABLE IF EXISTS Agents");
        //String sql = "Select * from Agents";
        //Cursor findEntry = db.rawQuery(sql,null);
        List<User> agentsList = new ArrayList<User>();
            if(findEntry.getCount() > 0) {
                while (findEntry.moveToNext()) {

                    User agents = new User();

                    agents.setImageView(findEntry.getBlob(0));
                    agents.setUserName(findEntry.getString(1));
                    agents.setLevel(findEntry.getString(2));
                    agents.setWebsite(findEntry.getString(3));
                    agents.setCountry(findEntry.getString(4));
                    agents.setPhone(findEntry.getString(5));
                    agents.setAddress(findEntry.getString(6));
                    agents.setAgency(findEntry.getString(7));
                    agents.setId(findEntry.getInt(8));
                    agentsList.add(agents);
                    Log.d("name",agents.getUserName());
                    Log.d("level",agents.getLevel());
                    Log.d("Agency",agents.getAgency());
                }
            }
        findEntry.close();
        //Log.d("name list ",agentsList.get(0).getUserName());

        return agentsList;
    }

    public void deleteRecord(User user){
        SQLiteDatabase db = getWritableDatabase();
        String[] param = {String.valueOf(user.getId())};
        db.delete("Agents","id = ?", param);
    }
    /// Mission Table Details
    public List<Mission> readMission(User mission){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns ={"missionName","date","status","id"};//colums name that you select
        Cursor findEntry = db.query("Missions", columns, "agentID=?", new String[] { String.valueOf(mission.getId()) },null, null, null, null);

        //db.execSQL("DROP TABLE IF EXISTS Agents");
        //String sql = "Select * from Agents";
        //Cursor findEntry = db.rawQuery(sql,null);
        List<Mission> missionList = new ArrayList<Mission>();
        Log.d("UserObj in ReadSQL ",mission.getUserName());
        Log.d("String.valueOf ",String.valueOf(mission.getId()));
        Log.d("findEntry.getCount() ",String.valueOf(findEntry.getCount()));
        if(findEntry.getCount() > 0) {
            while (findEntry.moveToNext()) {

                Mission miss = new Mission();

                miss.setUserName(findEntry.getString(0));
                miss.setDate(findEntry.getString(1));
                miss.setStatus(findEntry.getString(2));
                miss.setId(findEntry.getInt(3));
                missionList.add(miss);
                Log.d("miss name  ",miss.getUserName());
                Log.d("miss date  ",miss.getDate());
                Log.d("miss ID  ",String.valueOf(miss.getId()));

            }
        }
        findEntry.close();
       // Log.d("User Name --- list ",missionList.get(0).getUserName());

        return missionList;
    }


    public void dbinsertMission(Mission user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("missionName", user.getUserName());
        data.put("date", user.getDate());
        data.put("status", user.getStatus());

        Log.d("ContentValues -",String.valueOf(data));

        if(MissionActivity.recordId!=0){


            data.put("agentID", String.valueOf(MissionActivity.recordId));
            db.insert("Missions", null, data);
            Log.d("insert --- list ",data.toString());
            Log.d("Missionidinserty list ",String.valueOf(MissionActivity.recordId));
        }else {
            String[] args = new String[]{String.valueOf(MissionActivity.missionID)};
            db.update("Missions", data, "id=?" , args);
            Log.d("update --- list ",data.toString());
            Log.d("Missionidupdate list ",String.valueOf(MissionActivity.missionID));
        }
    }


    public void dbinsertPhotos(byte[] imageProfile){
        SQLiteDatabase db = getWritableDatabase();
        // db.execSQL("delete from Photos");
        ContentValues data = new ContentValues();
        data.put("profileImg",imageProfile);

        db.insert("Photos",null,data);

    }

    public List<CameraModel> readPhotos(){
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
            dataList.add(data);
        }
        findEntry.close();
        return dataList;
    }

}
