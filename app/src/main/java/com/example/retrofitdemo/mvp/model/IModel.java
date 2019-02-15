package com.example.retrofitdemo.mvp.model;

import com.example.retrofitdemo.mvp.callback.MyCallBack;

public interface IModel {
    void requestDataGet(String url, String params, Class clazz, MyCallBack myCallBack);
}
