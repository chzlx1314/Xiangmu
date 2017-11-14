package com.example.asus.jingdong.view.IViews;

import com.example.asus.jingdong.model.bean.GoodsDetailsData;

/**
 * 类描述
 */
public interface IGetGoodsDetailView extends IView {

    //    获取数据成功
    void onGetDataSucceed(GoodsDetailsData bean);
    //    获取数据失败
    void onGetDataFail(String e);

}
