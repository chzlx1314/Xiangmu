package com.example.asus.jingdong.model.bane;

import com.example.asus.jingdong.model.bean.GoodsClassBean;
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
 * 分类请求数据
 */
public class GetGoodsClassDataModel { private final OkHttpClient okHttpClient;

    public GetGoodsClassDataModel() {

        okHttpClient = MyApp.getOkHttpClient();
    }


    public void getData(final DataCallBack callBack){


        Request request = new Request.Builder()
                .url(Urls.GOODS_VIEWPAGER)
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

                if(response.isSuccessful()){
                    GoodsClassBean bean = gson
                            .fromJson(json,GoodsClassBean.class);

                    callBack.onGetDataSucceed(bean);
                }

            }
        });

    }



    public interface DataCallBack{
        void onGetDataSucceed(GoodsClassBean bean);
        void onGetDataFail(String e);
    }
}

