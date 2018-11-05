package com.enway.test;

import android.app.Application;

import com.rmmis.platform.ZwpLiabrary;

/**
 * Created by Edianzu on 2018/11/5.
 */

public class AppApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ZwpLiabrary.init(this);
    }
}
