package com.cqgas.gasmeter;

import android.app.Application;
import android.content.Context;

/**
 * Created by 国耀 on 2015/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context= getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
