package com.example.retrofitdemo.application;

import android.app.Application;

import com.example.retrofitdemo.network.GreenDaoUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        GreenDaoUtils.getInsanner().initGreenDao(this);
    }



}
