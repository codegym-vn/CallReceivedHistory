package com.mgame.callhistory.data.models;

/**
 * Created by TienBM on 12/17/2017.
 */

public class CallHistory {
    private long id;
    private String title;
    private String phone;
    private long timeAt;
    private int amount=1;

    public CallHistory() {
    }

    public CallHistory(String title, String phone, long timeAt) {
        this.title = title;
        this.phone = phone;
        this.timeAt = timeAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getTimeAt() {
        return timeAt;
    }

    public void setTimeAt(long timeAt) {
        this.timeAt = timeAt;
    }
}
