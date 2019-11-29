package com.example.aiclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new myDbHelper(context);
    }



    public boolean insertData(int alarmid,int hour, int min, String tips, String week, int sov, String soundtrack,int status,int flag,String weeklength,int setting) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ALARMID,alarmid);
        contentValues.put(myDbHelper.HOUR,hour);
        contentValues.put(myDbHelper.MIN,min);
        contentValues.put(myDbHelper.TIPS,tips);
        contentValues.put(myDbHelper.WEEK,week);
        contentValues.put(myDbHelper.SOV,sov);
        contentValues.put(myDbHelper.SOUNDTRACK,soundtrack);
        contentValues.put(myDbHelper.ALARMSTATUS,status);
        contentValues.put(myDbHelper.FLAG,flag);
        contentValues.put(myDbHelper.WEEKLENGTH,weeklength);
        contentValues.put(myDbHelper.SET,setting);
        long result = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);

        return result != -1;

    }




    public void deleteall(Context context)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        try{
            db.execSQL("DELETE FROM "+ myDbHelper.TABLE_NAME);
        }catch(SQLiteException e)
        {
            e.printStackTrace();
        }
    }

private Alarm cursorToAlarm(Cursor cursor){
        Alarm alarm = new Alarm();
    alarm.setId(cursor.getInt(cursor.getColumnIndex(myDbHelper.UID)));
    alarm.setAlarmid(cursor.getInt(cursor.getColumnIndex(myDbHelper.ALARMID)));
    alarm.setWeek(cursor.getString(cursor.getColumnIndex(myDbHelper.WEEK)));
    alarm.setHour(cursor.getInt(cursor.getColumnIndex(myDbHelper.HOUR)));
    alarm.setMin(cursor.getInt(cursor.getColumnIndex(myDbHelper.MIN)));
    alarm.setSoundorvibrator(cursor.getInt(cursor.getColumnIndex(myDbHelper.FLAG)));
    alarm.setTips(cursor.getString(cursor.getColumnIndex(myDbHelper.TIPS)));
    alarm.setSoundtrack(cursor.getString(cursor.getColumnIndex(myDbHelper.SOUNDTRACK)));
    alarm.setStatus(cursor.getInt(cursor.getColumnIndex(myDbHelper.ALARMSTATUS)));
    alarm.setWeeklength(cursor.getString(cursor.getColumnIndex(myDbHelper.WEEKLENGTH)));
    alarm.setSetting(cursor.getInt(cursor.getColumnIndex(myDbHelper.SET)));
    return alarm;
}

    public ArrayList<Alarm> getData()
        {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ArrayList<Alarm> mydata = new ArrayList<>();
        String[] columns = {myDbHelper.UID, myDbHelper.ALARMID,myDbHelper.HOUR,myDbHelper.MIN,myDbHelper.TIPS,myDbHelper.SOUNDTRACK,myDbHelper.ALARMSTATUS,myDbHelper.FLAG,myDbHelper.WEEKLENGTH,myDbHelper.WEEK,myDbHelper.SET};
//       String[] columns = {myDbHelper.ALARMID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
     // Cursor cursor = db.query(true,myDbHelper.TABLE_NAME,columns,null,null,null,null,null,null);
//      Cursor cursor = db.rawQuery("SELECT DISTINCT " + myDbHelper.ALARMID + " FROM " + myDbHelper.TABLE_NAME,null);
       try
       {
           cursor.moveToFirst();
           while (!cursor.isAfterLast()) {
               Alarm alarm = cursorToAlarm(cursor);
               mydata.add(alarm);
               cursor.moveToNext();
           }
           cursor.close();
       }catch(Exception e)
       {
           e.printStackTrace();
       }


        return mydata;
    }

    public ArrayList<Alarm> getDatabyID(int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ArrayList<Alarm> mydata = new ArrayList<>();
        String[] columns = {myDbHelper.UID, myDbHelper.ALARMID,myDbHelper.HOUR,myDbHelper.MIN,myDbHelper.TIPS,myDbHelper.SOUNDTRACK,myDbHelper.ALARMSTATUS,myDbHelper.FLAG,myDbHelper.WEEKLENGTH,myDbHelper.WEEK,myDbHelper.SET};
//       String[] columns = {myDbHelper.ALARMID};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.UID+"="+id,null,null,null,null);
        // Cursor cursor = db.query(true,myDbHelper.TABLE_NAME,columns,null,null,null,null,null,null);
//      Cursor cursor = db.rawQuery("SELECT DISTINCT " + myDbHelper.ALARMID + " FROM " + myDbHelper.TABLE_NAME,null);
        try
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Alarm alarm = cursorToAlarm(cursor);
                mydata.add(alarm);
                cursor.moveToNext();
            }
            cursor.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }


        return mydata;
    }



    public void deletebyID(String id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + myDbHelper.TABLE_NAME+ " WHERE "+myDbHelper.UID+"='"+id+"'");
