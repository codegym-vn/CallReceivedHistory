package com.mgame.callhistory;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mgame.callhistory.data.DataManager;
import com.mgame.callhistory.data.models.CallHistory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HistoryAdapter.OnItemClickListener {

    public static final String INTENT_FILTER_IDLE = "com.mgame.callhistory.receiver.stateidle";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private RecyclerView rvCallHistory;
    private SwipeRefreshLayout swRefresh;
    private HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCallHistory = findViewById(R.id.rvCallHistory);
        swRefresh = findViewById(R.id.swRefresh);
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllHistory();
            }
        });
        rvCallHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCallHistory.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryAdapter(this, this);

        rvCallHistory.setAdapter(mHistoryAdapter);
        getAllHistory();
        requestPermision();
    }

    BroadcastReceiver mBroadcastReceiverIdle = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getAllHistory();
        }
    };

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Thông báo")
                        .setMessage("Vui lòng chấp nhận quyền để ứng dụng chúng tôi được phép nhận cuộc gọi")
                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        })
                        .show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Cấp quyền thành công.", Toast.LENGTH_SHORT).show();
                } else {

                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiverIdle, new IntentFilter(INTENT_FILTER_IDLE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiverIdle);
    }

    private void getAllHistory() {
        List<CallHistory> callHistories = DataManager.getInstance().getAllHistory();
        mHistoryAdapter.setItems(callHistories);
        swRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(CallHistory item) {


        DetailContactActivity.start(this, item.getPhone());
    }
}
