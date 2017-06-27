package com.example.qinyu.dotweather.util;

/**
 * Created by qinyu on 2017/6/26.
 */
public interface HttpCallbackListener {
    void onFinish(String respone);
    void onError(Exception e);
}
