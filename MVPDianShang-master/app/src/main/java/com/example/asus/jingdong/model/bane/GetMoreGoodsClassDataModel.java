package com.example.asus.jingdong.model.bane;


import com.example.asus.jingdong.model.bean.GoodsClassData;
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
 * 获取分类信息的 model
 */

public class GetMoreGoodsClassDataModel {

    private final OkHttpClient okHttpClient;

    public GetMoreGoodsClassDataModel() {

        okHttpClient = MyApp.getOkHttpClient();
    }


    public void getMoreData(final DataCallBack callBack,String gc_id){


        Request request = new Request.Builder()
                .url(Urls.GOODS_CLASS+gc_id)
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
                    GoodsClassData bean = gson
                            .fromJson(json,GoodsClassData.class);

                    callBack.onGetDataSucceed(bean);
                }

            }
        });

    }


    public interface DataCallBack{
        void onGetDataSucceed(GoodsClassData bean);
        void onGetDataFail(String e);
    }
}
