package com.example.asus.jingdong.model.bane;

import com.example.asus.jingdong.model.bean.CarBean;
import com.example.asus.jingdong.model.net.MyApp;
import com.example.asus.jingdong.utils.Urls;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *    购物车请求数据
 */
public class GetGoodsCarBeanModel {
    private final OkHttpClient okHttpClient;

    public GetGoodsCarBeanModel() {

        okHttpClient = MyApp.getOkHttpClient();
    }


    public void getCarData(final DataCallBack callBack) {


        Request request = new Request.Builder()
                .url(Urls.CART_SELECT)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callBack.onGetDataFail(e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                Gson gson = new Gson();

                if (response.isSuccessful()) {
                    CarBean bean = gson
                            .fromJson(json, CarBean.class);

                    callBack.onGetDataSucceed(bean);
                }

            }
        });

    }


    public interface DataCallBack {
        void onGetDataSucceed(CarBean bean);

        void onGetDataFail(String e);
    }
}

