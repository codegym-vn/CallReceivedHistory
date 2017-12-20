package com.mgame.callhistory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mgame.callhistory.data.models.CallHistory;
import com.mgame.callhistory.data.sqlite.CallHistoryContract;
import com.mgame.callhistory.data.sqlite.CallHistoryDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TienBM on 12/17/2017.
 */

public class DataManager {
    private static final DataManager mDataManager = new DataManager();
    private CallHistoryDbHelper mDbHelper;

    public DataManager() {

    }

    public void initDb(Context context) {
        mDbHelper = new CallHistoryDbHelper(context);
    }

    public static DataManager getInstance() {
        return mDataManager;
    }

    public boolean insertEntry(CallHistory callHistory) {
// Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CallHistoryContract.CallEntry.COLUMN_NAME_TITLE, callHistory.getTitle());
        values.put(CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER, callHistory.getPhone());
        values.put(CallHistoryContract.CallEntry.COLUMN_NAME_TIME, callHistory.getTimeAt());
        values.put(CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT, callHistory.getAmount());

// Insert the new row, returning the primary key value of the new row
        return db.insert(CallHistoryContract.CallEntry.TABLE_NAME, null, values) != -1;

    }


    public List<CallHistory> getAllHistory() {
        if (mDbHelper == null)
            throw new RuntimeException("Please call DataManager.getInstance().initDb(context)");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                CallHistoryContract.CallEntry._ID,
                CallHistoryContract.CallEntry.COLUMN_NAME_TITLE,
                CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER,
                CallHistoryContract.CallEntry.COLUMN_NAME_TIME,
                CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = CallHistoryContract.CallEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {"My Title"};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                CallHistoryContract.CallEntry.COLUMN_NAME_TIME + " DESC";

//        Cursor cursor = db.query(
//                CallHistoryContract.CallEntry.TABLE_NAME,                     // The table to query
//                projection,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        );
        Cursor cursor = db.rawQuery("SELECT _id,title,phone_number,time_at,SUM(amount) as amount FROM " + CallHistoryContract.CallEntry.TABLE_NAME + " GROUP BY " + CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER + " ORDER BY " + CallHistoryContract.CallEntry.COLUMN_NAME_TIME + " DESC", null);
        List<CallHistory> itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry._ID));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_TITLE));
            String phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER));
            long timeAt = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_TIME));

            int amount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT));
            CallHistory callHistory = new CallHistory(title, phone, timeAt);
            callHistory.setId(itemId);
            callHistory.setAmount(amount);
            itemIds.add(callHistory);
        }
        cursor.close();
        return itemIds;
    }

    public List<CallHistory> getAllHistoryByPhone(String phoneNumber) {
        if (mDbHelper == null)
            throw new RuntimeException("Please call DataManager.getInstance().initDb(context)");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                CallHistoryContract.CallEntry._ID,
                CallHistoryContract.CallEntry.COLUMN_NAME_TITLE,
                CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER,
                CallHistoryContract.CallEntry.COLUMN_NAME_TIME,
                CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT,
        };

// Filter results WHERE "title" = 'My Title'
        String selection = CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                CallHistoryContract.CallEntry.COLUMN_NAME_TIME + " DESC";

        Cursor cursor = db.query(
                CallHistoryContract.CallEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        List<CallHistory> itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry._ID));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_TITLE));
            String phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_PHONE_NUMBER));
            long timeAt = cursor.getLong(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_TIME));

            int amount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(CallHistoryContract.CallEntry.COLUMN_NAME_AMOUNT));
            CallHistory callHistory = new CallHistory(title, phone, timeAt);
            callHistory.setId(itemId);
            callHistory.setAmount(amount);
            itemIds.add(callHistory);
        }
        cursor.close();
        return itemIds;
    }

    public void closeDb() {
        mDbHelper.close();
    }
}
