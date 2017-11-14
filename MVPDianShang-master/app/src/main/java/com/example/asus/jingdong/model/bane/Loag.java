package com.example.asus.jingdong.model.bane;

import com.example.asus.jingdong.model.bean.LoagBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;

import okhttp3.FormBody;

/**
 * 类描述登陆请求数据
 */
public class Loag implements NetDataCallBack<LoagBean> {


    public void initview(String username,String password) {
        OkHttpUrl okhttpUrl = new OkHttpUrl();
        FormBody body = new FormBody.Builder()
                .add("mobile",username)
                .add("password",password)
                .build();
        okhttpUrl.getLoadDataPost(Urls.LOGIN_URl,this,LoagBean.class,body);
    }

    @Override
    public void success(LoagBean loagBean) {
        String code = loagBean.getCode();
        int codes=Integer.valueOf(code);
        String msg = loagBean.getMsg();
        if (codes==0){
            iLogin.loginSuccess(msg,loagBean);
        }else {
            iLogin.loginFail(msg);
        }
    }

    @Override
    public void faild(int positon, String str) {

    }
    public interface ILogin{
        void loginSuccess(String error, LoagBean loag);
        void loginFail(String error);
    }
    private ILogin iLogin;

    public void setiLogin(ILogin iLogin) {
        this.iLogin = iLogin;
    }
}

