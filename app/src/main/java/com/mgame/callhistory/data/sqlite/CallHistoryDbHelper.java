package com.mgame.callhistory.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TienBM on 12/17/2017.
 */

public class CallHistoryDbHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CallHistoryContract.CallEntry.TABLE_NAME + " (" +
                    CallHistoryContract.CallEntry._ID + " INTEGER PRIMARY KEY," +
                    CallHistoryContract.CallEntry.COLUMN_NAME_TITLE + " TEXT," +
                    CallHistoryContract.CallEntry.COLUMN_NAME_TIME + " Long," +
                    CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT + " INTEGER," +
                    CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CallHistoryContract.CallEntry.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CallHistory.db";

    public CallHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
