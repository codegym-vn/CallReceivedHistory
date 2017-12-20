package com.mgame.callhistory.data.sqlite;

import android.provider.BaseColumns;

/**
 * Created by TienBM on 12/17/2017.
 */

public final class CallHistoryContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CallHistoryContract() {}

    /* Inner class that defines the table contents */
    public static class CallEntry implements BaseColumns {
        public static final String TABLE_NAME = "callhistory";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_TIME = "time_at";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }
}