db.close();
    }
    public  int delete(int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={String.valueOf(id)};
        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.UID+" = ?",whereArgs);
        return  count;

    }

    public int updateStatus(int newstatus,int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ALARMSTATUS,newstatus);
        String[] whereArgs = {String.valueOf(id)};
        int count = db.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.ALARMID+" = ?",whereArgs);
        return count;
    }
    public int updateTips(String newtips,int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TIPS,newtips);
        String[] whereArgs = {String.valueOf(id)};
        int count = db.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.UID+" = ?",whereArgs);
        return count;
    }
    public int updateSetting(int newSet,int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.SET,newSet);
        String[] whereArgs = {String.valueOf(id)};
        int count = db.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.ALARMID+" = ?",whereArgs);
        return count;
    }

    public int updateAlarm(int alarmid,int hour, int min, String tips, String week, int sov, String soundtrack,int status,int flag,String weeklength ,int setting,int id)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.ALARMID,alarmid);
        contentValues.put(myDbHelper.HOUR,hour);
        contentValues.put(myDbHelper.MIN,min);
        contentValues.put(myDbHelper.TIPS,tips);
        contentValues.put(myDbHelper.WEEK,week);
        contentValues.put(myDbHelper.SOV,sov);
        contentValues.put(myDbHelper.SOUNDTRACK,soundtrack);
        contentValues.put(myDbHelper.ALARMSTATUS,status);
        contentValues.put(myDbHelper.FLAG,flag);
        contentValues.put(myDbHelper.WEEKLENGTH,weeklength);
        contentValues.put(myDbHelper.SET,setting);
        String[] whereArgs = {String.valueOf(id)};
        int count = db.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.UID+" = ?",whereArgs);
        return count;
    }
    public int updateName(String oldHour , String newHour)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.HOUR,newHour);
        String[] whereArgs= {oldHour};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.HOUR+" = ?",whereArgs );
        return count;
    }


    static class myDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myAlarm";   // Table Name
        private static final int DATABASE_Version = 40;   // Database Version
        private static final String UID = "_id";     // Column I (Primary Key)
        private static final String ALARMID = "AlarmID";
        private static final String HOUR = "Hour";//Column II
        private static final String MIN = "Min";
        private static final String TIPS = "Tips";
        private static final String WEEK = "Week";
        private static final String SOV = "SoundOrVibrator";
        private static final String SOUNDTRACK = "SoundTrack";
        private static final String ALARMSTATUS = "AlarmStatus";
        private static final String FLAG = "Flag";
        private static final String WEEKLENGTH = "WeekLength";
        private static final String SET = "Setting";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ ALARMID + " INTEGER,  " + HOUR + " INTEGER ," + MIN + " INTEGER, " + TIPS + " VARCHAR(255)" +
                "," + WEEK + " VARCHAR(255)," + SOV + " INTEGER," + SOUNDTRACK + " VARCHAR(255), " + ALARMSTATUS + " INTEGER, "+ FLAG + " INTEGER, "+ WEEKLENGTH + " VARCHAR(255), "+ SET + " INTEGER)";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "on upgrade", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Toast.makeText(context, "something wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

