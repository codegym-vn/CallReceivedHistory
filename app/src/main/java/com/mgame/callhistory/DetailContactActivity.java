package com.mgame.callhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mgame.callhistory.data.DataManager;
import com.mgame.callhistory.data.models.CallHistory;

import java.util.List;

public class DetailContactActivity extends AppCompatActivity{

    public static void start(Context context,String phoneContact) {
        Intent starter = new Intent(context, DetailContactActivity.class);
        starter.putExtra("phoneContact",phoneContact);
        context.startActivity(starter);
    }

    private RecyclerView rvCallHistory;
    private SwipeRefreshLayout swRefresh;
    private HistoryDetailAdapter mHistoryAdapter;
    private String phoneContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        phoneContact = getIntent().getStringExtra("phoneContact");
        rvCallHistory = findViewById(R.id.rvCallHistory);
        swRefresh = findViewById(R.id.swRefresh);
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllHistoryByPhone(phoneContact);
            }
        });
        rvCallHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvCallHistory.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new HistoryDetailAdapter(this);

        rvCallHistory.setAdapter(mHistoryAdapter);
        getAllHistoryByPhone(phoneContact);
    }

    private void getAllHistoryByPhone(String phone) {
        List<CallHistory> callHistories = DataManager.getInstance().getAllHistoryByPhone(phone);
        mHistoryAdapter.setItems(callHistories);
        swRefresh.setRefreshing(false);
    }

}
