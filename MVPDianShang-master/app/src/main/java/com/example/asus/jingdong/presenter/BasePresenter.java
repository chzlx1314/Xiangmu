package com.example.asus.jingdong.presenter;

import android.content.Context;

import com.example.asus.jingdong.model.net.MyApp;
import com.example.asus.jingdong.view.IViews.IView;


/**
 * Created by wxn on 2017/9/7.
 *
 * Presenter 的基类
 */

public class BasePresenter<T extends IView> {

    protected T iView;


    public BasePresenter(T iView) {
        this.iView = iView;
    }

    Context context(){
        if(iView != null && iView.context() != null){
            return iView.context();
        }
        return MyApp.getAppContext();
    }

}
