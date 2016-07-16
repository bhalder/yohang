package com.example.bhalder.yohang_4;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;
import android.database.Cursor;

/**
 * Created by bhalder on 7/16/16.
 */
public class db_util extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "YoDB";

    public db_util(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Books table name
    private static final String TABLE_USER = "user";

    // Books Table Columns names
    private static final String KEY_ID = "userId";
    private static final String KEY_FULLNAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY__ID = "_id";
    private static final String KEY_POINTS = "points";

    private static final String[] COLUMNS = {KEY_ID,KEY_FULLNAME, KEY__ID, KEY_EMAIL, KEY_POINTS};

    private static final String TABLE_MISSION = "mission";

    // Books Table Columns names
    private static final String KEY_M_ID = "mission_id";
    private static final String KEY_M_LAT = "lat";
    private static final String KEY_M_LON = "lon";
    private static final String KEY_M_STATUS = "status";
    private static final String KEY_M_POINTS = "pointsEarned";


    private static final String[] COLUMNS_M = {KEY_M_ID, KEY_M_LAT, KEY_M_LON, KEY_M_STATUS, KEY_M_POINTS};

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_USER_TABLE = "CREATE TABLE user ( " +
                "userId TEXT PRIMARY KEY, " +
                "fullName TEXT, "+
                "_id TEXT,"+
                "email TEXT," +
                "points INTEGER )";


        String CREATE_MISSION_TABLE = "CREATE TABLE mission ( " +
                "mission_id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "lat FLOAT, "+
                "lon FLOAT,"+
                "status BOOL," +
                "pointsEarned INTEGER )";

        // create books table
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_MISSION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS mission");
        // create fresh books table
        this.onCreate(db);
    }

    public void addMission(db_mission mission){
        //for logging
        Log.d("addBook", mission.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        //KEY_M_ID, KEY_M_LAT, KEY_M_LON, KEY_M_STATUS, KEY_M_POINTS

        values.put(KEY_M_LAT, mission.lat);
        values.put(KEY_M_LON, mission.lon);
        values.put(KEY_M_STATUS, mission.status);
        values.put(KEY_M_POINTS, mission.pointsEarned);

        // 3. insert
        db.insert(TABLE_MISSION, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public db_mission getMission(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_MISSION, // a. table
                        COLUMNS_M, // b. column names
                        " mission_id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null && cursor.getCount() > 1) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        //KEY_M_ID, KEY_M_LAT, KEY_M_LON, KEY_M_STATUS, KEY_M_POINTS

        // 4. build book object
        db_mission mission = new db_mission( Integer.parseInt(cursor.getString(0)), Float.parseFloat(cursor.getString(1)), Float.parseFloat(cursor.getString(2)), Boolean.parseBoolean(cursor.getString(3)), Integer.parseInt(cursor.getString(4)) );

        //log
        Log.d("getBook("+id+")", mission.toString());

        // 5. return book
        return mission;
    }

    public int updateMission(db_mission mission) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //KEY_M_ID, KEY_M_LAT, KEY_M_LON, KEY_M_STATUS, KEY_M_POINTS
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_M_POINTS, mission.pointsEarned); // get title
        values.put(KEY_M_LAT, mission.lat); // get author
        values.put(KEY_M_LON, mission.lon);
        values.put(KEY_M_STATUS, mission.status);

        // 3. updating row
        int i = db.update(TABLE_MISSION, //table
                values, // column/value
                KEY_M_ID+" = ?", // selections
                new String[] { String.valueOf(mission.mission_id) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteMission(db_mission mission) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MISSION, //table name
                KEY_M_ID+" = ?",  // selections
                new String[] { String.valueOf(mission.mission_id) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", mission.toString());

    }


    /// CRUD USER

    public void addUser(db_user user){
        //for logging
        Log.d("addBook", user.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        //KEY_ID,KEY_FULLNAME, KEY__ID, KEY_EMAIL, KEY_POINTS

        values.put(KEY_ID, user.userId); //
        values.put(KEY_FULLNAME, user.fullName);
        values.put(KEY__ID, user._id);
        values.put(KEY_EMAIL, user.email);
        values.put(KEY_POINTS, user.points);

        // 3. insert
        db.insert(TABLE_USER, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public db_user getUser(String id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_USER, // a. table
                        COLUMNS, // b. column names
                        " userId = ?", // c. selections
                        new String[] { (id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null && cursor.getCount() > 1) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        //KEY_ID,KEY_FULLNAME, KEY__ID, KEY_EMAIL, KEY_POINTS

        // 4. build book object
        db_user user = new db_user( cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)) );

        //log
        Log.d("getBook("+id+")", user.toString());

        // 5. return book
        return user;
    }

    public int updateUser(db_user user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_POINTS, user.points); // get title
        values.put(KEY_EMAIL, user.email); // get author
        values.put(KEY_FULLNAME, user.fullName);

        // 3. updating row
        int i = db.update(TABLE_USER, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { (user.userId) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    public void deleteUser(db_user user) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USER, //table name
                KEY_ID+" = ?",  // selections
                new String[] { (user.userId) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", user.userId);

    }
}
