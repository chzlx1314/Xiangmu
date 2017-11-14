package com.example.asus.jingdong.model.net;


public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon,String str);
}
