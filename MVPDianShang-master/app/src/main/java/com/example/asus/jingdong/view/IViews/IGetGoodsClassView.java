package com.example.asus.jingdong.view.IViews;


import com.example.asus.jingdong.model.bean.GoodsClassBean;
/**
 * 类描述  用于获取主要商品分类的接口
 */
public interface IGetGoodsClassView extends IView {

//    获取数据成功
    void onGetDataSucceed(GoodsClassBean bean);
//    获取数据失败
    void onGetDataFail(String e);
}
