package com.example.asus.jingdong.model.bane;

import com.example.asus.jingdong.model.bean.RegisteredBean;
import com.example.asus.jingdong.model.net.NetDataCallBack;
import com.example.asus.jingdong.model.net.OkHttpUrl;
import com.example.asus.jingdong.utils.Urls;

import okhttp3.FormBody;

/**
 * 类描述注册请求数据
 */
public class Registere implements NetDataCallBack<RegisteredBean> {


    public void initview(String susername, String spassword) {
        OkHttpUrl okhttpUrl = new OkHttpUrl();
        FormBody body = new FormBody.Builder()
                .add("mobile", susername)
                .add("password", spassword)
                .build();
        okhttpUrl.getLoadDataPost(Urls.LINK_REGISTER, this, RegisteredBean.class, body);
    }


    @Override
    public void success(RegisteredBean registeredBean) {
        String code = registeredBean.getCode();
        int codes = Integer.valueOf(code);
        String msg = registeredBean.getMsg();
        if (codes == 0) {
            registeress.RegistereSuccess(msg);
        } else {
            registeress.RegistereFail(msg);
        }
    }

    @Override
    public void faild(int positon, String str) {

    }

    public interface Registeress {
        void RegistereSuccess(String error);

        void RegistereFail(String error);

    }

    private Registeress registeress;

    public void setRegisteress(Registeress registeress) {
        this.registeress = registeress;
    }
}


