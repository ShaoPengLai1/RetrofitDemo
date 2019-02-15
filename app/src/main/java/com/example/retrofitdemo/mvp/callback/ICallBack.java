package com.example.retrofitdemo.mvp.callback;

/**
 *
 * @author Peng
 * @time 2019/2/14 19:26
 */

public interface ICallBack {
    void success(Object obj);
    void failed(Exception e);
}
