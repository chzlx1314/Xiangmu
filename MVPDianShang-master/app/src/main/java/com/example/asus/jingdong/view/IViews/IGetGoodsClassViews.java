package com.example.asus.jingdong.view.IViews;


import com.example.asus.jingdong.model.bean.GoodsClassData;

/**
 * 类描述  用于获取主要商品分类的接口
 */
public interface IGetGoodsClassViews extends IView {

//    获取数据成功
    void onGetDataSucceed(GoodsClassData bean);
//    获取数据失败
    void onGetDataFail(String e);
}
