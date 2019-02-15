package com.example.retrofitdemo.mvp.presenter;

public interface IPresenter {
    void startRequestGet(String url, String params, Class clazz);
}
