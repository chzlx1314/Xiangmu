package com.example.asus.jingdong.model.bane;

import com.example.asus.jingdong.model.bean.AddCatBean;
import com.example.asus.jingdong.model.net.MyApp;
import com.example.asus.jingdong.utils.Urls;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加购物车
 */
public class AddOrDelCartModel {
    private final OkHttpClient okHttpClient;

    public AddOrDelCartModel() {

        okHttpClient = MyApp.getOkHttpClient();
    }


    public void AddGoodsToCart(final DataCallBack callBack,String uid,String pid,String sellerid){

        FormBody body = new FormBody.Builder()
                .add("uid",uid)
                .add("pid",pid)
                .add("sellerid",sellerid)
                .build();
        Request request = new Request.Builder()
                .url(Urls.CART_ADD)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callBack.onFail(e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                Gson gson = new Gson();

                if(response.isSuccessful()){
                    AddCatBean bean = gson.fromJson(json,AddCatBean.class);

                    callBack.onSucceed(bean);
                }

            }
        });



    }




    public interface DataCallBack{
        void onSucceed(AddCatBean bean);
        void onFail(String e);
    }
}
