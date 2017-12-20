package com.mgame.callhistory.data;

import android.app.Application;

/**
 * Created by TienBM on 12/17/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.getInstance().initDb(this);
    }
}
