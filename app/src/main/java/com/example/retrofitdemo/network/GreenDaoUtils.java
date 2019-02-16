package com.example.retrofitdemo.network;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.retrofitdemo.application.App;
import com.example.retrofitdemo.gen.DaoMaster;
import com.example.retrofitdemo.gen.DaoSession;

public class GreenDaoUtils {

    private static GreenDaoUtils insanner;
    public static GreenDaoUtils getInsanner(){
        if (insanner==null){
            synchronized (GreenDaoUtils.class){
                if (insanner == null){
                    insanner=new GreenDaoUtils();
                }
            }
        }
        return insanner;
    }

    public GreenDaoUtils() {
    }

    public void initGreenDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "testDB");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public static DaoSession daoSession;
    public  DaoSession getDaoSession() {
        return daoSession;
    }
}
