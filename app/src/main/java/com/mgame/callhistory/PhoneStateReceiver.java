package com.mgame.callhistory;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import com.mgame.callhistory.data.DataManager;
import com.mgame.callhistory.data.models.CallHistory;

import java.util.Calendar;

/**
 * Created by TienBM on 12/17/2017.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String name = getContactName(context, incomingNumber);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                Toast.makeText(context, "Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();
                CallHistory callHistory = new CallHistory(name, incomingNumber, Calendar.getInstance().getTimeInMillis());
                DataManager.getInstance().insertEntry(callHistory);
            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
//                Toast.makeText(context, "Received State", Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
//                Toast.makeText(context, "Idle State", Toast.LENGTH_SHORT).show();
                context.sendBroadcast(new Intent(MainActivity.INTENT_FILTER_IDLE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getContactName(Context context, String phone) {
        String name = "Unknown";
        try {
            ContentResolver cr = context.getContentResolver();
            String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?";
            String[] selectionArgs = {phone};

            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection, selectionArgs, null);
            phones.moveToNext();
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phones.close();

        } catch (NullPointerException e) {
            name = "Unknown";
        }
        return name;
    }
}
