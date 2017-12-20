package com.mgame.callhistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgame.callhistory.data.models.CallHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TienBM on 12/17/2017.
 */
public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder> {

    private static final String TAG = HistoryDetailAdapter.class.getSimpleName();
    private Context mContext;
    private List<CallHistory> mList;
    private SimpleDateFormat mSimpleDateFormat;

    public HistoryDetailAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        mSimpleDateFormat = new SimpleDateFormat("HH:mm EEE,d MMM", Locale.getDefault());
    }

    public void setItems(List<CallHistory> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_detail_callhistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CallHistory item = mList.get(position);
        holder.tvPhone.setText(item.getPhone());
        holder.tvTime.setText(mSimpleDateFormat.format(item.getTimeAt()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvPhone;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}