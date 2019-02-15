package com.example.retrofitdemo.mvp.model;

import com.example.retrofitdemo.mvp.callback.MyCallBack;
import com.example.retrofitdemo.network.RetrofitManager;
import com.google.gson.Gson;

public class IModelImpl implements IModel {
    @Override
    public void requestDataGet(String url, String params, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    if (myCallBack != null) {
                        myCallBack.onFailed(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.onFailed(error);
                }
            }
        });
    }
}
